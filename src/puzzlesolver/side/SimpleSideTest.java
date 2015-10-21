package puzzlesolver.side;

import org.junit.Test;

import puzzlesolver.Point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class SimpleSideTest {
  Side s1 = new SimpleSide();
  Side s2 = new SimpleSide(new Point(0, 0),
                           new Point(5, 1),
                           new Point(10, 0));
  Side s3 = new SimpleSide(new Point(0, 0),
                           new Point(8.1, 0));
  Side s4 = new SimpleSide(new Point(0, 0),
                           new Point(5, 1),
                           new Point(10, 0));

  @Test
  public void testCompareTo() throws Exception {
    assertEquals(0, s1.compareTo(s1));
    assertEquals(0, s2.compareTo(s2));
    assertEquals(0, s3.compareTo(s3));
    assertEquals(0, s1.compareTo(s4));
    assertNotEquals(0, s1.compareTo(s2));
    assertNotEquals(0, s2.compareTo(s1));
    assertNotEquals(0, s1.compareTo(s3));
    assertNotEquals(0, s3.compareTo(s1));
    assertNotEquals(0, s2.compareTo(s3));
    assertNotEquals(0, s3.compareTo(s2));
  }

  @Test
  public void testGetCornerDistance() throws Exception {
    assertEquals(0, s1.getCornerDistance(), 0.01);
    assertEquals(10, s2.getCornerDistance(), 0.01);
    assertEquals(8.1, s3.getCornerDistance(), 0.01);
  }

  @Test
  public void testGetSideType() throws Exception {
    assertNull(s1.getSideType());
    assertEquals(Side.SideType.OUT, s2.getSideType());
    assertEquals(Side.SideType.FLAT, s3.getSideType());
  }
}
