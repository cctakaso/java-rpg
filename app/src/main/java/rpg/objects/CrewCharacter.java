package rpg.objects;

import java.util.Scanner;


/**
 * クルーキャラクターを表すクラス。
 * <p>
 * クルーキャラクターは、キャラクターの基本的なプロパティを持ち、
 * パーティに参加することができます。
 * </p>
 */
public class CrewCharacter extends Character{
  /**
   * クルーキャラクターを指定して初期化します。
   * <p>
   * 引数として渡されたCharacterオブジェクトのプロパティをコピーして、
   * 新しいクルーキャラクターを作成します。
   * </p>
   * @param character コピー元のCharacterオブジェクト
   */
  public CrewCharacter(Character character) {
    this();
    super.copy(character);
  }

  /**
   * デフォルトコンストラクタ。
   * <p>
   * クルーキャラクターのプロパティを初期化します。
   * </p>
   */
  public CrewCharacter() {
  }

  /**
   * クルーキャラクターと出会う処理を行います。
   * <p>
   * キャラクターとの出会いをシミュレートし、プレイヤーに選択肢を提示します。
   * プレイヤーがキャラクターを仲間にするかどうかを選択できます。
   * </p>
   * @param scan 入力を受け付けるScannerオブジェクト
   * @param myParty プレイヤーのパーティ
   * @return 出会いの結果（true: 仲間になった、false:
   * 出会いを拒否した）
   */
  public boolean meet(Scanner scan, Party myParty) {

    System.out.println(this.name+"と出会いました。");
    this.talks.print(scan, myParty, this);
    System.out.println(this.charStatus.toString());
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
