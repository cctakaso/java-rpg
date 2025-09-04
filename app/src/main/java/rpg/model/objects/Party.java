package rpg.model.objects;
import java.util.*;

import rpg.App;
import rpg.model.types.*;
import rpg.utils.*;
import rpg.view.console.View;

/**
 * キャラクターの集まりである「パーティ」を表すクラスです。
 * パーティ全体の所持品、所持金、キャラクターの管理、およびイベント処理を担当します。
 */
public class Party extends Base{
  protected Characters characters;  // パーティに所属するキャラクターのリスト
  protected Items items;            // パーティが持つアイテムのリスト
  protected CharStatus charStatus;  // パーティ全体のステータス情報
  protected Talks talks;            // パーティに関連する会話のリスト

  /**
   * デフォルトコンストラクタ。
   * 空のパーティを生成します。
   */
  public Party() {
    super();
    this.characters = new Characters();
    this.items = new Items();
    this.charStatus = new CharStatus();
    this.talks = new Talks();
  }

  /**
   * 指定されたキャラクターで新しいパーティを生成します。
   * @param character 初期メンバーとなるキャラクター
   */
  public Party(Character character) {
    this();
    addCharacter(character);
  }

  /**
   * パーティのキャラクタリストを返します。
   * @return パーティに所属するキャラクタリスト
   */
  public List<Character> getCharactersList() {
    return this.characters.getList();
  }

  /**
   * パーティの人数を返します。
   * @return パーティに所属するキャラクターの数
   */
  public int size() {
    return this.characters.getList().size();
  }


  /**
   * キャラクターのステータスを取得します。
   * @return キャラクターのステータス
   */
  public CharStatus getCharStatus() {
    return this.charStatus;
  }


  /**
   * パーティのアイテムリストを取得します。
   * @return アイテムのリスト
   */
  public Items getItems() {
    return this.items;
  }


  /**
   * パーティにアイテムを追加します。
   * アイテムが通貨の場合は所持金に加算し、それ以外はアイテムリストに追加します。
   * @param item 追加するアイテム
   */
  public void addItem(Item item) {
    if (item.isMoney()) {
      this.charStatus.addMoney(item.getConditions().getPoint(ConditionType.Money));
    }else{
      this.items.add(item);
    }
  }

  /**
   * パーティに新しいキャラクターを追加します。
   * キャラクターのステータスと所持品はパーティ全体のものに統合されます。
   * @param character 追加するキャラクター
   */
  public void addCharacter(Character character) {
    this.characters.add(character);
    this.charStatus.merge(character.charStatus);
    this.items.transfer(character.items);
  }

  /**
   * このパーティのクローンを作成します。
   * @return パーティの新しいインスタンス
   */
  public Base clone() {
    return this.clone(0, null);
  }

  /**
   * このパーティのクローンを、指定された番号とランダムな位置で作成します。
   * @param num クローン番号
   * @param randomPt ランダムな位置
   * @return パーティの新しいインスタンス
   */
  public Base clone(int num, Pt randomPt) {
    Party copy = null;
    try {
      copy = (Party)super.clone(num, randomPt);
      copy.characters = (Characters)this.characters.clone();
      copy.items = (Items)this.items.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * パーティのリーダーの名前を取得します。
   * @return リーダーのキャラクター名
   */
  public String getLeaderName() {
    String name = ((Base)this.characters.getList().get(0)).getName();
    return View.toString(name);
  }

  /**
   * パーティの情報を文字列として返します。
   * @param isDetail 詳細情報を表示するかどうか
   * @return フォーマットされたパーティ情報
   */
  public void printParty() {
    String strCharaString = "";
    for (Character one: (ArrayList<Character>)this.characters.getList()) {
      strCharaString += one.toString(true);
    }
    App.view.printMessage("party_status", toString(), this.pt.x, this.pt.y, this.charStatus.getMoney(), this.items.toString());
    App.view.printMessage("party_members", strCharaString);
  }

  /**
   * パーティ内のキャラクターを選択させます。
   * @param scanner ユーザー入力用のScanner
   * @param excludeLord リーダーを除外するかどうか
   * @return 選択されたキャラクターを含むAnswerオブジェクト
   */
  public Answer<Character> selectCharacter(Scanner scanner, boolean excludeLord) {
    if (this.characters.getList().size()==1) {
      return new Answer<Character>(null, (Character)this.characters.getList().get(0));
    }
    Answers<Character> ansers = this.characters.toAnswers(excludeLord);
    return ansers.printChoice(scanner, null, false);
  }

  /**
   * このパーティが敵パーティかどうかを判定します。
   * @return リーダーが敵キャラクターの場合はtrue
   */
  public boolean isEnemyParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    // リーダーが敵なら敵パーティとみなす
    Character character = this.characters.children.get(0);
    return character.type.isEnemyCharacter();
  }

  /**
   * このパーティが仲間パーティかどうかを判定します。
   * @return リーダーが仲間キャラクターの場合はtrue
   */
  public boolean isCrewParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    Character character = this.characters.children.get(0);
    return character.type.isCrewCharacter();
  }

  /**
   * このパーティがNPCパーティかどうかを判定します。
   * @return リーダーがNPCキャラクターの場合はtrue
   */
  public boolean isNpcParty() {
    if (this.characters == null || this.characters.getList().isEmpty()) {
      return false;
    }
    Character character = this.characters.children.get(0);
    return character.type.isNpcCharacter();
  }


  /**
   * パーティ全体の合計経験値を取得します。
   * @return 合計経験値
   */
  public int getTotalExperience() {
    int ex = 0;
    ArrayList<Character>lst = (ArrayList<Character>)this.characters.getList();
    for (Character one: lst) {
      ex += one.getTotalExpericence();
    }
    return ex;
  }

  /**
   * パーティの各メンバーに均等に経験値を加算します。
   * @param ex 加算する経験値
   */
  public void addAveExperience(int ex) {
    for (Character one: (ArrayList<Character>)this.characters.getList()) {
      one.addExperience(ex);
    }
  }
}