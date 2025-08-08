package rpg.types;

/**
 * アイテム種類を定義する列挙型(Enum)。
 */
public enum ItemType {
  Gear(1),          //装備品
  Money(2),         //お金

  Item(10),         //Items mark
  Herb(10),         //薬草
  Potion(11),       //ポーション
  SuperPotion(12),  //スーパーポーション
  HyperPotion(13),  //ハイポーション
  MagicPotion(14),  //マジックポーション
  SuperMagicPotion(15), //スーパーマジックポーション
  HyperMagicPotion(16), //ハイマジックポーション

  Antidote(20),     //毒消し
  ParalyzeHeal(21), //麻痺直し
  Awakening(22),    //目覚まし
  EyeDrops(23),     //目薬
  ParlyzHeal(24),   //麻痺直し
  FullHeal(25),     //全部直し
  GoldNeedle(26),   //石化直し
  EchoGrass(27),    //やまびこ草
  SilverNeedle(28), //混乱・沈黙直し
  PhoenixDown(29);  //フェニックスの尾

  /**
   * フィールドタイプに対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id フィールドタイプに割り当てるID
   */
  private ItemType(int id) { // コンストラクタの定義
    this.id = id;
  }

  /**
   * アイテムが装備品かどうかを判定する。
   * @return 装備品ならtrue、そうでなければfalse
   */
  public boolean isGear() {
    return this.id == Gear.id;
  }

  /**
   * アイテムが通貨（お金）かどうかを判定する。
   * @return お金ならtrue、そうでなければfalse
   */
  public boolean isMoney() {
    return this.id == Money.id;
  }

  /**
   * アイテムが通常のアイテム（装備品やお金以外）かどうかを判定する。
   * @return 通常のアイテムならtrue、そうでなければfalse
   */
  public boolean isItem() {
    return !isGear() && !isMoney();
  }
}
