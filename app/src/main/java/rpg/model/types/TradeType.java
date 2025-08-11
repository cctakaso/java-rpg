package rpg.model.types;

/**
 * 取引の種類を定義する列挙型(Enum)。
 * <p>
 * 主に、アイテムの売買に関連する取引の種類を表します。
 * </p>
 */
public enum TradeType {
  buy(0),      //買う
  sale(1);      //売る

  /**
   * フィールドタイプに対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id フィールドタイプに割り当てるID
   */
  private TradeType(int id) { // コンストラクタの定義
    this.id = id;
  }
}
