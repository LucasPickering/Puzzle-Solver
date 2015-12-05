package puzzlesolver.test.polypoint;

import org.junit.Test;

import puzzlesolver.Generator;
import puzzlesolver.polypoint.PolypointGenerator;

public class PolypointGeneratorTest {

  @Test
  public void testGenerate5() {
    Generator generator = new PolypointGenerator(0.2d, 0.2d);
    generator.generate(100, 100);
  }

  @Test
  public void testGenerate10() {
    Generator generator = new PolypointGenerator(0.1d, 0.1d);
    generator.generate(100, 100);
  }

  @Test
  public void testGenerate20() {
    Generator generator = new PolypointGenerator(0.05d, 0.05d);
    generator.generate(100, 100);
  }

  @Test
  public void testGenerate100() {
    Generator generator = new PolypointGenerator(0.01d, 0.01d);
    generator.generate(100, 100);
  }

  @Test
  public void testGenerate500() {
    Generator generator = new PolypointGenerator(0.002d, 0.002d);
    generator.generate(100, 100);
  }
}
