package rpg.model.objects;
import java.util.*;

import rpg.model.types.CharacterType;
import rpg.model.types.GearType;
import rpg.utils.Pt;

/**
 * 装備品を表すクラス。
 * <p>
 * 装備品は、キャラクターが装備できるアイテムであり、
 * 装備可能な職業（CharacterType）と装備の種類（GearType）を持ちます。
 * </p>
 */
public class Gear extends Item {

  protected GearType gearType;    // 装備の種類
  protected ArrayList<CharacterType> characterTypes;  //装備可能職業

  /**
   * デフォルトコンストラクタ。
   * <p>
   * 装備品のプロパティを初期化します。
   * </p>
   */
  public Gear() {
    this(500);
  }

  /**
   * 重さを指定して装備品を初期化します。
   * <p>
   * 装備品の重さを指定して、装備の種類と装備可能職業のリストを初期化します。
   * </p>
   * @param weight 装備品の重さ
   */
  public Gear(int weight) {
    super(weight);
    characterTypes = new ArrayList<CharacterType>();
  }

  /**
   * 装備の種類を取得します。
   * <p>
   * 装備の種類は、装備品がどのタイプの装備であるかを示します。
   * </p>
   * @return 装備の種類
   */
  public GearType getGearType() {
    return this.gearType;
  }

  /**
   * 装備品のクローンを作成します。
   * <p>
   * 装備品の種類と装備可能職業のリストを保持した新しいGearオブジェクトを返します。
   * </p>
   * @return 新しいGearオブジェクト
   */
  public Gear clone() {
    return this.clone(0, null);
  }

  /**
   * 装備品のクローンを作成します。
   * <p>
   * 装備品の種類と装備可能職業のリストを保持した新しいGearオブジェクトを返します。
   * </p>
   * @param num クローンの番号
   * @param randomPt ランダムな座標（使用されない）
   * @return 新しいGearオブジェクト
   */
  public Gear clone(int num, Pt randomPt) {
    Gear copy = null;
    try {
      copy = (Gear)super.clone(num, randomPt);
      copy.gearType = this.gearType;
      copy.characterTypes = (ArrayList<CharacterType>)characterTypes.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public boolean isFiting(Character character) {
    return this==null || this.characterTypes.contains(character.type);
  }
}
