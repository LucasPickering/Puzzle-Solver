package puzzlesolver.ui.fx_3d;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import puzzlesolver.Generator;
import puzzlesolver.Piece;
import puzzlesolver.Point;
import puzzlesolver.Side;
import puzzlesolver.Solver;
import puzzlesolver.arrays.PointsBuilder;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleGenerator;
import puzzlesolver.simple.SimpleSide;
import puzzlesolver.simple.SimpleSolver;
import puzzlesolver.ui.fx_2d.renderers.SimpleVisualPuzzleRenderer;

public class ThreeDController extends Application {

  private static final int WIDTH = 4;
  private static final int HEIGHT = 4;
  private Paint[] colors = new Paint[]{Color.NAVY, Color.BLACK, Color.FIREBRICK};
  private boolean dynamicSize;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Generator generator = new SimpleGenerator();
    Solver solver = new SimpleSolver();
    solver.init(generator.generate(WIDTH, HEIGHT));

    primaryStage.setTitle("Puzzle!");
    Group root = new Group();
    Canvas canvas = new Canvas(300, 250);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    root.getChildren().add(canvas);
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

    dynamicSize = gc.getCanvas().widthProperty().isBound()
                  || gc.getCanvas().heightProperty().isBound();

    SteppableAnimationTimer timer = new SteppableAnimationTimer(solver, gc);
    primaryStage.getScene().setOnMouseClicked(new MouseEventHandler(timer));
    timer.start();
  }

  private void drawPuzzle(GraphicsContext gc, Solver solver) {
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

    Piece[][] solution = solver.getSolution();

    if (!dynamicSize) {
      gc.getCanvas().setWidth(UIConstants.VISUAL_PIECE_PADDING * 2
                              + UIConstants.VISUAL_PIECE_WIDTH * solution.length);
      gc.getCanvas().setHeight(UIConstants.VISUAL_PIECE_PADDING * 2
                               + UIConstants.VISUAL_PIECE_HEIGHT * solution[0].length);
    }

    for (int x = 0; x < solution.length; x++) {
      for (int y = 0; y < solution[x].length; y++) {
        Piece piece = solution[x][y];

        if (piece != null) {
          gc.setStroke(colors[(y * solution.length + x) % colors.length]);
          gc.setFill(Color.BLUEVIOLET);

          if (dynamicSize) {
            gc.setLineWidth(1);
          }

          PointsBuilder xs = new PointsBuilder();
          PointsBuilder ys = new PointsBuilder();

          for (Direction direction : Direction.values()) {
            Side s = piece.getSide(direction);
            if (s != null) {
              Point[] points = ((SimpleSide) s).getPoints();
              for (Point point : points) {
                Point globalPoint
                    = SimpleVisualPuzzleRenderer.globalPointFromLocalPoint(point, direction, x, y);
                xs.add(globalPoint.x);
                ys.add(globalPoint.y);
              }
            }
          }
          double[] primitiveXs = ArrayUtils.toPrimitive(xs.toPoints());
          double[] primitiveYs = ArrayUtils.toPrimitive(ys.toPoints());
          gc.fillPolygon(primitiveXs, primitiveYs, xs.size());
          gc.strokePolygon(primitiveXs, primitiveYs, xs.size());
        }
      }
    }
  }

  private class MouseEventHandler implements EventHandler<MouseEvent> {

    private final SteppableAnimationTimer timer;

    public MouseEventHandler(SteppableAnimationTimer timer) {
      this.timer = timer;
    }

    @Override
    public void handle(MouseEvent event) {
      timer.nextStep();
    }
  }

  private class SteppableAnimationTimer extends AnimationTimer {

    private final Solver solver;
    private final GraphicsContext gc;

    public SteppableAnimationTimer(Solver solver, GraphicsContext gc) {
      this.solver = solver;
      this.gc = gc;
    }

    public void nextStep() {
      solver.nextStep();
      System.out.println(Arrays.deepToString(solver.getSolution()));
    }

    @Override
    public void handle(long now) {
      drawPuzzle(gc, solver);
    }
  }

}
