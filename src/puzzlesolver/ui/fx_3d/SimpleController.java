package puzzlesolver.ui.fx_3d;

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
import puzzlesolver.Solver;
import puzzlesolver.constants.UIConstants;
import puzzlesolver.simple.SimpleGenerator;
import puzzlesolver.simple.SimpleSolver;
import puzzlesolver.ui.fx_2d.PuzzleRenderer;

public class SimpleController extends Application {

  private static final int WIDTH = 4;
  private static final int HEIGHT = 4;
  private Paint[] colors = new Paint[]{Color.NAVY, Color.DARKSLATEBLUE,
                                       Color.DARKGOLDENROD, Color.DARKOLIVEGREEN,
                                       Color.MAROON, Color.DARKVIOLET,
                                       Color.DARKCYAN, Color.DARKORCHID};
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

    primaryStage.setMinWidth(UIConstants.WINDOW_MIN_WIDTH);
    primaryStage.setMinHeight(UIConstants.WINDOW_MIN_HEIGHT);
    Canvas canvas = new Canvas(UIConstants.WINDOW_MIN_WIDTH, UIConstants.WINDOW_MIN_HEIGHT);

    GraphicsContext gc = canvas.getGraphicsContext2D();
    root.getChildren().add(canvas);
    primaryStage.setScene(new Scene(root));
    canvas.widthProperty().bind(primaryStage.getScene().widthProperty());
    canvas.heightProperty().bind(primaryStage.getScene().heightProperty());
    primaryStage.show();

    SteppableAnimationTimer timer = new SteppableAnimationTimer(solver, gc);
    primaryStage.getScene().setOnMouseClicked(new MouseEventHandler(timer));
    timer.start();
  }

  private void drawPuzzle(GraphicsContext gc, Solver solver) {
    PuzzleRenderer pr = new PuzzleRenderer();
    pr.drawPuzzle(gc, solver);
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
    }

    @Override
    public void handle(long now) {
      drawPuzzle(gc, solver);
    }
  }

}
