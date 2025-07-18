package rpg.utils;

import java.util.*;

import rpg.types.Defs;
import rpg.utils.*;

public class Answers<T>{
  private final List<Answer<T>> list = new ArrayList<>();

  public void add(Answer<T> answer) {
      answer.setIndex(list.size());
      list.add(answer);
  }

  public int size() {
      return list.size();
  }

  public Answer<T> get(int index) {
      return list.get(index);
  }


  public List<Answer<T>> getList() {
      return list;
  }


  public Answer<T> printChoice(Scanner scan, String name, boolean isPutTaileZero) {
    String str = "";
    if (isPutTaileZero) {
      for(int index=1; index<this.size(); index++) {
        str += index + ":" + this.get(index).getLabel() + " ";
      }
      if (this.size()>0) {
        str += "0:" + this.get(0).getLabel();
      }
    }else{
      for(int index=0; index<this.size(); index++) {
        str += index+1 + ":" + this.get(index).getLabel() + " ";
      }
    }
    if (scan!=null) {
      System.out.println(str);
    }
    return choice(scan, name, isPutTaileZero);
  }

  private Answer<T> choice(Scanner scan, String name, boolean isPutTaileZero) {
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

  /**
   * 選択肢を表示し、ユーザに選ばせる
   * @param scan 入力Scanner
   * @param prompt プロンプト文字列
   * @return 選ばれたAnswer
  public Answer<T> printChoice(Scanner scan, String prompt) {
      for (int i = 0; i < list.size(); i++) {
          System.out.printf("%d: %s ", i + 1, list.get(i).getLabel());
      }
      System.out.println();
      int choice = 0;
      while (true) {
          try {
              System.out.print(prompt != null ? prompt + ">" : ">");
              choice = scan.nextInt();
              if (choice >= 1 && choice <= list.size()) {
                  break;
              }
          } catch (Exception e) {
              System.out.println("正しい番号を入力して下さい");
              scan.next(); // 入力バッファクリア
          }
      }
      return list.get(choice - 1);
  }
   */

  /**
   * ランダムに選択肢を選ぶ
   * @return ランダム選択されたAnswer
  public Answer<T> randomChoice() {
      if (list.isEmpty()) return null;
      Random random = new Random();
      return list.get(random.nextInt(list.size()));
  }
   */
/*
  public boolean add(Answer anser) {
    anser.setIndex(this.size());
    super.add(anser);
    return true;
  }
 */


}
