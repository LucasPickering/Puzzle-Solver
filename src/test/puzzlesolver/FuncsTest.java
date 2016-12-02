package puzzlesolver;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FuncsTest {

    private List<Integer> intList1;
    private List<Integer> intList2;
    private List<Integer> intList3;
    private List<Integer> intList4;
    private List<Integer> intList5;

    @Before
    public void setUp() {
        intList1 = Collections.singletonList(1);
        intList2 = Arrays.asList(1, 5);
        intList3 = Arrays.asList(0, 1, 2, 3);
        intList4 = Arrays.asList(0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3);
        intList5 = Arrays.asList(0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 5);
    }

    @Test
    public void testExpSearch1() {
        assertEquals(0, Funcs.expSearch(intList1, Comparator.naturalOrder(), 0, true));
        assertEquals(0, Funcs.expSearch(intList1, Comparator.naturalOrder(), 0, false));
    }

    @Test
    public void testExpSearch2() {
        assertEquals(0, Funcs.expSearch(intList2, Comparator.naturalOrder(), 0, true));
        assertEquals(0, Funcs.expSearch(intList2, Comparator.naturalOrder(), 0, false));

        assertEquals(1, Funcs.expSearch(intList2, Comparator.naturalOrder(), 1, true));
        assertEquals(1, Funcs.expSearch(intList2, Comparator.naturalOrder(), 1, false));
    }

    @Test
    public void testExpSearch3() {
        assertEquals(0, Funcs.expSearch(intList3, Comparator.naturalOrder(), 0, true));
        assertEquals(0, Funcs.expSearch(intList3, Comparator.naturalOrder(), 0, false));

        assertEquals(1, Funcs.expSearch(intList3, Comparator.naturalOrder(), 1, true));
        assertEquals(1, Funcs.expSearch(intList3, Comparator.naturalOrder(), 1, false));

        assertEquals(2, Funcs.expSearch(intList3, Comparator.naturalOrder(), 2, true));
        assertEquals(2, Funcs.expSearch(intList3, Comparator.naturalOrder(), 2, false));

        assertEquals(3, Funcs.expSearch(intList3, Comparator.naturalOrder(), 3, true));
        assertEquals(3, Funcs.expSearch(intList3, Comparator.naturalOrder(), 3, false));
    }

    @Test
    public void testExpSearch4() {
        assertEquals(0, Funcs.expSearch(intList4, Comparator.naturalOrder(), 0, true));
        assertEquals(2, Funcs.expSearch(intList4, Comparator.naturalOrder(), 0, false));

        assertEquals(0, Funcs.expSearch(intList4, Comparator.naturalOrder(), 1, true));
        assertEquals(2, Funcs.expSearch(intList4, Comparator.naturalOrder(), 1, false));

        assertEquals(0, Funcs.expSearch(intList4, Comparator.naturalOrder(), 2, true));
        assertEquals(2, Funcs.expSearch(intList4, Comparator.naturalOrder(), 2, false));

        assertEquals(3, Funcs.expSearch(intList4, Comparator.naturalOrder(), 4, true));
        assertEquals(5, Funcs.expSearch(intList4, Comparator.naturalOrder(), 4, false));

        assertEquals(6, Funcs.expSearch(intList4, Comparator.naturalOrder(), 7, true));
        assertEquals(8, Funcs.expSearch(intList4, Comparator.naturalOrder(), 7, false));

        assertEquals(9, Funcs.expSearch(intList4, Comparator.naturalOrder(), 10, true));
        assertEquals(11, Funcs.expSearch(intList4, Comparator.naturalOrder(), 10, false));
    }

    @Test
    public void testExpSearch5() {
        for (int i = 2; i <= 13; i++) {
            assertEquals(2, Funcs.expSearch(intList5, Comparator.naturalOrder(), i, true));
            assertEquals(13, Funcs.expSearch(intList5, Comparator.naturalOrder(), i, false));
        }
    }
}
