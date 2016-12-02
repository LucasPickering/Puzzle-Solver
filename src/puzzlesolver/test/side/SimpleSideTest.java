package puzzlesolver.test.side;

import org.junit.Test;

import puzzlesolver.Point;
import puzzlesolver.enums.SideType;
import puzzlesolver.side.Side;
import puzzlesolver.side.SimpleSide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SimpleSideTest {

    Side s2 = new SimpleSide(new Point(0, 0),
                             new Point(5, 1),
                             new Point(10, 0));
    Side s3 = new SimpleSide(new Point(0, 0),
                             new Point(8.1, 0));
    Side s4 = new SimpleSide(new Point(0, 0),
                             new Point(5, 1),
                             new Point(10, 0));
    Side s4_o = s4.inverse();

    @Test
    public void testCopy() throws Exception {
        assertFalse(s2 == s2.copy());
        assertTrue(s2.compareTo(s2.copy()) == 0);
        assertFalse(s3 == s3.copy());
        assertTrue(s3.compareTo(s3.copy()) == 0);
        assertFalse(s4 == s4.copy());
        assertTrue(s4.compareTo(s4.copy()) == 0);
        assertFalse(s4_o == s4_o.copy());
        assertTrue(s4_o.compareTo(s4_o.copy()) == 0);
    }

    @Test
    public void testToString() throws Exception {
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s4_o);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() throws Exception {
        new SimpleSide();
    }

    @Test
    public void testCompareTo() throws Exception {
        assertEquals(0, s2.compareTo(s2));
        assertEquals(0, s3.compareTo(s3));
        assertNotEquals(0, s2.compareTo(s3));
        assertNotEquals(0, s3.compareTo(s2));
        assertEquals(0, s2.compareTo(s4));
        assertEquals(0, s4.compareTo(s2));
        assertNotEquals(0, s4.compareTo(s4_o));
        assertNotEquals(0, s4_o.compareTo(s4));
        assertNotEquals(0, s2.compareTo(s4_o));
        assertNotEquals(0, s4_o.compareTo(s2));
    }

    @Test
    public void testGetCornerDistance() throws Exception {
        assertEquals(10, s2.getCornerDistance(), 0.01);
        assertEquals(10, s2.inverse().getCornerDistance(), 0.01);
        assertEquals(8.1, s3.getCornerDistance(), 0.01);
        assertEquals(8.1, s3.inverse().getCornerDistance(), 0.01);
    }

    @Test
    public void testGetSideType() throws Exception {
        assertEquals(SideType.OUT, s2.getSideType());
        assertEquals(SideType.IN, s2.inverse().getSideType());
        assertEquals(SideType.FLAT, s3.getSideType());
        assertEquals(SideType.FLAT, s3.inverse().getSideType());
        assertEquals(SideType.OUT, s4.getSideType());
        assertEquals(SideType.IN, s4_o.getSideType());
    }

    @Test
    public void testGetPointCount() throws Exception {

    }
}
