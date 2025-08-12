package rpg.model.objects;
import java.util.*;
import rpg.utils.Pt;

/**
 * 複数のパーティ(Party)を管理するためのリストクラスです。
 * Listsクラスを継承し、Partyオブジェクトのコレクションを扱います。
 */
public class Parties extends Lists{
  /**
   * Partyオブジェクトを格納するリスト
   */
  public ArrayList<Party> children;

  /**
   * Partiesの新しいインスタンスを生成します。
   * 内部のchildrenリストを初期化します。
   */
  public Parties() {
    super();
    children = new ArrayList<Party>();
  }

  /**
   * Partiesクラスの新しいインスタンスを返します。
   * @return 新しいPartiesインスタンス
   */
  protected Lists getNewInstance() {
    return new Parties();
  }

  /**
   * 管理しているPartyのリストを返します。
   * @return Partyオブジェクトのリスト
   */
  @Override
  public List<Party> getList() {
    return children;
  }

  /**
   * 管理するPartyのリストを設定します。
   * @param children 設定するPartyオブジェクトのリスト
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setList(List<?> children) {
    this.children = (ArrayList<Party>)children;
  }

  /**
   * 指定された座標に存在する、指定されたパーティ以外のパーティのリストを取得します。
   * @param mParty 除外するパーティ（通常はプレイヤーのパーティ）
   * @param pt 検索する座標
   * @return 条件に一致するパーティを含む新しいListsオブジェクト。見つからない場合はnull。
   */
  @SuppressWarnings("unchecked")
  public Lists getHitInstance(Party mParty, Pt pt) {
    try {
      Lists lsts = getNewInstance();
      List<Party> lst = getList();
      for(Base one: lst) {
        // 自分自身のパーティは除外して、指定座標にいるパーティを検索
        if (one != mParty && one.pt.isEquals(pt)) {
          lsts.add(one);
        }
      }
    return lst.size()> 0 ? lsts:null;
    }catch(Exception ex) {
      // エラーが発生した場合は何もしない
    }
    return null;
  }
}