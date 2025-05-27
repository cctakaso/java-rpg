package rpg.utils;
public class Xy implements Cloneable{
  public int x;
  public int y;

  public Xy() {
    this.x = 0;
    this.y = 0;
  }
  public Xy(String str) {
    if (str != null) {
      int index = str.indexOf(".");
      this.x = Integer.parseInt(str.substring(0, index-1));
      this.y = Integer.parseInt(str.substring(index+1));
    }
  }
  public Xy(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public Xy clone() {
    Xy copy = null;
    try {
      copy = (Xy)super.clone();
      copy.x = this.x;
      copy.y = this.y;
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

}
