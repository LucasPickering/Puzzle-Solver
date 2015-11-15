package puzzlesolver.ui.console;

public interface TextView {

  /**
   * Generate a console view of the puzzle.
   *
   * Each line of the console should go in its own array index. (draw()[0] should be the first
   * line, and so on). Do not append newline characters or breaks to the output.
   *
   * @return a text representation of the current puzzle view.
   */
  String[] draw();
}
