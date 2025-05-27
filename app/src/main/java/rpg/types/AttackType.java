package rpg.types;

public enum AttackType {
  Attack(1),
  PartyAttack(2),

  MagicDefenseMin(3),
  Heal(3),
  PartyHeal(4),

  ReGene(5), //リジェネ	効果：HPが徐々に回復する
  Barrier(6), //バリア	効果：物理ダメージを軽減する
  MagicBarrier(7), //マバリア	効果：魔法ダメージを軽減する
  Reflect(8), //リフレク	効果：魔法を反射する
  Shield(9), //シールド	効果：魔法ダメージを無効化する
  Haste(10), //ヘイスト	効果：スピードが上昇する
  Resist(11), //レジスト	効果：状態異常を防ぐ
  Reraise(12), //リレイズ	効果：戦闘不能なっても一度だけ蘇生される
  MagicDefenseMax(12),

  MagicOffenceMin(20),
  Sleep(21),	//スリプル
  Focus(22),	//フォーカス
  Dark(23),	//ダクネス
  Slow(24),	//スロウ
  Temper(25),	//テンプター（激怒）
  Hold(26),	//ホールド
  Confuse(27),	//コンフユ
  Stop(28),	//ストップ

  Fire(30),	//ファイア
  Thunder(31),	//サンダー
  Blizzard(32),	//ブリザド

  MagicMultiOffenceMin(33),
  Fira(33),	//ファイラ
  Thundara(34),	//サンダラ
  Blizzara(35),	//ブリザラ
  Firaga(36),	//ファイガ
  Thundaga(37),	//サンガー
  Blizzaga(38),	//ブリザガ
  Death(39),	//デス
  MagicMultiOffenceMax(39),

  Kill(40),	//キル
  MagicOffenceMax(50);

  public int id; // フィールドの定義
  private AttackType(int id) { // コンストラクタの定義
    this.id = id;
  }
  public int constOfMagic() {
    if (this.id != Attack.id) {
      return 5; //5%
    }
    return 0;
  }


}
