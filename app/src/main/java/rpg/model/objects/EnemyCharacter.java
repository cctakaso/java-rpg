package rpg.model.objects;

import java.util.Scanner;

import rpg.App;

/**
 * 敵キャラクターを表すクラス。
 * <p>
 * 敵キャラクターは、プレイヤーと戦うことができるキャラクターです。
 * </p>
 */
public class EnemyCharacter extends Character{

  /**
   * デフォルトコンストラクタ。
   * <p>
   * 敵キャラクターのプロパティを初期化します。
   * </p>
   */
  public EnemyCharacter() {
  }

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

  /**
   * 敵キャラクターと出会う処理を行います。
   * <p>
   * 敵キャラクターとの出会いをシミュレートし、プレイヤーに選択肢を提示します。
   * プレイヤーが敵キャラクターと戦うかどうかを選択できます。
   * </p>
   * @param scanner 入力を受け付けるScannerオブジェクト
   * @param myParty プレイヤーのパーティ
   * @return 出会いの結果（true: 戦う、false: 逃げる）
   */
  public boolean meet(Scanner scanner, Party myParty) {
    this.talks.print(scanner, myParty, this);
    App.view.printMessage("choice_fight_or_flee");
    int num=0;
    while(true) {
      try{
        System.out.print(myParty.getLeaderName()+">");
        num = scanner.nextInt();
        if (num ==1 || num == 0) {
          break;
        }
      }catch (Exception ex){
        ex.printStackTrace();
        System.err.println(ex.getMessage());
        //scanner.nextLine(); // 入力バッファをクリア
      }
      App.view.printMessage("wrong_number");
    }
    return num==1;
  }


}