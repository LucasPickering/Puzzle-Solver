package puzzlesolver.ui.fx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import puzzlesolver.Constants;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.PointsBuilder;
import puzzlesolver.Solver;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleSide;

public class PuzzleRenderer {

  private final Solver solver;
  private final Canvas puzzleCanvas;

  public PuzzleRenderer(Solver solver, Canvas puzzleCanvas) {
    this.solver = solver;
    this.puzzleCanvas = puzzleCanvas;

    // Set the size of the canvas
    puzzleCanvas.setWidth(getRequiredWidth());
    puzzleCanvas.setHeight(getRequiredHeight());
  }

  public void setRenderMethod(String renderMethod) {
    switch (renderMethod) {
      case Constants.UI.TEXT_SIMPLE:
        // TODO
        break;
      case Constants.UI.TEXT_FANCY:
        // TODO
        break;
      case Constants.UI.VISUAL:
        // TODO
        break;
      case Constants.UI.VISUAL_FANCY:
        // TODO
        break;
      default:
        throw new IllegalArgumentException(renderMethod + " is not a valid render method");
    }
  }

  public int getRequiredWidth() {
    return Math.max(solver.getSolution().length * Constants.UI.VISUAL_PIECE_WIDTH
                    + Constants.UI.VISUAL_PIECE_PADDING * 2,
                    Constants.UI.WINDOW_MIN_WIDTH);
  }

  public int getRequiredHeight() {
    return Math.max(solver.getSolution()[0].length * Constants.UI.VISUAL_PIECE_HEIGHT
                    + Constants.UI.VISUAL_PIECE_PADDING * 2,
                    Constants.UI.WINDOW_MIN_HEIGHT);
  }

  public void draw() throws Exception {
    GraphicsContext gc = puzzleCanvas.getGraphicsContext2D();
    Piece[][] solution = solver.getSolution();

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
            Point globalPoint = globalPointFromLocalPoint(points[i], arrayX, arrayY);
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

  public Point globalPointFromLocalPoint(Point localPoint, int pieceX, int pieceY) {
    // TODO
    return new Point(0d, 0d);
  }
}
