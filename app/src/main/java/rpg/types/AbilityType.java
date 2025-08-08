package rpg.types;

/**
 *キャラクターの能力値の種類を定義する列挙型(Enum)。
 * <p>
 * 各能力値には、識別のためのIDが割り当てられています。
 * </p>
 */
public enum AbilityType {
  Strength(1),      //筋力
  Agility(2),       //敏捷性
  Durability(3),    //耐久性
  Intelligence(4),  //知力
  Judgment(5),      //判断力
  Charm(6),         //魅力
  max(7);           //Terminal End - 能力値の最大数を示すための番兵

  /**
   * 能力値の種類に対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id 能力値に割り当てるID
   */
  private AbilityType(int id) { // コンストラクタの定義
    this.id = id;
  }

  /**
   * 能力値の種類の最大IDを取得します。
   * @return 能力値の最大ID
   */
  public static int max() {
    AbilityType tmp = max;
    return tmp.id;
  }
}
