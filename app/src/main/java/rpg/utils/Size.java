package rpg.utils;

public class Size extends Xy {
  public Size() {
    super();
  }

  public Size(String str) {
    super(str);
  }
  public Size(int x, int y) {
    super(x, y);
  }
  public Size clone() {
    Size copy = null;
    try {
      copy = (Size)super.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }
  public boolean isWithinArea(Pt start, Pt check) {
    int cx = this.x + start.x;
    int cy = this.y + start.y;
    return (start.x<= check.x && check.x < cx) &&
          (start.y<= check.y && check.y < cy);
  }

}
