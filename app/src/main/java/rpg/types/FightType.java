package rpg.types;

/**
 * 戦闘種類を定義する列挙型(Enum)。
 */
public enum FightType {
  Attack(0),     //戦う
  Magic(1),      //魔法を唱える
  Defence(2),    //防御する
  UseTools(3),   //道具を使う
  Escape(4);     //逃げる

  /**
   * フィールドタイプに対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id フィールドタイプに割り当てるID
   */
  private FightType(int id) { // コンストラクタの定義
    this.id = id;
  }
}
