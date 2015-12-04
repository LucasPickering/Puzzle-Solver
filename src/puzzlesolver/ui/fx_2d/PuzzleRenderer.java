package puzzlesolver.ui.fx_2d;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.Side;
import puzzlesolver.Solver;
import puzzlesolver.arrays.PointsBuilder;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.enums.Direction;

public class PuzzleRenderer {

  private Paint[] FILL_COLORS = new Paint[]{Color.NAVY, Color.DARKSLATEBLUE,
                                            Color.DARKGOLDENROD, Color.DARKOLIVEGREEN,
                                            Color.MAROON, Color.DARKVIOLET,
                                            Color.DARKCYAN, Color.DARKORCHID};
  private Paint[] STROKE_COLORS = new Paint[]{Color.LIGHTSEAGREEN,
                                              Color.LIGHTSALMON,
                                              Color.LIGHTYELLOW};

  /**
   * Get a global point from a local point.
   *
   * @param localPoint   local point to calculate from
   * @param orientation  direction of parent side
   * @param pieceX       x-position in 2D points array
   * @param pieceY       y-position in 2D points array
   * @param windowWidth  width of window to render in
   * @param windowHeight width of window to render in
   * @return global point
   */
  public static Point getGlobalPoint(Point localPoint, Direction orientation, int pieceX,
                                     int pieceY, int puzzleWidth, int puzzleHeight,
                                     double windowWidth, double windowHeight) {
    Objects.requireNonNull(localPoint);
    // May have to scale. For now, made a placeholder.
    final double scaledPaddingX = UIConstants.VISUAL_PIECE_WIDTH;
    final double scaledPaddingY = UIConstants.VISUAL_PIECE_HEIGHT;

    final double scaledPieceWidth = (windowWidth - (scaledPaddingX * 2)) / puzzleWidth;
    final double scaledPieceHeight = (windowHeight - (scaledPaddingY * 2)) / puzzleHeight;

    final double scaleX = scaledPieceWidth / UIConstants.VISUAL_PIECE_WIDTH;
    final double scaleY = scaledPieceHeight / UIConstants.VISUAL_PIECE_HEIGHT;

    final double pieceGlobalX = scaledPaddingX + pieceX * scaledPieceWidth;
    final double pieceGlobalY = scaledPaddingY + pieceY * scaledPieceHeight;

    double pointGlobalX = pieceGlobalX;
    double pointGlobalY = pieceGlobalY;

    switch (orientation) {
      case NORTH:
        pointGlobalX += scaleX * localPoint.x;
        pointGlobalY -= scaleY * localPoint.y;
        break;
      case EAST:
        pointGlobalX += scaledPieceWidth;
        pointGlobalX += scaleX * localPoint.y;
        pointGlobalY += scaleY * localPoint.x;
        break;
      case SOUTH:
        pointGlobalY += scaledPieceHeight;
        pointGlobalX += scaleX * localPoint.x;
        pointGlobalY += scaleY * localPoint.y;
        break;
      case WEST:
        pointGlobalY += scaleY * localPoint.x;
        pointGlobalX -= scaleX * localPoint.y;
        break;
      default:
        break;
    }

    return new Point(pointGlobalX, pointGlobalY);
  }

  public void draw(GraphicsContext gc, Solver solver) {
    gc.getCanvas().getScene().setFill(Color.STEELBLUE.darker());
    drawPuzzle(gc, solver);
    // drawNextPiece(gc, solver);
  }

  public void drawPuzzle(GraphicsContext gc, Solver solver) {
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

    Piece[][] solution = solver.getSolution();

    for (int x = 0; x < solution.length; x++) {
      for (int y = 0; y < solution[x].length; y++) {
        Piece piece = solution[x][y];

        if (piece != null) {
          drawPiece(gc, piece, x, y, solution.length, solution[x].length);
        }
      }
    }
  }

  public void drawPiece(GraphicsContext gc, Piece piece, int x, int y,
                        int puzzleWidth, int puzzleHeight) {
    gc.setFill(
      FILL_COLORS[FILL_COLORS.length - 1 - piece.getPieceType().ordinal() % FILL_COLORS.length]);
    gc.setLineJoin(StrokeLineJoin.ROUND);
    gc.setLineCap(StrokeLineCap.ROUND);
    double windowWidth = gc.getCanvas().getWidth();
    double windowHeight = gc.getCanvas().getHeight();

    gc.setLineWidth((windowWidth + windowHeight) / 2 / 200);

    PointsBuilder xs = new PointsBuilder();
    PointsBuilder ys = new PointsBuilder();

    for (Direction direction : Direction.values()) {
      Side s = piece.getSide(direction);
      if (s != null) {
        gc.setStroke(STROKE_COLORS[s.getSideType().ordinal() % STROKE_COLORS.length]);
        Point[] points = s.getPoints();
        if (direction == Direction.SOUTH || direction == Direction.WEST) {
          ArrayUtils.reverse(points);
        }
        for (Point point : points) {
          Point globalPoint = PuzzleRenderer
            .getGlobalPoint(point, direction, x, y,
                            puzzleWidth, puzzleHeight,
                            windowWidth, windowHeight);

          xs.add(globalPoint.x);
          ys.add(globalPoint.y);
        }
      }
    }

    double[] primitiveXs = ArrayUtils.toPrimitive(xs.toPoints());
    double[] primitiveYs = ArrayUtils.toPrimitive(ys.toPoints());
    gc.setGlobalAlpha(1.0d);
    gc.fillPolygon(primitiveXs, primitiveYs, xs.size());
    gc.setGlobalAlpha(0.5d);
    gc.strokePolygon(primitiveXs, primitiveYs, xs.size());
  }


  public void drawNextPiece(GraphicsContext gc, Solver solver) {
    gc.setFill(Color.PALEVIOLETRED);
    gc.setStroke(Color.INDIANRED);
    gc.setGlobalAlpha(0.5d);

    Piece[][] solution = solver.getSolution();

    final double width = gc.getCanvas().getWidth();
    final double height = gc.getCanvas().getHeight();

    final int puzzleWidth = solution.length;
    final int puzzleHeight = solution[0].length;

    gc.fillRect(width * (puzzleWidth - 1) / puzzleWidth, height * (puzzleWidth - 1) / puzzleHeight,
                width / puzzleWidth, height / puzzleHeight);

    if (solver.getX() < puzzleWidth && solver.getY() < puzzleHeight) {
      Piece next = solution[solver.getX()][solver.getY()];
      if (next != null) {
        drawPiece(gc, next, puzzleWidth - 1, puzzleHeight - 1, puzzleWidth, puzzleHeight);
      }
    }
  }
}
