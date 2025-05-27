package rpg.utils;

import java.util.*;

import rpg.objects.Base;

public class Dic extends HashMap<String, HashMap<String, Base>>{

  public List<Base> getClones(String type, String name, int number, Pt randomPt) {
    if (type == "Parties") {
      System.out.println();
    }
    List<Base> list = new ArrayList<Base>();
    try {
      Base one = get(type, name);
      if (one != null) {
        //Resource.scanClasses(one);
        one.scanClasses();
        do {
          list.add(one.clone(number, randomPt));
        }while(--number >0);
        return list;
      }
    }catch(Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  //-----------------------------------------//
  public Base get(String type, String name) {
    try {
      return this.get(type).get(name);
    }catch(Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

}
