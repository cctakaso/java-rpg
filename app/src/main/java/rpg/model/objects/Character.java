package rpg.model.objects;
import java.util.ArrayList;
import java.util.Scanner;

import rpg.model.types.*;
import rpg.utils.*;

/**
 * キャラクターを表すクラス。
 * <p>
 * キャラクターは、名前、タイプ、会話、ステータス、装備、アイテム、攻撃などの
 * プロパティを持ちます。キャラクターの行動や戦闘に関する機能も提供します。
 * </p>
 */
public class Character extends Base{
  protected CharacterType type; // キャラクターの種類
  protected Talks talks;        // キャラクターの会話
  protected CharStatus charStatus;  // キャラクターのステータス
  protected Gears gears;  // キャラクターの装備
  protected Items items;  // キャラクターのアイテム
  protected Attacks attacks;  // キャラクターの攻撃

  /**
   * デフォルトコンストラクタ。
   * <p>
   * キャラクターの名前を空文字列で初期化し、会話、ステータス、装備、アイテム、
   * 攻撃をそれぞれ初期化します。
   * </p>
   */
  public Character() {
    super();
    this.talks = new Talks();
    this.charStatus = new CharStatus();
    this.gears = new Gears();
    this.items = new Items();
    this.attacks = new Attacks();
  }

  /**
   * キャラクターの種類を取得します。
   * @return キャラクターの種類
   */
  public String getName() {
    return super.getName();
  }

  public CharacterType getType() {
    return this.type;
  }
  /**
   * キャラクターの種類を設定します。
   * @param type キャラクターの種類
   */
  public void setType(CharacterType type) {
    this.type = type;
  }
  /**
   * キャラクターの会話を取得します。
   * @return キャラクターの会話
   */
  public Talks getTalks() {
    return this.talks;
  }
  /**
   * キャラクターのステータスを取得します。
   * @return キャラクターのステータス
   */
  public CharStatus getCharStatus() {
    return this.charStatus;
  }
  /**
   * キャラクターの装備を取得します。
   * @return キャラクターの装備
   */
  public Gears getGears() {
    return this.gears;
  }
  /**
   * キャラクターのアイテムを取得します。
   * @return キャラクターのアイテム
   */
  public Items getItems() {
    return this.items;
  }
  /**
   * キャラクターの攻撃を取得します。
   * @return キャラクターの攻撃
   */
  public Attacks getAttacks() {
    return this.attacks;
  }

  /**
   * キャラクターの文字列表現を返します。
   * <p>
   * キャラクターの名前、タイプ、ステータス、攻撃の詳細を含む文字列を返します。
   * </p>
   * @return キャラクターの文字列表現
   */
  @Override
  public String toString() {
    return super.toString()+"["+this.type+"]"
    + " "+this.charStatus.toString()+"\n"+this.attacks.toString();
  }

  /**
   * キャラクターの文字列表現を返します。
   * <p>
   * キャラクターの名前、タイプ、ステータス、攻撃の詳細を含む文字列を返します。
   * </p>
   * @param isDetail 詳細表示フラグ
   * @return キャラクターの文字列表現
   */
  public String toString(boolean isDetail) {
    return super.toString()+"["+this.type+"]"
    + (isDetail ? " "+this.charStatus.toString()+"\n"+this.attacks.toString():"");
  }

  /**
   * キャラクターのヘルスポイントを取得します。
   * <p>
   * キャラクターのヘルスポイントは、キャラクターの体力を表します。
   * </p>
   * @return ヘルスポイント
   */
  public int getHealthPoint() {
    return this.charStatus.getHealthPoint();
  }

  /**
   * キャラクターの詳細を文字列として返します。
   * <p>
   * キャラクターの名前、タイプ、装備の詳細を含む文字列を返します。
   * </p>
   * @param gearType 装備の種類
   * @return キャラクターの詳細を表す文字列
   */
  public String toWithGearPrinting(GearType gearType) {
    // キャラクターの装備を取得し、指定された装備タイプの装備を探す
    // 装備が見つかった場合、その装備の情報を返す
    // 装備が見つからない場合、装備なしの情報を返す
    for(Gear gear: (ArrayList<Gear>)this.gears.getList()) {
      if (gear.getGearType() == gearType) {
        return super.toString()+"["+this.type+ " " + gear.toString()+ "]";
      }
    }
    return toString(false);
  }

  /**
   * キャラクターの攻撃を選択します。
   * <p>
   * キャラクターが使用可能な攻撃をリストから選択し、選択された攻撃を返します。
   * </p>
   * @param scanner 入力を受け付けるスキャナー
   * @return 選択された攻撃
   */
  public Answer<Attack> selectAttack(Scanner scanner) {
    Answers<Attack> ansers = attacks.toAnswers(this);
    return ansers.printChoice(scanner, getName(), true);
  }

