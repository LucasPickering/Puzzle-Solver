package puzzlesolver.test.piecelist;

import org.junit.Before;
import org.junit.Test;

import puzzlesolver.constants.Constants;
import puzzlesolver.generator.Generator;
import puzzlesolver.PieceComparator;
import puzzlesolver.piecelist.PieceList;
import puzzlesolver.enums.Direction;
import puzzlesolver.generator.SimpleGenerator;
import puzzlesolver.piecelist.SimplePieceList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimplePieceListTest {

  private final PieceList empty = new SimplePieceList();
  private final PieceList oneByOne = new SimplePieceList(1);
  private final PieceList fourByFour = new SimplePieceList(16);
  private final PieceList bigList = new SimplePieceList(900);

  @Before
  public void setUp() {
    Constants.RANDOM.setSeed("SimplePieceListTest".hashCode());
    Generator gen = new SimpleGenerator();
    oneByOne.addAll(gen.generate(1, 1));
    fourByFour.addAll(gen.generate(4, 4));
    bigList.addAll(gen.generate(30, 30));
  }

  @Test
  public void testEmpty() {
    assertTrue(empty.isEmpty());
    assertFalse(oneByOne.isEmpty());
    assertFalse(fourByFour.isEmpty());
    assertFalse(bigList.isEmpty());
  }

  @Test
  public void testSize() {
    assertEquals(0, empty.size());
    assertEquals(1, oneByOne.size());
    assertEquals(16, fourByFour.size());
    assertEquals(900, bigList.size());
  }

  @Test
  public void testClear() {
    empty.clear();
    oneByOne.clear();
    fourByFour.clear();
    bigList.clear();
    assertTrue(empty.isEmpty());
    assertTrue(oneByOne.isEmpty());
    assertTrue(fourByFour.isEmpty());
    assertTrue(bigList.isEmpty());
  }

  @Test
  public void testSorted() {
    for (Direction dir : Direction.values()) {
      for (int i = 0; i < bigList.size() - 1; i++) {
        assertTrue(new PieceComparator(dir).compare(bigList.get(dir, i),
                                                    bigList.get(dir, i + 1)) <= 0);
      }
    }
  }
}
