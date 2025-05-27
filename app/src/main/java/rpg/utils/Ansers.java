package rpg.utils;

import java.util.*;

import rpg.types.Defs;
import rpg.utils.*;

public class Ansers extends ArrayList<Anser>{
  public boolean add(Anser anser) {
    anser.setIndex(this.size());
    super.add(anser);
    return true;
  }

  public Anser printChoice(Scanner scan, String name, boolean isPutTaileZero) {
    String str = "";
    if (isPutTaileZero) {
      for(int index=1; index<this.size(); index++) {
        str += index + ":" + this.get(index).printing + " ";
      }
      if (this.size()>0) {
        str += "0:" + this.get(0).printing;
      }
    }else{
      for(int index=0; index<this.size(); index++) {
        str += index+1 + ":" + this.get(index).printing + " ";
      }
    }
    if (scan!=null) {
      System.out.println(str);
    }
    return choice(scan, name, isPutTaileZero);
  }

  private Anser choice(Scanner scan, String name, boolean isPutTaileZero) {
    int choice = 0;
    if (scan == null) {
      Random random = new Random();
      choice = random.nextInt(this.size());
    }else{
      System.out.println("次のどれを選びますか？");
      while(this.size()>0) {
        try{
          System.out.print(name!=null ? name+">":">");
          choice = scan.nextInt();
          if (!isPutTaileZero) {
            choice--;
          }
          if (choice >= 0 && choice < this.size()) {
            break;
          }
        }catch (Exception e){}
        System.out.println("正しい番号を入力して下さい");
      }
    }
    return this.get(choice);
  }
}
