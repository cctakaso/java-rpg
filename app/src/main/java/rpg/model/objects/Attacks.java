package rpg.model.objects;

import java.util.*;

import rpg.Adventure;
import rpg.utils.*;

/**
 * 複数の攻撃（Attack）を管理するためのリストクラスです。
 * Listsクラスを継承し、キャラクターが使用できる攻撃のコレクションを扱います。
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
   * キャラクターが選択可能な攻撃のリストをAnswers形式で取得します。
   * <p>
   * 防御の選択肢を含め、キャラクターが使用可能な攻撃をAnswersオブジェクトとして返します。
   * </p>
   * @param character 攻撃を選択するキャラクター
   * @return 選択可能な攻撃を含むAnswersオブジェクト
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

  /**
   * 新たにAttacksインスタンスを取得します。
   * <p>
   * 攻撃のリストを管理するための新しいインスタンスを返します。
   * </p>
   * @return Attacksインスタンス
   */
  protected Lists getNewInstance() {
    return new Attacks();
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
}