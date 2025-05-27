package rpg.types;

public enum TradeType {
  buy(0),      //買う
  sale(1);      //売る

  public int id; // フィールドの定義
  private TradeType(int id) { // コンストラクタの定義
    this.id = id;
  }
}
