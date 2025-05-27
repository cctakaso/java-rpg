package rpg.utils;
import java.util.*;
public class DicList<E> extends ArrayList<E>{
  public <E> E getE(int index) {
    return (E)super.get(index);
  }
}
