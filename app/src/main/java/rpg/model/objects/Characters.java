package rpg.model.objects;

import java.util.*;
import rpg.utils.*;

/**
 * キャラクターのリストを表すクラス。
 * <p>
 * キャラクターのリストは、キャラクターオブジェクトのArrayListとして保持され、
 * キャラクターの追加、削除、取得などの機能を提供します。
 * </p>
 */
public class Characters extends Lists{
  protected ArrayList<Character> children;  // キャラクターのリスト
  protected Character leader;               // リーダーキャラクター

  /**
   * デフォルトコンストラクタ。
   * <p>
   * キャラクターのリストを空のArrayListで初期化します。
   * </p>
   */
  public Characters() {
    super();
    children = new ArrayList<Character>();
  }

  /**
   * キャラクターのリストを取得します。
   * <p>
   * キャラクターのリストは、CharacterオブジェクトのArrayListとして保持されます。
   * </p>
   * @return キャラクターのリスト
   */
  @Override
  public List<Character> getList() {
    return children;
  }

  /**
   * キャラクターのリストからキャラクターを削除します。
   * <p>
   * 引数として渡されたCharacterオブジェクトを、キャラクターのリストから削除します。
   * </p>
   * @param character 削除するキャラクター
   * @return 削除したキャラクター
   */
  public Base remove(Base character) {
    if (character != null) {
      children.remove(character);
      if (leader == character) {
        // リーダーが削除された場合、次のキャラクターをリーダーとして設定
        if (children.size() > 0) {
          leader = children.get(0);
        } else {
          leader = null; // リストが空の場合はリーダーをnull
        }
      }
      return character;
    }
    return null;
  }

  /**
   * キャラクターのリストをクリアします。
   * <p>
   * キャラクターのリストを空にし、リーダーをnullに設定します。
   * </p>
   */
  public void clear() {
    children.clear();
    leader = null; // リストが空になった場合、リーダーをnull
  }

  /**
   * キャラクターのリストをリセットします。
   * <p>
   * キャラクターのリスト内のすべてのキャラクターのステータスをリセットします。
   * </p>
   */
  public void setReset() {
    for(Character one: children) {
      one.setReset();
    }
  }

  /**
   * キャラクターのリストを文字列として返します。
   * <p>
   * キャラクターのリスト内の各キャラクターの名前を含む文字列を返します。
   * </p>
   * @param excludeLord リーダーキャラクターを除外するかどうか
   * @return キャラクターのリストを表す文字列
   */
  public String toString(boolean excludeLord) {
    StringBuilder sb = new StringBuilder();
    for (int index = excludeLord ? 1 : 0; index < children.size(); index++) {
      Character one = children.get(index);
      sb.append(one.toString()).append("\n");
    }
    return sb.toString();
  }

  /**
   * キャラクターのリストを回答形式で取得します。
   * <p>
   * キャラクターのリストをAnswersオブジェクトとして返します。
   * </p>
   * @param excludeLord リーダーキャラクターを除外するかどうか
   * @return キャラクターのリストを表すAnswersオブジェクト
   */
  public Answers<Character> toAnswers(boolean excludeLord) {
    Answers<Character> list = new Answers<>();
    for(int index = excludeLord ? 1:0; index<children.size(); index++) {
      Character one = children.get(index);
      if (one.getHealthPoint()>0){
        list.add(new Answer<Character>(one.toString(), one));
      }
    }
    return list;
  }

  /**
   * キャラクターのリストを回答形式で取得します。
   * <p>
   * キャラクターのリストをAnswersオブジェクトとして返します。
   * </p>
   * @param gear 装備に適合するキャラクターのリストを取得
   * @return キャラクターのリストを表すAnswersオブジェクト
   */
  public Answers<Character> toAnswers(Gear gear) {
    Answers<Character> list = new Answers<>();
    for(Character one: children) {
      if (gear.isFiting(one)) {
        list.add(new Answer<Character>(one.toWithGearPrinting(gear.getGearType()), one));
      }
    }
    return list;
  }

  /**
   * 新しいインスタンスを取得します。
   * <p>
   * このメソッドは、Charactersクラスの新しいインスタンスを返します。
   * </p>
   * @return 新しいCharactersオブジェクト
   */
  protected Lists getNewInstance() {
    return new Characters();
  }

  /**
   * キャラクターをリストに追加します。
   * <p>
   * 引数として渡されたCharacterオブジェクトを、キャラクターのリストに追加します。
   * </p>
   * @param one 追加するキャラクター
   */
  public void add(Base one) {
    super.add(one);
    if (size()==1){
      leader = (Character)one;
    }
  }

  /**
   * キャラクターのリストを設定します。
   * <p>
   * 引数として渡されたリストを、キャラクターのリストとして設定します。
   * </p>
   * @param children キャラクターのリスト
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setList(List<?> children) {
    this.children = (ArrayList<Character>)children;
  }
}