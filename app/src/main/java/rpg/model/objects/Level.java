package rpg.model.objects;

import java.util.ArrayList;

import rpg.model.types.ConditionType;

/**
 * キャラクターのレベル、経験値、およびそれに関連するステータス計算を管理するクラスです。
 */
public class Level {
  /**
   * 現在のレベル
   */
  private int level;
  /**
   * 現在のレベルでの経験値
   */
  private int experience;
  /**
   * 現在のレベルの最大経験値
   */
  private int expTotal;
  /**
   * 次のレベルアップに必要な経験値
   */
  private int expForNextLevel;
  /**
   * レベル係数
   */
  private float levelFactor;
  /**
   * 初期レベル
   */
  private static int FIRST_LEVEL = 1;
  /**
   * レベルアップに必要な敵の討伐数（目安）
   */
  private static int NEED_NUM_DEFEAT = 5;
  /**
   * 平均HP
   */
  private static int AVE_HP = 10;

  /**
   * デフォルトコンストラクタ。
   * レベルを1に初期化し、経験値を0に設定します。
   */
  public Level() {
    this.level = 1;
    experience = 0;
    buildLevelFactor();
  }

  /**
   * 現在のレベルの最大経験値を取得します。
   * @return 現在のレベルの最大経験値
   */
  public int getExpTotal() {
    return this.expTotal;
  }

