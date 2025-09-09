package rpg.model.objects;

import java.util.ArrayList;
import java.util.Scanner;
import rpg.App;
import rpg.Adventure;
import rpg.model.types.ItemType;
import rpg.utils.*;

/**
 * NPCとの会話イベントを管理するクラスです。
 * 会話の前半部分、選択肢、および選択後の会話を保持します。
 */
public class Talk extends Base{
  protected ArrayList<String> befores;  // プレイヤーに提示される選択肢の前の会話文
  protected ArrayList<String> choices;  // プレイヤーが選択できる選択肢のリスト
  protected ArrayList<String> afters;   // プレイヤーが選択した後に表示される会話文

  /**
   * デフォルトコンストラクタ。
   * 会話の各パートを空のリストで初期化します。
   */
  public Talk() {
    super();
    this.befores = new ArrayList<String>();
    this.choices = new ArrayList<String>();
    this.afters = new ArrayList<String>();
  }

  /**
   * 会話の各パートを文字列配列で指定して、新しいTalkインスタンスを生成します。
   * @param befores 選択肢の前の会話文
   * @param choices 選択肢
   * @param afters 選択肢の後の会話文
   */
  public Talk(String [] befores, String [] choices, String [] afters) {
    this();
    for (String before: befores) {
      this.befores.add(before);
    }
    for (String choice: choices) {
      this.befores.add(choice);
    }
    for (String after: afters) {
      this.befores.add(after);
    }
  }

  /**
   * このTalkオブジェクトのクローンを、指定された番号とランダムな位置で作成します。
   * @param num クローン番号
   * @param randomPt ランダムな位置
   * @return Talkオブジェクトの新しいインスタンス
   */
  @SuppressWarnings("unchecked")
  public Base clone(int num, Pt randomPt) {
    Talk copy = null;
    try {
      copy = (Talk)super.clone(num, randomPt);
      copy.befores = (ArrayList<String>)befores.clone();
      copy.choices = (ArrayList<String>)choices.clone();
      copy.afters = (ArrayList<String>)afters.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * 選択肢の前の会話文を表示します。
   * @param scanner ユーザー入力（現在は未使用）
   */
  public void printBefore(Scanner scanner) {
    for (String before : befores) {
      App.view.printMessage(before);
    }
  }

  /**
   * 選択肢の後の会話文を表示します。
   * @param scanner ユーザー入力（現在は未使用）
   */
  public void printAfter(Scanner scanner) {
    for (String after : afters) {
      App.view.printMessage(after);
    }
  }

  /**
   * 選択肢に売買に関するものが含まれているかを確認します。
   * @return 売買の選択肢が含まれていればtrue
   */
  public boolean isSaleBuyChoice() {
    for(String choice : choices) {
      if (choice.indexOf(':')==0) {
        if (choice.equals(":buy.gears") || choice.equals(":buy.items") ||
            choice.equals(":sale.gears") || choice.equals(":sale.items")) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 選択肢リストから、プレイヤーに提示する回答の選択肢（Answers）を生成します。
   * @param choices 選択肢の文字列リスト
   * @param allyParty プレイヤーのパーティ
   * @param otherCharacter 会話相手のキャラクター
   * @return 生成されたAnswersオブジェクト
   */
  public Answers<Item> makeOptions(ArrayList<String> choices, Party allyParty, Character otherCharacter) {
    Answers<Item> ansers = new Answers<>();

    for (String choice : choices) {
      // ':'で始まる特殊コマンドを処理
      if (choice.indexOf(':')==0) {
        if (choice.equals(":buy.gears")) {
          ansers.add(otherCharacter.items.toAnswers(ItemType.Gear, choice));
        }else if (choice.equals(":buy.items")) {
          ansers.add(otherCharacter.items.toAnswers(ItemType.Item, choice));
        }else if (choice.equals(":sale.gears")) {
          ansers.add(allyParty.items.toAnswers(ItemType.Gear, choice));
        }else if (choice.equals(":sale.items")) {
          ansers.add(allyParty.items.toAnswers(ItemType.Item, choice));
        // '情報/選択肢' 形式の文字列を処理
        }else if (choice.indexOf('/')>0) {
          int n = choice.indexOf('/');
          ansers.add(new Answer<Item>(choice.substring(n+1), null, choice.substring(0, n)));
        }
      }else{
        // 通常の会話選択肢
        ansers.add(new Answer<Item>(choice));
      }
    }
    return ansers;
  }

  /**
   * 会話を実行し、プレイヤーの選択を取得します。
   * @param scanner ユーザー入力用のScanner
   * @param allyParty プレイヤーのパーティ
   * @param otherCharacter 会話相手のキャラクター
   * @return プレイヤーの選択に対応するAnswerオブジェクト
   */
  public Answer<Item> print(Scanner scanner, Party allyParty, Character otherCharacter) {
    Answer<Item> anser;

    printBefore(scanner);
    Answers<Item> ansers = makeOptions(choices, allyParty, otherCharacter);
    if (ansers.size()==0) {
      return null;
    }
    anser = ansers.printChoice(scanner, null, true);

    // 選択後の処理
    if (afters.size()>0) {
      int index = anser.getIndex();
      if (isSaleBuyChoice() && index>0)  {
        // 売買選択肢の場合、aftersのインデックスを調整
        index = 1;
      }
      String after = afters.get(index);
      // '>'で始まる場合は、別の会話に遷移
      if (after.charAt(0) == '>') {
        ArrayList<Base> list = (ArrayList<Base>)Adventure.getDicClones("Talks", after.substring(1));
        Talk talk = (Talk)list.get(0);
        anser = talk.print(scanner, allyParty, otherCharacter);
      }else{
        App.view.printMessage(after);
      }
    }

    return anser;
  }
}