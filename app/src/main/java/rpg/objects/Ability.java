package rpg.objects;
import rpg.types.AbilityType;

/**
 * キャラクターの能力値を表すクラス。
 * <p>
 * 各能力値には、種類（AbilityType）とポイント（point）があり、
 * 一時的な低下や増大の比率（rate）も持ちます。
 * </p>
 */
public class Ability {
  static int INITIAL_RATE = 10; // 初期の能力値比率（10がデフォルト）

  public AbilityType type;  // 能力値の種類
  public int point; // 能力値のポイント（１〜10）
  public int rate;   // 能力値の一時的低下増大比率（10がデフォルト）
}
