package rpg.model.types;

/**
 * 装備種類を定義する列挙型(Enum)。
 */
public enum GearType{
  Clothes(1),  //服
  Cloak(2),    //マント
  Hat(3),      //帽子
  Shoes(4),    //靴
  gloves(5),   //手袋
  Sword(6),    //剣
  Shield(7),   //盾
  Bow(8),      //弓
  Wand(9),     //杖
  hammer(10);  //金槌

  /**
   * 装備タイプに対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id 装備タイプに割り当てるID
   */
  private GearType(int id) { // コンストラクタの定義
    this.id = id;
  }

}