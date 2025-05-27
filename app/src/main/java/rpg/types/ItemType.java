package rpg.types;

public enum ItemType {
  Gear(1),     //装備品
  Money(2),

  Item(10),   //Items mark
  Herb(10),     //薬草
  Potion(11),  //ポーション
  SuperPotion(12),
  HyperPotion(13),
  MagicPotion(14),
  SuperMagicPotion(15),
  HyperMagicPotion(16),

  Antidote(20), //毒消し
  ParalyzeHeal(21), //麻痺直し
  Awakening(22),  //目覚まし
  EyeDrops(23),   //目薬
  ParlyzHeal(24),  //麻痺直し
  FullHeal(25),   //全部直し
  GoldNeedle(26), //石化直し
  EchoGrass(27), //やまびこ草
  SilverNeedle(28), //混乱・沈黙直し
  PhoenixDown(29); //フェニックスの尾

  public int id; // フィールドの定義
  private ItemType(int id) { // コンストラクタの定義
    this.id = id;
  }

  public boolean isGear() {
    return this.id == Gear.id;
  }
  public boolean isMoney() {
    return this.id == Money.id;
  }
  public boolean isItem() {
    return !isGear() && !isMoney();
  }
}
