package puzzlesolver.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import puzzlesolver.PointsBuilder;

public class PointsBuilderTest {

  PointsBuilder pb = new PointsBuilder();
  PointsBuilder pb2 = new PointsBuilder(1, 2, 3);

  @Test
  public void testAdd() throws Exception {
    pb.add(1, 2, 3);
    Assert.assertEquals(3, pb.size());
  }

  @Test
  public void testToPoints() throws Exception {
    Assert.assertTrue(Arrays.equals(new double[]{1, 2, 3}, pb2.toPoints()));
    Assert.assertTrue(Arrays.equals(new double[]{}, pb.toPoints()));
    pb2.add(4, 5);
    Assert.assertTrue(Arrays.equals(new double[]{1, 2, 3, 4, 5}, pb2.toPoints()));
  }

  @Test
  public void testSize() throws Exception {
    Assert.assertEquals(pb2.size(), pb2.toPoints().length);
    Assert.assertEquals(pb.size(), pb.toPoints().length);
    pb.add(4, 5);
    Assert.assertEquals(pb.size(), pb.toPoints().length);
  }
}
