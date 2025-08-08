package rpg.types;

/**
 * フィールド（マップのエリア）の地形や種類を定義する列挙型(Enum)。
 */
public enum FieldType {
  Town(1),  // 町
  City(2),  // 都市
  Prairie(3), // 草原
  Mountain(4),  // 山
  Cave(5),  // 洞窟
  Mix(6), // 複数の地形が混在するエリア
  max(7); // フィールドタイプの最大数を示すための番兵

  /**
   * フィールドタイプに対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id フィールドタイプに割り当てるID
   */
  private FieldType(int id) { // コンストラクタの定義
    this.id = id;
  }
}
