package rpg.utils;

public class Anser {
  public int index;
  public String printing;
  public Object object;
  public String infomation;

  public Anser() {
    this(new String(), null, new String());
  }

  public Anser(String printing) {
    this(printing, null, "");
  }
  public Anser(String printing, Object object) {
    this(printing, object, "");
  }
  public Anser(String printing, Object object, String infomation) {
    //this.index = 0;
    this.printing = printing;
    this.object = object;
    this.infomation = infomation;
  }

  public void setIndex(int index) {
    this.index = index;
  }

}
