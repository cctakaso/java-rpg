package rpg.utils;

/**
 * プロジェクト全体で利用される、汎用的な静的メソッドを提供するユーティリティクラス。
 */
public class Util {
  /**
   * 文字列を整数（int）に変換します。
   * <p>
   * 変換に失敗した場合は、例外を発生させる代わりに-1を返します。
   * </p>
   * @param str 変換対象の文字列
   * @return 変換された整数値。変換できない場合は-1。
   */
  public static int valueOf(String str) {
    try{
      // Java標準のInteger.valueOfを使用して変換を試みる
      return Integer.valueOf(str);
    }catch(Exception ex) {
      // NumberFormatExceptionなど、あらゆる例外をキャッチする
      // 本来は、より具体的な例外をキャッチすることが望ましい
    }
    // 変換に失敗した場合は-1を返す
    return -1;
  }
}