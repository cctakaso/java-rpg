package rpg.model.objects;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import rpg.App;

/**
 * クルーキャラクターを表すクラス。
 * <p>
 * クルーキャラクターは、キャラクターの基本的なプロパティを持ち、
 * パーティに参加することができます。
 * </p>
 */
public class CrewCharacter extends Character{


  /**
   * デフォルトコンストラクタ。
   * <p>
   * クルーキャラクターのプロパティを初期化します。
   * </p>
   */
  public CrewCharacter() {
  }


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
   * クルーキャラクターと出会う処理を行います。
   * <p>
   * キャラクターとの出会いをシミュレートし、プレイヤーに選択肢を提示します。
   * プレイヤーがキャラクターを仲間にするかどうかを選択できます。
   * </p>
   * @param scanner 入力を受け付けるScannerオブジェクト
   * @param myParty プレイヤーのパーティ
   * @return 出会いの結果（true: 仲間になった、false:
   * 出会いを拒否した）
   */
  public boolean meet(Scanner scanner, Party myParty) {

    App.view.printMessage("met_character", this);
    this.talks.print(scanner, myParty, this);
    App.view.printMessage(this.charStatus.toString());
    App.view.printMessage("choice_join_or_leave");
    while(true) {
      try{
        System.out.print(myParty.getLeaderName()+">");
        int num = scanner.nextInt();
        if (num == 1) {
          myParty.addCharacter(this);
          App.view.printMessage("character_joined", this);
          break;
        }else if (num==0) {
          break;
        }
      }catch (InputMismatchException ex){
        scanner.nextLine(); // 入力バッファをクリア
      } catch (NoSuchElementException e) {
        App.view.printMessage("no_input_try_again");
        //scanner.next(); // 無効な入力をスキップ
        System.exit(-1);
      }catch(Exception ex) {
        ex.printStackTrace();
        System.err.println(ex.toString());
        System.exit(-1);
      }
      App.view.printMessage("enter_correct_number");
    }
    return true;
  }
}
