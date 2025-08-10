package rpg.objects;
import java.util.*;

import rpg.types.GearType;
/**
 * 装備品のリストを表すクラス。
 * <p>
 * 複数の装備品（Gear）を管理し、特定の装備品を取得する機能を提供します。
 * </p>
 */
public class Gears extends Lists{
  protected ArrayList<Gear> children; // 装備品のリスト

  /**
   * デフォルトコンストラクタ。
   * <p>
   * 装備品のリストを初期化します。
   * </p>
   */
  public Gears() {
    super();
    children = new ArrayList<Gear>();
  }

  /**
   * 新たにGearsインスタンスを取得します。
   * <p>
   * 装備品のリストを管理するための新しいインスタンスを返します。
   * </p>
   * @return Gearsインスタンス
   */
  protected Lists getNewInstance() {
    return new Gears();
  }

  /**
   * 装備品のリストを取得します。
   * <p>
   * 装備品のリストを返します。
   * </p>
   * @return 装備品のリスト
   */
  public List<?> getList() {
    return children;
  }

  /**
   * 装備品のリストを設定します。
   * <p>
   * 指定されたリストを装備品のリストとして設定します。
   * </p>
   * @param children 装備品のリスト
   */
  protected void setList(List<?> children) {
    this.children = (ArrayList<Gear>)children;
  }

  /**
   * 指定された装備の種類に一致する装備品を取得します。
   * <p>
   * 装備品のリストから、指定された装備の種類に一致する最初の装備品を返します。
   * </p>
   * @param gearType 装備の種類
   * @return 一致する装備品、存在しない場合はnull
   */
  public Gear getGear(GearType gearType) {
    for (Gear gear: children) {
      if (gear.getGearType() == gearType) {
        return gear;
      }
    }
    return null;
  }

}
