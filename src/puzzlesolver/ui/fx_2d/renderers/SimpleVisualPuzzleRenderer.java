package puzzlesolver.ui.fx_2d.renderers;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.Solver;
import puzzlesolver.arrays.PointsBuilder;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleSide;

public class SimpleVisualPuzzleRenderer implements PuzzleRenderer<Solver> {

  @FXML
  private Solver solver;

  public SimpleVisualPuzzleRenderer() {
  }

  public SimpleVisualPuzzleRenderer(Solver solver) {
    init(solver);
  }

  /**
   * Get a global point from a local point.
   *
   * @param localPoint  local point to calculate from
   * @param orientation direction of parent side
   * @param pieceX      x-position in 2D points array
   * @param pieceY      y-position in 2D points array
   * @return global point
   */
  public static Point globalPointFromLocalPoint(Point localPoint, Direction orientation, int pieceX,
                                                int pieceY) {
    Objects.requireNonNull(localPoint);
    Objects.requireNonNull(orientation);

    if (pieceX < 0 || pieceY < 0) {
      throw new IllegalArgumentException("Piece coordinates must be natural numbers");
    }

    final double
      pieceGlobalX =
      UIConstants.VISUAL_PIECE_PADDING + pieceX * UIConstants.VISUAL_PIECE_WIDTH;
    final double
      pieceGlobalY =
      UIConstants.VISUAL_PIECE_PADDING + pieceY * UIConstants.VISUAL_PIECE_HEIGHT;

    return new Point(pieceGlobalX + ((UIConstants.VISUAL_PIECE_WIDTH / 2) * orientation.x)
                     + 5 * localPoint.x * orientation.y,
                     pieceGlobalY + ((UIConstants.VISUAL_PIECE_HEIGHT / 2) * orientation.y)
                     + 5 * localPoint.y * orientation.x);
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
  public static Point globalPointFromLocalPoint(Point localPoint, Direction orientation, int pieceX,
                                                int pieceY, int puzzleWidth, int puzzleHeight,
                                                double windowWidth, double windowHeight) {
    // May have to scale. For now, made a placeholder.
    final double scaledPaddingX = UIConstants.VISUAL_PIECE_PADDING;
    final double scaledPaddingY = UIConstants.VISUAL_PIECE_PADDING;

    final double pieceWidth = (windowWidth - scaledPaddingX * 2) / puzzleWidth;
    final double pieceHeight = (windowHeight - scaledPaddingY * 2) / puzzleHeight;

    final double scaleX = pieceWidth / UIConstants.VISUAL_PIECE_WIDTH;
    final double scaleY = pieceHeight / UIConstants.VISUAL_PIECE_HEIGHT;

    final double pieceGlobalX = scaledPaddingX + pieceX * pieceWidth;
    final double pieceGlobalY = scaledPaddingY + pieceY * pieceHeight;

    final double
      pointGlobalX =
      pieceGlobalX + ((pieceWidth / 2) * orientation.x)
      + localPoint.x * scaleX * orientation.y;
    final double
      pointGlobalY =
      pieceGlobalY + ((pieceHeight / 2) * orientation.y)
      + localPoint.y * scaleX * orientation.x;

    return new Point(pointGlobalX, pointGlobalY);
  }

  public void init(Solver solver) {
    Objects.requireNonNull(solver);

    this.solver = solver;
    Constants.LOGGER.println(1, "Initialized puzzle rendering window.");
  }

  @Override
  public double getRequiredWidth() {
    return (solver == null || solver.getSolution() == null) ? UIConstants.WINDOW_MIN_WIDTH : Math.max(
      solver.getSolution().length * UIConstants.VISUAL_PIECE_WIDTH
      + UIConstants.VISUAL_PIECE_PADDING * 2, UIConstants.WINDOW_MIN_WIDTH);
  }

  @Override
  public double getRequiredHeight() {
    return (solver == null || solver.getSolution() == null) ? UIConstants.WINDOW_MIN_HEIGHT : Math
      .max(solver.getSolution()[0].length * UIConstants.VISUAL_PIECE_HEIGHT
           + UIConstants.VISUAL_PIECE_PADDING * 2, UIConstants.WINDOW_MIN_HEIGHT);
  }

  @Override
  public void update(Canvas canvas) throws Exception {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Piece[][] solution = solver.getSolution();

    Constants.LOGGER.printf(1, "Drawing %dx%d puzzle.\n", solution.length, solution[0].length);

    // Clear the canvas
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    for (int arrayX = 0; arrayX < solution.length; arrayX++) {
      for (int arrayY = 0; arrayY < solution[0].length; arrayY++) {
        drawPiece(gc, solution[arrayX][arrayY], arrayX, arrayY);
      }
    }

  }

  private void drawPiece(GraphicsContext gc, Piece piece, int arrayX, int arrayY) throws Exception {
    gc.setFill(Color.BLACK);
    gc.setLineWidth(2);
    gc.setStroke(Color.DEEPPINK);
    gc.getCanvas().setWidth(getRequiredWidth());
    gc.getCanvas().setHeight(getRequiredHeight());
    Constants.LOGGER.println(2, "Drawing piece");
    Double[] xPoints;
    Double[] yPoints;

    // test
    gc.fillPolygon(new double[]{0, 10, 10, 0}, new double[]{0, 0, 10, 10}, 4);

    PointsBuilder xs = new PointsBuilder();
    PointsBuilder ys = new PointsBuilder();
    if (piece != null) {
      for (Direction d : Direction.values()) {
        Point[] points = ((SimpleSide) piece.getSide(d)).getPoints();
        Constants.LOGGER.printf(3, "Direction %s: %s", d, points);

        xPoints = new Double[points.length];
        yPoints = new Double[points.length];

        for (int i = 0; i < points.length; i++) {
          Point globalPoint = globalPointFromLocalPoint(points[i], d, arrayX, arrayY);
          xPoints[i] = globalPoint.x;
          yPoints[i] = globalPoint.y;
        }

        xs.addAll(xPoints);
        ys.addAll(yPoints);
      }
      if (xs.size() != ys.size()) {
        throw new Exception(
          String.format("mismatch in number of coordinates: x(%d) != y(%d)", xs.size(), ys.size()));
      }

      gc.strokePolygon(ArrayUtils.toPrimitive(xs.toPoints()), ArrayUtils.toPrimitive(ys.toPoints()),
                       xs.size());
      gc.fillPolygon(ArrayUtils.toPrimitive(xs.toPoints()), ArrayUtils.toPrimitive(ys.toPoints()),
                     xs.size());
    }
  }
}
