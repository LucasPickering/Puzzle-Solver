package puzzlesolver.ui.console;

import java.io.PrintStream;
import java.util.Scanner;

import puzzlesolver.Generator;
import puzzlesolver.Solver;
import puzzlesolver.simple.SimpleGenerator;
import puzzlesolver.simple.SimpleSolver;

public class ConsoleMain {

  public static void main(String[] args) {
    ConsoleMain consoleMain = new ConsoleMain();
      try {
        consoleMain.start(System.out);
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  /**
   * Time to wait between steps, in milliseconds.
   */
  private long timeInterval = 0;

  /**
   * Whether to require the user to hit <kbd>enter</kbd> on each step.
   */
  private boolean requireInputPerStep;

  public ConsoleMain(long timeInterval, boolean requireInputPerStep) {
    this.timeInterval = timeInterval;
    this.requireInputPerStep = requireInputPerStep;
  }

  public ConsoleMain() {
    requireInputPerStep = true;
  }

  public void start(PrintStream out) throws Exception {
    Generator generator = new SimpleGenerator();
    Solver solver = new SimpleSolver();
    TextView textView = new TextView(solver);
    Scanner in = new Scanner(System.in);
    int width, height;

    out.print("Puzzle Width: ");
    width = in.nextInt();
    out.print("Puzzle Height: ");
    height = in.nextInt();
    solver.init(generator.generate(width, height));

    do {
      String[] render = textView.draw();
      for (String line : render) {
        out.println(line);
      }
      out.println();

      if (requireInputPerStep) {
        in.nextLine();
      } else {
        Thread.sleep(timeInterval);
      }
      out.println("Solvin' away!");
    } while (!solver.nextStep());

    out.println("Done!");
    in.close();
  }
}
