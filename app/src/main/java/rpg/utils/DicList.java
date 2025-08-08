package rpg.utils;
import java.util.*;

/**
 * ジェネリクスを使用したArrayListの単純な拡張クラス。
 * <p>
 * このクラスの主な目的は、特定の型{@code <E>}でリストを扱う際に、
 * getメソッドの戻り値を明示的にキャストする手間を省くことにあると考えられます。
 * しかし、現代のJavaではジェネリクスが標準で強力にサポートされているため、
 * このクラスの必要性は低いかもしれません。
 * </p>
 * @param <E> リストに格納される要素の型
 */
public class DicList<E> extends ArrayList<E>{
  /**
   * 指定されたインデックスの要素を返します。
   * <p>
   * 親クラスであるArrayListのgetメソッドを呼び出しますが、
   * このクラス自体がジェネリクスで型付けされているため、
   * 呼び出し元でキャストは不要です。
   * </p>
   * @param index 取得する要素のインデックス
   * @return 指定されたインデックスにある要素
   */
  public E get(int index) {
    // 親クラスのgetを呼び出すだけで、特別な処理はしていない
    return (E)super.get(index);
  }
}
