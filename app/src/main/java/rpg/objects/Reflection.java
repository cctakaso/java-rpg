package rpg.objects;

/**
 * リフレクションを用いてクラスをスキャンするためのインターフェースです。
 * このインターフェースを実装するクラスは、
 * クラスパス上のクラスを動的に走査し、特定の処理を行う機能を提供します。
 */
public interface Reflection {
  /**
   * クラスパス上のすべてのクラスをスキャンします。
   */
  public void scanClasses();

  /**
   * 指定されたクラスをスキャンします。
   * @param cls スキャン対象のクラス
   */
  public void scanClass(Class<?> cls);
}