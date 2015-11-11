package puzzlesolver.ui;

import java.io.PrintStream;
import java.util.Scanner;

import puzzlesolver.Generator;
import puzzlesolver.Solver;
import puzzlesolver.simple.SimpleGenerator;
import puzzlesolver.simple.SimpleSolver;

public class ConsoleController {

  /**
   * Time to wait between steps, in milliseconds.
   */
  private long timeInterval = 0;

  /**
   * Whether to require the user to hit <kbd>enter</kbd> on each step.
   */
  private boolean requireInputPerStep;

  public ConsoleController(long timeInterval, boolean requireInputPerStep) {
    this.timeInterval = timeInterval;
    this.requireInputPerStep = requireInputPerStep;
  }

  public ConsoleController() {
    requireInputPerStep = true;
  }

  public void start(PrintStream out) throws Exception {
    Generator generator = new SimpleGenerator();
    Solver solver = new SimpleSolver();
    Scanner in = new Scanner(System.in);
    int width, height;

    out.print("Puzzle Width: ");
    width = in.nextInt();
    out.print("Puzzle Height: ");
    height = in.nextInt();
    in.close();
    solver.init(generator.generate(width, height));

    do {
      // TODO render code

      if (requireInputPerStep) {
        in.nextLine();
      } else {
        Thread.sleep(timeInterval);
      }
    } while (!solver.nextStep());
  }
}
