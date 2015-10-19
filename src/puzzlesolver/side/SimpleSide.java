package puzzlesolver.side;

import java.util.Arrays;

import puzzlesolver.Point;

public final class SimpleSide implements Side {

  /**
   * List of points on the side.
   *
   * points[i].x = distance from left side
   * points[i].y = height from side base
   */
  private final Point[] points;
  private final SideType sideType;

  public SimpleSide(Point... points){
    this.points = points.clone();
    Arrays.sort(this.points, 0, this.points.length, Point.PointComparator.class);
    sideType = findSideType();
  }

  private SideType findSideType(){
    return sideType;
  }

  @Override
  public int compareTo(Side side) {
    return
  }

  @Override
  public double getCornerDistance() {
    return points[points.length - 1].x;
  }

  @Override
  public SideType getSideType() {
    return sideType;
  }
}
