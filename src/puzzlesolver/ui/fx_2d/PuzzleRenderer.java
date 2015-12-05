package puzzlesolver.ui.fx_2d;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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

  int lastDrawnX = 0;
  int lastDrawnY = 0;

  int previousPuzzleWidth;
  int previousPuzzleHeight;

  /*
  private Paint[] FILL_COLORS = new Paint[]{Color.NAVY, Color.DARKSLATEBLUE,
                                            Color.DARKGOLDENROD, Color.DARKOLIVEGREEN,
                                            Color.MAROON, Color.DARKVIOLET,
                                            Color.DARKCYAN, Color.DARKORCHID};
  private Paint[] STROKE_COLORS = new Paint[]{Color.LIGHTSEAGREEN,
                                              Color.LIGHTSALMON,
                                              Color.LIGHTYELLOW};
                                              */
  boolean done;

  public static double getScaledPieceSideLength(int puzzleWidth, int puzzleHeight,
                                                double windowWidth, double windowHeight) {
    final double scaledPadding = UIConstants.VISUAL_PIECE_PADDING;
    final boolean yConstraint = Double.compare(windowWidth, windowHeight) > 0;
    final double windowShortSide = yConstraint ? windowHeight : windowWidth;
    final double windowLongSide = yConstraint ? windowWidth : windowHeight;
    final double usableSpaceShortSide = windowShortSide - (scaledPadding * 2);
    final double usableSpaceLongSide = windowLongSide - (scaledPadding * 2);

    return Math.min(usableSpaceShortSide / (yConstraint ? puzzleHeight : puzzleWidth),
                    usableSpaceLongSide / (yConstraint ? puzzleWidth : puzzleHeight));
  }

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
    final double scaledPadding = UIConstants.VISUAL_PIECE_PADDING;
    final boolean yConstraint = Double.compare(windowWidth, windowHeight) > 0;
    final double windowShortSide = yConstraint ? windowHeight : windowWidth;
    final double windowLongSide = yConstraint ? windowWidth : windowHeight;
    final double usableSpaceShortSide = windowShortSide - (scaledPadding * 2);
    final double usableSpaceLongSide = windowLongSide - (scaledPadding * 2);

    final double scaledPieceSideLength
      = Math.min(usableSpaceShortSide / (yConstraint ? puzzleHeight : puzzleWidth),
                 usableSpaceLongSide / (yConstraint ? puzzleWidth : puzzleHeight));

    final double centredMarginSizeX =
      (windowWidth - puzzleWidth * scaledPieceSideLength
       - scaledPieceSideLength / 2) / 2;
    final double centredMarginSizeY =
      (windowHeight - puzzleHeight * scaledPieceSideLength
       - scaledPieceSideLength / 2) / 2;

    final double scaleX = scaledPieceSideLength / UIConstants.VISUAL_PIECE_WIDTH;
    final double scaleY = scaledPieceSideLength / UIConstants.VISUAL_PIECE_HEIGHT;

    final double pieceGlobalX = scaledPadding + pieceX * scaledPieceSideLength;
    final double pieceGlobalY = scaledPadding + pieceY * scaledPieceSideLength;

    double pointGlobalX = pieceGlobalX + centredMarginSizeX;
    double pointGlobalY = pieceGlobalY + centredMarginSizeY;

    switch (orientation) {
      case NORTH:
        pointGlobalX += scaleX * localPoint.x;
        pointGlobalY -= scaleY * localPoint.y;
        break;
      case EAST:
        pointGlobalX += scaledPieceSideLength;
        pointGlobalX += scaleX * localPoint.y;
        pointGlobalY += scaleY * localPoint.x;
        break;
      case SOUTH:
        pointGlobalY += scaledPieceSideLength;
        pointGlobalX += scaleX * localPoint.x;
        pointGlobalY += scaleY * localPoint.y;
        break;
      case WEST:
        pointGlobalY += scaleY * localPoint.x;
        pointGlobalX -= scaleX * localPoint.y;
        break;
      default:
        throw new EnumConstantNotPresentException(Direction.class, orientation.name());
    }

    return new Point(pointGlobalX, pointGlobalY);
  }

  public void draw(GraphicsContext gc, Solver solver) {
    reset(gc);
    previousPuzzleWidth = 0;
    previousPuzzleHeight = 0;

    update(gc, solver);
  }

  private void clearCanvas(GraphicsContext gc) {
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
  }

  private void reset(GraphicsContext gc) {
    clearCanvas(gc);
    lastDrawnX = 0;
    lastDrawnY = 0;
    done = false;
  }

  public void update(GraphicsContext gc, Solver solver) {
    Piece[][] solution = solver.getSolution();

    if (solution.length != previousPuzzleWidth || solution[0].length != previousPuzzleHeight) {
      reset(gc);
      previousPuzzleWidth = solution.length;
      previousPuzzleHeight = solution[0].length;
    }

    drawPuzzle(gc, solver);
    // drawNextPiece(gc, solver);
  }

  public void drawPuzzle(GraphicsContext gc, Solver solver) {
    if (done) {
      return;
    }

    Piece[][] solution = solver.getSolution();

    for (int x = lastDrawnX; x < solution.length; x++) {
      Piece piece = solution[x][lastDrawnY];
      if (piece == null) {
        lastDrawnX = x;
        return;
      }
      if (lastDrawnY == solution[0].length - 1 && x == solution.length - 1) {
        done = true;
      }

      drawPiece(gc, piece, x, lastDrawnY, solution.length, solution[0].length);
      lastDrawnX = x;
    }

    if (done) {
      return;
    }

    for (int y = lastDrawnY + 1; y < solution[0].length; y++) {
      for (int x = 0; x < solution.length; x++) {
        Piece piece = solution[x][y];

        if (piece == null || (x == solution.length - 1 && y == solution[x].length - 1)) {
          lastDrawnX = x;
          lastDrawnY = y;
          return;
        }

        drawPiece(gc, piece, x, y, solution.length, solution[x].length);
      }
    }
  }

  public void drawPiece(GraphicsContext gc, Piece piece, int x, int y,
                        int puzzleWidth, int puzzleHeight) {
    /*
    gc.setFill(
      FILL_COLORS[FILL_COLORS.length - 1 - piece.getPieceType().ordinal() % FILL_COLORS.length]);
    */
    double randomVariant = 0.1d * Math.random();
    gc.setFill(
      new Color(1.0d - 0.4d * x / puzzleWidth - randomVariant,
                0.5d + 0.4d * x / puzzleWidth + randomVariant,
                0.5d + 0.4d * y / puzzleHeight + randomVariant, 1.0d));
    gc.setLineJoin(StrokeLineJoin.ROUND);
    gc.setLineCap(StrokeLineCap.ROUND);
    double windowWidth = gc.getCanvas().getWidth();
    double windowHeight = gc.getCanvas().getHeight();

    gc.setLineWidth(getScaledPieceSideLength(puzzleWidth, puzzleHeight, gc.getCanvas().getWidth(),
                                             gc.getCanvas().getHeight()) / 10);

    PointsBuilder xs = new PointsBuilder();
    PointsBuilder ys = new PointsBuilder();

    for (Direction direction : Direction.values()) {
      Side s = piece.getSide(direction);
      if (s != null) {
        gc.setStroke(
          new Color(0.8d - 0.7d * x / puzzleWidth - randomVariant,
                    0.7d * x / puzzleWidth + randomVariant,
                    0.8d - 0.7d * y / puzzleHeight - randomVariant, 1.0d));
        Point[] points = s.getPoints();
        if (direction == Direction.SOUTH || direction == Direction.WEST) {
          ArrayUtils.reverse(points);
        }
        for (Point point : points) {
          Point globalPoint = getGlobalPoint(point, direction, x, y,
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
    gc.strokePolygon(primitiveXs, primitiveYs, xs.size());
  }

  public void drawNextPiece(GraphicsContext gc, Solver solver) {
    gc.setFill(Color.PALEVIOLETRED);
    gc.setStroke(Color.INDIANRED);

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