  /**
   * このLevelオブジェクトのクローンを作成します。
   * @return Levelオブジェクトの新しいインスタンス
   */
  public Level clone() {
    Level copy = null;
    try {
      copy = new Level();
      copy.level = this.level;
      copy.experience = this.experience;
      copy.expTotal = this.expTotal;
      copy.expForNextLevel = this.expForNextLevel;
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * 現在のレベルとステータス情報を文字列として返します。
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return フォーマットされたステータス文字列
   */
  public String toString(ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    if (condarray == null || rates == null || condarray.size() == 0 || rates.size() == 0) {
      return "LEVEL: " + this.level + " EX: " + this.experience + "/" + this.expForNextLevel;
    }
    // レベル係数を再計算して、ステータスのポイントを正 しく計算
    // これにより、レベルアップ時のステータス計算が正確になります。
    // ただし、レベルアップの際にはaddExperienceメソッド
    // で再度buildLevelFactorを呼び出すため、ここでは必要ありません。
    // ただし、レベルアップ後にステータスを表示する場合
    // には再度buildLevelFactorを呼び出す必要があります。
    // ここでは、レベルアップ後のステータス表示を想定して
    // buildLevelFactorを呼び出しています。
    // ただし、レベルアップ後のステータス表示は
    // addExperienceメソッド内で行われるため、
    // ここでは不要です。
    buildLevelFactor();
    return "LEVEL:" + this.level + " EX:" + this.experience + "/" + this.expForNextLevel + "\n" +
            "HP:" + getRatePoint(ConditionType.HealthPoint, condarray, rates) + "/" + getFullPoint(ConditionType.HealthPoint, condarray) +
            " MP:" + getRatePoint(ConditionType.MagicPoint, condarray, rates) + "/" + getFullPoint(ConditionType.MagicPoint, condarray) +
            " AK:" + getRatePoint(ConditionType.Attack, condarray, rates) +
            " DF:" + getRatePoint(ConditionType.Defence, condarray, rates) +
            " MA:" + getRatePoint(ConditionType.MagicAttack, condarray, rates) +
            " MD:" + getRatePoint(ConditionType.MagicDefence, condarray, rates) +
            " AG:" + getRatePoint(ConditionType.Agility, condarray, rates) +
            " DX:" + getRatePoint(ConditionType.Dexterity, condarray, rates) +
            " EV:" + getRatePoint(ConditionType.Evansion, condarray, rates) +
            " CR:" + getRatePoint(ConditionType.Critical, condarray, rates);
  }

  /**
   * HPを全回復します。
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 回復後のHP
   */
  public int fullHeal(ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    rates.set(ConditionType.HealthPoint.id, 100);
    return condarray.get(ConditionType.HealthPoint.id);
  }

  /**
   * 現在のHPを取得します。
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 現在のHP
   */
  public int getHealthPoint(ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return getRatePoint(ConditionType.HealthPoint, condarray, rates);
  }

  /**
   * HPを指定された値だけ増加させます。
   * @param delta 増加させるHPの値
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 増加後のHP
   */
  public int incHealthPoint(int delta, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return incPoint(delta, ConditionType.HealthPoint, condarray, rates);
  }

  /**
   * HPの割合を指定された値だけ増加させます。
   * @param delta 増加させるHPの割合
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 増加後のHP
   */
  public int incHealthRate(int delta, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return incRate(delta, ConditionType.HealthPoint, condarray, rates);
  }

  /**
   * HPを指定された値だけ減少させます。
   * @param delta 減少させるHPの値
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 減少後のHP
   */
  public int decHealthPoint(int delta, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return decPoint(delta, ConditionType.HealthPoint, condarray, rates);
  }

  /**
   * 現在のMPを取得します。
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 現在のMP
   */
  public int getMagicPoint(ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return getRatePoint(ConditionType.MagicPoint, condarray, rates);
  }

  /**
   * MPを指定された値だけ増加させます。
   * @param delta 増加させるMPの値
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 増加後のMP
   */
  public int incMagicPoint(int delta, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return incPoint(delta, ConditionType.MagicPoint, condarray, rates);
  }

  /**
   * MPの割合を指定された値だけ増加させます。
   * @param delta 増加させるMPの割合
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 増加後のMP
   */
  public int incMagicRate(int delta, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return incRate(delta, ConditionType.MagicPoint, condarray, rates);
  }

  /**
   * MPを指定された値だけ減少させます。
   * @param delta 減少させるMPの値
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 減少後のMP
   */
  public int decMagicPoint(int delta, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return decPoint(delta, ConditionType.MagicPoint, condarray, rates);
  }

  /**
   * MPの割合を指定された値だけ減少させます。
   * @param delta 減少させるMPの割合
   * @param condarray ステータスの基本値のリスト
   * @param rates ステータスの割合のリスト
   * @return 減少後のMP
   */
  public int decMagicRate(int delta, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return decRate(delta, ConditionType.MagicPoint, condarray, rates);
  }

  /**
   * 指定されたステータスタイプのポイントを増加させます。
   * @param delta 増加量
   * @param type ステータスタイプ
   * @param condarray ステータスの基本値リスト
   * @param rates ステータスの割合リスト
   * @return 更新後のポイント
   */
  private int incPoint(int delta, ConditionType type, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    int point = getRatePoint(type, condarray, rates);
    point += delta;
    int rate = point * 100 / getFullPoint(type, condarray);
    if (rate >= 100) {
      rates.set(type.id, 100);
      return 100;
    } else {
      rates.set(type.id, rate);
    }
    return getRatePoint(type, condarray, rates);
  }

  /**
   * 指定されたステータスタイプのポイントを減少させます。
   * @param delta 減少量
   * @param type ステータスタイプ
   * @param condarray ステータスの基本値リスト
   * @param rates ステータスの割合リスト
   * @return 更新後のポイント
   */
  private int decPoint(int delta, ConditionType type, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    int point = getRatePoint(type, condarray, rates);
    point -= delta;
    if (point <= 0) {
      rates.set(type.id, 0);
      return 0;
    } else {
      rates.set(type.id, (int) point * 100 / getFullPoint(type, condarray));
    }
    return getRatePoint(type, condarray, rates);
  }

  /**
   * 指定されたステータスタイプの割合を増加させます。
   * @param delta 増加量
   * @param type ステータスタイプ
   * @param condarray ステータスの基本値リスト
   * @param rates ステータスの割合リスト
   * @return 更新後のポイント
   */
  private int incRate(int delta, ConditionType type, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    int rate = rates.get(type.id);
    rate += delta;
    if (rate >= 100) {
      rates.set(type.id, 100);
      return 100;
    } else {
      rates.set(type.id, (int) rate);
    }
    return getRatePoint(type, condarray, rates);
  }

  /**
   * 指定されたステータスタイプの割合を減少させます。
   * @param delta 減少量
   * @param type ステータスタイプ
   * @param condarray ステータスの基本値リスト
   * @param rates ステータスの割合リスト
   * @return 更新後のポイント
   */
  private int decRate(int delta, ConditionType type, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    int rate = rates.get(type.id);
    rate -= delta;
    if (rate <= 0) {
      rates.set(type.id, 0);
      return 0;
    } else {
      rates.set(type.id, (int) rate);
    }
    return getRatePoint(type, condarray, rates);
  }

  /**
   * 経験値を加算し、必要であればレベルアップ処理を行います。
   * @param delta 加算する経験値
   * @return 更新後のレベル
   */
  public int addExperience(int delta) {
    buildLevelFactor();
    this.experience += delta;
    // 経験値が次のレベルアップに必要な値を超える限りレベルアップを繰り返す
    while (this.experience >= this.expForNextLevel) {
      this.level++;
      this.experience -= this.expForNextLevel;
      this.expTotal = this.expForNextLevel;
      this.expForNextLevel = this.expForNextLevel * 3 / 2;
    }
    buildLevelFactor();
    return this.level;
  }

  /**
   * 指定されたステータスタイプの最大ポイントを取得します。
   * @param type ステータスタイプ
   * @param condarray ステータスの基本値リスト
   * @return 最大ポイント
   */
  private int getFullPoint(ConditionType type, ArrayList<Integer> condarray) {
    return calcLevelFactor(condarray.get(type.id));
  }

  /**
   * 指定されたステータスタイプの現在のポイントを割合に基づいて取得します。
   * @param type ステータスタイプ
   * @param condarray ステータスの基本値リスト
   * @param rates ステータスの割合リスト
   * @return 現在のポイント
   */
  public int getRatePoint(ConditionType type, ArrayList<Integer> condarray, ArrayList<Integer> rates) {
    return calcLevelFactor(condarray.get(type.id), rates.get(type.id));
  }

  /**
   * レベル係数を考慮したポイントを計算します（割合100%）。
   * @param point 基本ポイント
   * @return 計算後のポイント
   */
  private int calcLevelFactor(int point) {
    return calcLevelFactor(point, 100);
  }

  /**
   * レベル係数と割合を考慮したポイントを計算します。
   * @param point 基本ポイント
   * @param rate 割合
   * @return 計算後のポイント
   */
  private int calcLevelFactor(int point, int rate) {
    return (int) (point * rate * this.levelFactor / 100);
  }

  /**
   * レベル係数と次のレベルに必要な経験値を計算して設定します。
   */
  private void buildLevelFactor() {
    this.levelFactor = getLevelFactor(this.level);
    this.expForNextLevel = (int) (NEED_NUM_DEFEAT * AVE_HP * this.levelFactor);
    this.expTotal = this.expForNextLevel * 2 / 3;
  }

  /**
   * 指定されたレベルに対するレベル係数を計算します。
   * @param level 計算対象のレベル
   * @return レベル係数
   */
  private float getLevelFactor(int level) {
    float f = 1;
    for (int i = 1; i < level; i++) {
      f *= 1.5;
    }
    return f;
  }
}