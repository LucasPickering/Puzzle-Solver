package puzzlesolver.test.simple;

import org.junit.Before;
import org.junit.Test;

import puzzlesolver.Generator;
import puzzlesolver.PieceComparator;
import puzzlesolver.PieceList;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimpleGenerator;
import puzzlesolver.simple.SimplePieceList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimplePieceListTest {

  private final PieceList empty = new SimplePieceList();
  private final PieceList oneByOne = new SimplePieceList();
  private final PieceList fourByFour = new SimplePieceList();
  private final PieceList bigList = new SimplePieceList();

  @Before
  public void setUp() {
    Generator gen = new SimpleGenerator();
    gen.setSeed("SimplePieceListTest");
    oneByOne.addAll(gen.generate(1, 1));
    fourByFour.addAll(gen.generate(4, 4));
    bigList.addAll(gen.generate(30, 30));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFirstOob() {
    empty.first(Direction.NORTH);
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
