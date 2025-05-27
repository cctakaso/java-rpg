package rpg;

import java.util.*;

import rpg.objects.*;
import rpg.utils.*;

public class Adventure {
  static Dic dic;
  static Dic map;

  public static void initialize(String path) {
    dic = new Dic();
    map = new Dic();
    Resource.load(dic, "/dic");
    Resource.load(map, path);
    System.out.println("initialize end");
  }


  public static List<Base> getDicClones(String type, String name) {
    return getDicClones(type, name, 0, null);
  }

  public static List<Base> getDicClones(String type, String name, int number, Pt randomPt) {
    return dic.getClones(type, name, number, randomPt);
  }

  public static void start(String title) {
    System.out.println(title+"を始めます。");
    Scanner scan = new Scanner(System.in);
    Party myParty = (Party)map.get("Parties", "勇者パーティ");
    Fields allFields = (Fields)map.get("Fields", "全体マップ");
    //Fields beginFields = allFields.children.get(0);
    //Party myParty = beginFields.parties.children.get(0);
    Fields myField = null;
    do {
      System.out.println();
      allFields.toPrinting(myParty);
      Map<String, Object> map = allFields.hitField(myParty.getPt());
      Fields tmp = (Fields)map.get("hitField");
      if (tmp != myField) {
        if (myField != null) {
          ArrayList<Talk> talks = (ArrayList<Talk>)myField.getTalks().getList();
          if (talks!=null && talks.size()>0) {
            Talk talk = talks.get(0);
            talk.printAfter(scan);
          }
        }
        myField = tmp;
        ArrayList<Talk> talks = (ArrayList<Talk>)myField.getTalks().getList();
        if (talks!=null && talks.size()>0) {
          Talk talk = talks.get(0);
          talk.printBefore(scan);
        }
      }
      Pt orginPt = (Pt)map.get("orginPt");
      System.out.println((String)map.get("toPrinting"));
      List<Event> events = myField.getHitEvents(myParty, myParty.getPt(), orginPt);
      if (events != null) {
        for (Event event: events) {
          System.out.println();
          if (myParty.event(scan, event)) {
            myField.removeEvent(myParty.getPt(), orginPt, event);
          }
        }
      }
      System.out.println();
      System.out.println(myParty.toPrinting(true));
      Pt pt = myParty.walk(scan, allFields.getPt(), allFields.getSize());
      if (pt == null) {
        System.out.println("冒険を終了します。");
        break;
      }
      myParty.setPt(pt);
    }while(true);
  }


}

