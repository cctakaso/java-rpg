package rpg.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import rpg.types.ItemType;
import rpg.utils.*;

public class CrewCharacter extends Character{
  public CrewCharacter(Character character) {
    this();
    super.copy(character);
  }
  public CrewCharacter() {
  }

  public boolean meet(Scanner scan, Party myParty) {

    System.out.println(this.name+"と出会いました。");
    this.talks.print(scan, myParty, this);
    System.out.println(this.charStatus.toPrinting());
    System.out.println("1:仲間になる  0:バイバイする");
    while(true) {
      try{
        System.out.print(myParty.getLeaderName()+">");
        int num = scan.nextInt();
        if (num == 1) {
          myParty.addCharacter(this);
          System.out.println(this.name+"が仲間になりました。");
          break;
        }else if (num==0) {
          break;
        }
      }catch (Exception e){}
      System.out.println("正しい番号を入力して下さい");
    }
    return true;
  }
}
