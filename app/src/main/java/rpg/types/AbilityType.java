package rpg.types;

public enum AbilityType {
  Strength(1),      //筋力
  Agility(2),       //敏捷性
  Durability(3),    //耐久性
  Intelligence(4),  //知力
  Judgment(5),      //判断力
  Charm(6),         //魅力
  max(7);           //Terminal End

  public int id; // フィールドの定義
  private AbilityType(int id) { // コンストラクタの定義
    this.id = id;
  }
  public static int max() {
    AbilityType tmp = max;
    return tmp.id;
  }
}
