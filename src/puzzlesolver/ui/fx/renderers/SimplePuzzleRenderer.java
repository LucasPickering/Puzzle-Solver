package puzzlesolver.ui.fx.renderers;

import java.util.Objects;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.PointsBuilder;
import puzzlesolver.Solver;
import puzzlesolver.constants.Constants;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleSide;

public class SimplePuzzleRenderer implements PuzzleRenderer<Solver> {

  Solver solver;
  private Canvas puzzleCanvas;

  public SimplePuzzleRenderer() {
    this(null);
  }

  public SimplePuzzleRenderer(Solver solver) {
    init(solver);
  }

  public static Point globalPointFromLocalPoint(Point localPoint, Direction orientation,
                                                int pieceX, int pieceY) {
    Objects.requireNonNull(localPoint);
    Objects.requireNonNull(orientation);
    if (pieceX < 0 || pieceY < 0) {
      throw new IllegalArgumentException("Piece coordinates must be natural numbers");
    }

    final int pieceGlobalX =
        UIConstants.VISUAL_PIECE_PADDING + pieceX * UIConstants.VISUAL_PIECE_WIDTH;
    final int pieceGlobalY =
        UIConstants.VISUAL_PIECE_PADDING + pieceY * UIConstants.VISUAL_PIECE_HEIGHT;

    return new Point(pieceGlobalX + ((UIConstants.VISUAL_PIECE_WIDTH / 2) * orientation.x)
                     + (localPoint.x * orientation.y),
                     pieceGlobalY + ((UIConstants.VISUAL_PIECE_HEIGHT / 2) * orientation.y)
                     + (localPoint.y * orientation.x));
  }

  public void init(Solver solver) {
    this.solver = solver;
    puzzleCanvas = new Canvas(getRequiredWidth(), getRequiredHeight());
    Constants.LOGGER.println(1, "Initialized " + puzzleCanvas.getWidth() + "x" +
                                puzzleCanvas.getHeight() + " puzzle rendering window.");
  }

  public int getRequiredWidth() {
    return (solver == null || solver.getSolution() == null)
           ? UIConstants.WINDOW_MIN_WIDTH
           : Math.max(solver.getSolution().length * UIConstants.VISUAL_PIECE_WIDTH
                      + UIConstants.VISUAL_PIECE_PADDING * 2,
                      UIConstants.WINDOW_MIN_WIDTH);
  }

  public int getRequiredHeight() {
    return (solver == null || solver.getSolution() == null)
           ? UIConstants.WINDOW_MIN_HEIGHT
           : Math.max(solver.getSolution()[0].length * UIConstants.VISUAL_PIECE_HEIGHT
                      + UIConstants.VISUAL_PIECE_PADDING * 2,
                      UIConstants.WINDOW_MIN_HEIGHT);
  }

  @Override
  public void update() throws Exception {
    GraphicsContext gc = puzzleCanvas.getGraphicsContext2D();
    Piece[][] solution = solver.getSolution();

    Constants.LOGGER.printf(1, "Rendering %dx%d puzzle.\n", solution.length, solution[0].length);

    // Clear the canvas
    gc.clearRect(0, 0, puzzleCanvas.getWidth(), puzzleCanvas.getHeight());

    PointsBuilder xs = new PointsBuilder();
    PointsBuilder ys = new PointsBuilder();

    for (int arrayX = 0; arrayX < solution.length; arrayX++) {
      for (int arrayY = 0; arrayY < solution[0].length; arrayY++) {
        double[] xPoints;
        double[] yPoints;
        for (Direction d : Direction.values()) {
          Point[] points = ((SimpleSide) solution[arrayX][arrayY].getSide(d)).getPoints();

          xPoints = new double[points.length];
          yPoints = new double[points.length];

          for (int i = 0; i < points.length; i++) {
            Point globalPoint = globalPointFromLocalPoint(points[i], d, arrayX, arrayY);
            xPoints[i] = globalPoint.x;
            yPoints[i] = globalPoint.y;
          }

          xs.add(xPoints);
          ys.add(yPoints);
        }
        if (xs.getLength() != ys.getLength()) {
          throw new Exception(String.format("mismatch in number of coordinates: x(%d) != y(%d)",
                                            xs.getLength(), ys.getLength()));
        }
        gc.fillPolygon(xs.toPoints(), ys.toPoints(), xs.getLength());
      }
    }

  }
}
