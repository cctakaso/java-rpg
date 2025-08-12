package rpg.model.objects;

import java.util.*;

import rpg.Adventure;
import rpg.utils.*;

/**
 * 攻撃を表すクラス。
 * <p>
 * 攻撃の種類（AttackType）とステータス（Statuses）を持ち、
 * 攻撃の詳細やダメージ計算などの機能を提供します。
 * </p>
 */
public class Attacks extends Lists{
  protected ArrayList<Attack> children; // 攻撃のリスト

  /**
   * デフォルトコンストラクタ。
   * <p>
   * 攻撃のリストを空のArrayListで初期化します。
   * </p>
   */
  public Attacks() {
    super();
    this.children = new ArrayList<Attack>();
  }

  /**
   * 攻撃の種類を取得します。
   * @return 攻撃の種類
   */
  protected Lists getNewInstance() {
    return new Attacks();
  }

  /**
   * 攻撃のリストを取得します。
   * <p>
   * 攻撃のリストは、AttackオブジェクトのArrayListとして保持されます。
   * </p>
   * @return 攻撃のリスト
   */
  @Override
  public List<Attack> getList() {
    return children;
  }

  /**
   * 攻撃のリストを設定します。
   * <p>
   * 引数として渡されたリストを、攻撃のリストとして設定します。
   * </p>
   * @param children 攻撃のリスト
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setList(List<?> children) {
    this.children = (ArrayList<Attack>)children;
  }

  /**
   * 攻撃のリストに攻撃を追加します。
   * <p>
   * 引数として渡されたAttackオブジェクトを、攻撃のリストに追加します。
   * </p>
   * @param attack 追加する攻撃
   */
  public Answers<Attack> toAnswers(Character character) {
    Answers<Attack> list = new Answers<>();
    Attack attack = (Attack)Adventure.getDicClone("Attacks", "プロテクト");
    list.add(new Answer<Attack>("防御する", attack));
    for (Attack one: children) {
      if (one.isAvailable(character)) {
        list.add(new Answer<Attack>(one.toString(), one));
      }
    }
    return list;
  }
}
