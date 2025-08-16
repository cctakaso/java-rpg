package rpg.model.objects;
import java.util.*;

import rpg.utils.Answer;

/**
 * 複数のTalkオブジェクトを管理するためのリストクラスです。
 * Listsクラスを継承し、一連の会話の流れなどを表現するために使用されます。
 */
public class Talks extends Lists{

  protected ArrayList<Talk> children; // Talkオブジェクトのリスト

  /**
   * Talksの新しいインスタンスを生成します。
   * 内部のchildrenリストを初期化します。
   */
  public Talks() {
    super();
    children = new ArrayList<Talk>();
  }

  /**
   * 管理しているTalkのリストを返します。
   * @return Talkオブジェクトのリスト
   */
  @Override
  public List<Talk> getList() {
    return children;
  }

  /**
   * リスト内の会話を順に実行します。
   * 現在の実装では、リストの最初の会話のみを実行して終了します。
   * @param scan ユーザー入力用のScanner
   * @param allyParty プレイヤーのパーティ
   * @param otherCharacter 会話相手のキャラクター
   * @return プレイヤーの選択に対応するAnswerオブジェクト。会話がない場合はnull。
   */
  public Answer<Item> print(Scanner scan, Party allyParty, Character otherCharacter) {
    Answer<Item> rtn = null;
    // リスト内の最初のTalkを実行する
    for(Talk talk: children) {
      rtn = talk.print(scan, allyParty, otherCharacter);
      break; // 現在は最初の会話のみ実行
    }
    return rtn;
  }

  /**
   * Talksクラスの新しいインスタンスを返します。
   * @return 新しいTalksインスタンス
   */
  protected Lists getNewInstance() {
    return new Talks();
  }

  /**
   * 管理するTalkのリストを設定します。
   * @param children 設定するTalkオブジェクトのリスト
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setList(List<?> children) {
    this.children = (ArrayList<Talk>)children;
  }
}