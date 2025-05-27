package rpg.utils;

public class Util {
  public static int valueOf(String str) {
    try{
      return Integer.valueOf(str);
    }catch(Exception ex) {
    }
    return -1;
  }
}