  /**
   * キャラクターが攻撃を受けた際の処理を行います。
   * <p>
   * 攻撃の種類に応じて、キャラクターのステータスを更新し、攻撃結果を返します。
   * </p>
   * @param attack 攻撃の詳細
   * @param attacker 攻撃者のキャラクター
   * @return 攻撃結果の文字列
   */
  public Answer<Integer> haveAttack(Attack attack, Character attacker) {
    int damage = attack.calcHealthDamage(attacker, this);
    if (attack.isHeal()) {
      int reciverHp = this.charStatus.incHealthPoint(damage);
      attacker.charStatus.decMagicPoint(attack.usedMagicPoint());
      return new Answer<Integer>(attacker.getName()+"が"+this.getName()+"に、ヒール:"+damage+"によりHP:"+reciverHp+"になりました。", Integer.valueOf(reciverHp));
    }else if (attack.isDefence()) {
      // 防御系の攻撃の場合、キャラクターの防御処理を行う
      // 防御系の攻撃は、通常のダメージ計算を行わない
      // 防御系の攻撃は、キャラクターの防御ステータスを更新する
      return new Answer<Integer>(attacker.getName()+"が、"+attack.getName()+"を使用して防御しました。");
    }else{
      int reciverHp = this.charStatus.decHealthPoint(damage);
      attacker.charStatus.decMagicPoint(attack.usedMagicPoint());
      return new Answer<Integer>(attacker.getName()+"が"+this.getName()+"に、攻撃:"+attack.getName()+"で"+damage+"ダメージ与えました\n"+this.getName()+"は、現在HP:"+reciverHp+"です。", Integer.valueOf(reciverHp));
    }
  }

  /**
   * キャラクターのクローンを作成します。
   * <p>
   * キャラクターの名前、位置、タイプ、会話、ステータス、装備、アイテム、攻撃を
   * 保持した新しいCharacterオブジェクトを返します。
   * </p>
   * @return 新しいCharacterオブジェクト
   */
  public Character clone() {
    return this.clone(0, null);
  }

  /**
   * キャラクターのクローンを作成します。
   * <p>
   * キャラクターの名前、位置、タイプ、会話、ステータス、装備、アイテム、攻撃を
   * 保持した新しいCharacterオブジェクトを返します。
   * </p>
   * @param num クローンの番号（識別用）
   * @param randomPt ランダムな位置（座標）
   * @return 新しいCharacterオブジェクト
   */
  public Character clone(int num, Pt randomPt) {
    Character copy = null;
    try {
      copy = (Character)super.clone(num, randomPt);
      copy.type = this.type;
      copy.talks = (Talks)this.talks.clone();
      copy.charStatus = (CharStatus)this.charStatus.clone();
      copy.gears = (Gears)this.gears.clone();
      copy.items = (Items)this.items.clone();
      copy.attacks = (Attacks)this.attacks.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * キャラクターのプロパティを他のキャラクターからコピーします。
   * <p>
   * 他のキャラクターの名前、位置、タイプ、会話、ステータス、装備、アイテム、
   * 攻撃をコピーします。
   * </p>
   * @param character コピー元のキャラクター
   */
  public void copy(Character character) {
    super.copy(character);
    this.type = character.type;
    this.talks = character.talks;
    this.charStatus = character.charStatus;
    this.gears = character.gears;
    this.items = character.items;
    this.attacks = character.attacks;
  }

  /**
   * キャラクターとの遭遇イベントを処理します。
   * <p>
   * このメソッドは、キャラクターが他のパーティと遭遇した際のイベント処理を行います。
   * </p>
   * @param scanner ユーザー入力用のScanner
   * @param myParty 自身のパーティ
   * @return イベント処理が正常に終了した場合はtrue
   */
  public boolean meet(Scanner scanner, Party myParty) {
    return false;
  }

  /**
   * キャラクターのステータスをリセットします。
   * <p>
   * キャラクターのステータスを初期状態に戻します。
   * </p>
   */
  public void setReset() {
    this.charStatus.setReset();
  }

  /**
   * キャラクターの経験値を取得します。
   * <p>
   * キャラクターの現在の経験値を返します。
   * </p>
   * @return キャラクターの経験値
   */
  public int getTotalExpericence() {
    return this.charStatus.getLevel().expTotal;
  }

  /**
   * キャラクターの経験値を追加します。
   * <p>
   * 指定された経験値をキャラクターのステータスに追加し、レベルアップを行います。
   * </p>
   * @param ex 追加する経験値
   * @return 更新後の経験値
   */
  public int addExperience(int ex) {
    return this.charStatus.getLevel().addExperience(ex);
  }
}

