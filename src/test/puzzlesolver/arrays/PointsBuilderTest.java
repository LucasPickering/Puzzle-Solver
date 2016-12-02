package puzzlesolver.arrays;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import puzzlesolver.arrays.PointsBuilder;

public class PointsBuilderTest {

    private final PointsBuilder pb = new PointsBuilder();
    private final PointsBuilder pb2 = new PointsBuilder(1d, 2d, 3d);

    @Test
    public void testAdd() throws Exception {
        pb.addAll(1d, 2d, 3d);
        Assert.assertEquals(3, pb.size());
    }

    @Test
    public void testToPoints() throws Exception {
        Assert.assertTrue(Arrays.equals(new Double[]{1d, 2d, 3d}, pb2.toPoints()));
        Assert.assertTrue(Arrays.equals(new Double[]{}, pb.toPoints()));
        pb2.addAll(4d, 5d);
        Assert.assertTrue(Arrays.equals(new Double[]{1d, 2d, 3d, 4d, 5d}, pb2.toPoints()));
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertEquals(pb2.size(), pb2.toPoints().length);
        Assert.assertEquals(pb.size(), pb.toPoints().length);
        pb.addAll(4d, 5d);
        Assert.assertEquals(pb.size(), pb.toPoints().length);
    }
}
