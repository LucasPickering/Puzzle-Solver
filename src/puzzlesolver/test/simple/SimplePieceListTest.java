package puzzlesolver.test.simple;

import org.junit.Before;
import org.junit.Test;

import puzzlesolver.PieceList;
import puzzlesolver.enums.Direction;
import puzzlesolver.simple.SimplePieceList;

import static org.junit.Assert.*;

public class SimplePieceListTest {

  private final PieceList emptyList = new SimplePieceList();
  private final PieceList oneElementList = new SimplePieceList();
  private final PieceList twoElementList = new SimplePieceList();

  @Before
  public void setup() {
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFirstOob(){
    emptyList.first(Direction.NORTH);
  }

  @Test
  public void testEmpty() {
    assertTrue(emptyList.isEmpty());
    assertFalse(oneElementList.isEmpty());
    assertFalse(twoElementList.isEmpty());
  }

  @Test
  public void testSize() {
    assertEquals(0, emptyList.size());
    assertEquals(1, oneElementList.size());
    assertEquals(2, twoElementList.size());
  }
}
