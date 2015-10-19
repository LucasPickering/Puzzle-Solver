package puzzlesolver.side;

import puzzlesolver.Point;

public final class SimpleSide implements Side {

  private final Point[] points;
  private final SideType sideType;

  public SimpleSide(Point... points){
    this.points = points.clone();
    sideType = findSideType();
  }

  private Side.SideType findSideType(){
    return null; // TODO
  }

  @Override
  public int compareTo(Side side) {
    return 0; // TODO
  }

  @Override
  public float getCornerDistance() {
    return 0; // TODO
  }

  @Override
  public SideType getSideType() {
    return sideType;
  }
}
