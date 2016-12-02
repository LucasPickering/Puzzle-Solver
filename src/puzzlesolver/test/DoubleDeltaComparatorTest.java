package puzzlesolver.test;

import org.junit.Test;

import puzzlesolver.DoubleDeltaComparator;

import static org.junit.Assert.assertEquals;

public class DoubleDeltaComparatorTest {

    private DoubleDeltaComparator comp = new DoubleDeltaComparator(0.1d);

    @Test
    public void testCompare1() {
        assertEquals(-1, comp.compare(-5d, 5d));
        assertEquals(0, comp.compare(5d, 5d));
        assertEquals(1, comp.compare(5d, -5d));
    }

    @Test
    public void testCompare2() {
        assertEquals(-1, comp.compare(-5d, 5d));
        assertEquals(-1, comp.compare(4.8999d, 5d));
        assertEquals(0, comp.compare(4.9d, 5d));
        assertEquals(0, comp.compare(4.95d, 5d));
        assertEquals(0, comp.compare(5d, 5d));
        assertEquals(0, comp.compare(5.05d, 5d));
        assertEquals(0, comp.compare(5.1d, 5d));
        assertEquals(1, comp.compare(5.100001d, 5d));
        assertEquals(1, comp.compare(5d, -5d));
    }
}
