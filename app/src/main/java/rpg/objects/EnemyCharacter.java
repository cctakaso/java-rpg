package rpg.objects;


import java.util.HashMap;
import java.util.Scanner;

import rpg.utils.*;

public class EnemyCharacter extends Character{
  public EnemyCharacter(Character character) {
    this();
    super.copy(character);
  }
  public EnemyCharacter() {
  }

  public boolean meet(Scanner scan, Party myParty) {
    this.talks.print(scan, myParty, this);
    System.out.println("1:戦う  0:逃げる");
    int num=0;
    while(true) {
      try{
        System.out.print(myParty.getLeaderName()+">");
        num = scan.nextInt();
        if (num ==1 || num == 0) {
          break;
        }
      }catch (Exception e){}
      System.out.println("番号が違います。");
    }
    return num==1;
  }


}
