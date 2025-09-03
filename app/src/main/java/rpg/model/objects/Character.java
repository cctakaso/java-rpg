package rpg.model.objects;
import java.util.ArrayList;
import java.util.Scanner;

import rpg.model.types.*;
import rpg.utils.*;
import rpg.view.console.View;

/**
 * キャラクターを表すクラス。
 * <p>
 * キャラクターは、名前、タイプ、会話、ステータス、装備、アイテム、攻撃などの
 * プロパティを持ちます。キャラクターの行動や戦闘に関する機能も提供します。
 * </p>
 */
public class Character extends Base{
  protected CharacterType type;     // キャラクターの種類
  protected Talks talks;            // キャラクターの会話
  protected CharStatus charStatus;  // キャラクターのステータス
  protected Gears gears;            // キャラクターの装備
  protected Items items;            // キャラクターのアイテム
  protected Attacks attacks;        // キャラクターの攻撃

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
  public CharacterType getType() {
    return this.type;
  }

  /**
   * キャラクターのステータスを取得します。
   * @return キャラクターのステータス
   */
  public CharStatus getCharStatus() {
    return this.charStatus;
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
   * キャラクターの経験値を取得します。
   * <p>
   * キャラクターの現在の経験値を返します。
   * </p>
   * @return キャラクターの経験値
   */
  public int getTotalExpericence() {
    return this.charStatus.getLevel().getExpTotal();
  }

  /**
   * キャラクターの文字列表現を返します。
   * <p>
   * キャラクターの名前、タイプ、ステータス、攻撃の詳細を含む文字列を返します。
   * </p>
   * @return キャラクターの文字列表現
   */
/*
  @Override
  public String toString() {
    return super.toString()+"["+this.type+"]"
    + " "+this.charStatus.toString()+"\n"+this.attacks.toString();
  }
*/

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
        return super.toString()+"["+this.type+ " " + gear.toString()+"]";
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
    return ansers.printChoice(scanner, toString(), true);
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
      //%sがヒール:%dを使用し、thisのHPは%dになりました。
      String msg = View.toString("heel_apply", attacker.toString(), damage, this, reciverHp);
      return new Answer<Integer>(msg, Integer.valueOf(reciverHp));
    }else if (attack.isDefence()) {
      // 防御系の攻撃の場合、キャラクターの防御処理を行う
      // 防御系の攻撃は、通常のダメージ計算を行わない
      // 防御系の攻撃は、キャラクターの防御ステータスを更新する
      //attacker+"が、"+attack+"を使用して防御しました。"
      String msg = View.toString("do_defence", attacker.toString(), attack.toString());
      return new Answer<Integer>(msg);
    }else{
      int reciverHp = this.charStatus.decHealthPoint(damage);
      attacker.charStatus.decMagicPoint(attack.usedMagicPoint());
      //attacker+"が"+this+"に、攻撃:"+attack+"で"+damage+"ダメージ与えました\n"+this+"は、現在HP:"+reciverHp+"です。"
      String msg = View.toString("atack_apply", attacker.toString(), attack.toString(), damage, this.toString(), reciverHp);
      return new Answer<Integer>(msg, Integer.valueOf(reciverHp));
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

