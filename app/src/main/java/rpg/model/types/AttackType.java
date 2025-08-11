package rpg.model.types;

/**
 * 戦闘における攻撃やスキルの種類を網羅的に定義する列挙型(Enum)。
 * <p>
 * 物理攻撃、回復魔法、補助魔法、状態異常魔法など、あらゆるアクションを分類します。
 * 各アクションには識別のためのIDが割り当てられています。
 * いくつかの定数（例: MagicDefenceMin）は、特定のカテゴリの範囲を示すためのマーカーとして使われています。
 * </p>
 */
public enum AttackType {
  // --- 物理攻撃系 ---
  Attack(1),        // 単体物理攻撃
  Protect(2),       // 単体防御
  PartyAttack(3),   // 全体物理攻撃
  // --- 回復・補助魔法（防御系）の範囲定義 ---
  MagicDefenceMin(4), // 防御系魔法の最小ID
  Heal(4),          // 単体HP回復
  PartyHeal(5),     // 全体HP回復
  ReGene(6),        // リジェネ (効果：HPが徐々に回復する)
  Barrier(7),       // バリア (効果：物理ダメージを軽減する)
  MagicBarrier(8),  // マバリア (効果：魔法ダメージを軽減する)
  Reflect(9),       // リフレク (効果：魔法を反射する)
  Shield(10),        // シールド (効果：魔法ダメージを無効化する)
  Haste(11),        // ヘイスト (効果：スピードが上昇する)
  Resist(12),       // レジスト (効果：状態異常を防ぐ)
  Reraise(13),      // リレイズ (効果：戦闘不能なっても一度だけ蘇生される)
  MagicDefenceMax(13),  // 防御系魔法の最大IDを示すマーカー

  // --- 状態異常魔法（攻撃系）の範囲定義 ---
  MagicOffenceMin(20),  // 攻撃系魔法の最小ID
  Sleep(21),        // スリプル (眠り)
  Focus(22),        // フォーカス (集中)
  Dark(23),         // ダクネス (暗闇)
  Slow(24),         // スロウ (素早さ低下)
  Temper(25),       // テンプター（激怒）
  Hold(26),         // ホールド (麻痺)
  Confuse(27),      // コンフユ (混乱)
  Stop(28),         // ストップ (時間停止)

  // --- 基本的な属性攻撃魔法 ---
  Fire(30),         // ファイア
  Thunder(31),      // サンダー
  Blizzard(32),     // ブリザド

  // --- 上位の全体属性攻撃魔法の範囲定義 ---
  MagicMultiOffenceMin(33),
  Fira(33),         // ファイラ
  Thundara(34),     // サンダラ
  Blizzara(35),     // ブリザラ
  Firaga(36),       // ファイガ
  Thundaga(37),     // サンガー (サンダガの誤記か)
  Blizzaga(38),     // ブリザガ
  Death(39),        // デス (即死)
  MagicMultiOffenceMax(39),

  // --- 特殊な攻撃 ---
  Kill(40),         // キル (即死物理攻撃？)
  MagicOffenceMax(50); // 攻撃魔法全体の範囲の終わりを示すマーカー

  /**
   * 攻撃・スキル種類に対応する整数ID。
   */
  public int id; // フィールドの定義

  /**
   * Enumのコンストラクタ。
   * @param id 攻撃・スキルに割り当てるID
   */
  private AttackType(int id) { // コンストラクタの定義
    this.id = id;
  }

  /**
   * 魔法の消費コストを計算する（？）メソッド。
   * <p>
   * 注意: 現在の実装には問題がある可能性があります。
   * {@code this.id != Attack.id} という比較は、意図通りに動作しない可能性が高いです。
   * (Attackクラスに静的なidフィールドが存在しないため)
   * </p>
   * @return 計算されたコスト。現在はほぼ常に5を返す。
   */
  public int constOfMagic() {
    // TODO: このロジックは要レビュー。Attack.idという参照はコンパイルエラーになるはず。
    // おそらく、特定のAttackTypeと比較することを意図していたと思われる。
    // if (this.id != Attack.id) { のような比較が正しいかもしれない。
    if (this.id != 1) { // 仮に Attack(1) 以外を魔法とみなすロジックに修正
      return 5; //5%
    }
    return 0;
  }
}
