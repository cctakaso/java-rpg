package rpg.objects;

import java.util.Scanner;

/**
 * 敵キャラクターを表すクラス。
 * <p>
 * 敵キャラクターは、プレイヤーと戦うことができるキャラクターです。
 * </p>
 */
public class EnemyCharacter extends Character{
  /**
   * 敵キャラクターのコンストラクタ。
   * <p>
   * 引数として渡されたCharacterオブジェクトのプロパティをコピーして初期化します。
   * </p>
   * @param character コピー元のCharacterオブジェクト
   */
  public EnemyCharacter(Character character) {
    this();
    super.copy(character);
  }
  public EnemyCharacter() {
  }

  /**
   * 敵キャラクターと出会う処理を行います。
   * <p>
   * 敵キャラクターとの出会いをシミュレートし、プレイヤーに選択肢を提示します。
   * プレイヤーが敵キャラクターと戦うかどうかを選択できます。
   * </p>
   * @param scan 入力を受け付けるScannerオブジェクト
   * @param myParty プレイヤーのパーティ
   * @return 出会いの結果（true: 戦う、false: 逃げる）
   */
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
