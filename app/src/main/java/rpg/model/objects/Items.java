package rpg.model.objects;
import java.util.*;

import rpg.model.types.*;
import rpg.utils.*;

/**
 * アイテムのリストを表すクラス。
 * <p>
 * アイテムのリストは、アイテムオブジェクトのArrayListとして保持され、
 * アイテムの追加、削除、取得などの機能を提供します。
 * </p>
 */
public class Items extends Lists{
  protected ArrayList<Item> children;    // アイテムのリスト

  /**
   * デフォルトコンストラクタ。
   * <p>
   * アイテムのリストを空のArrayListで初期化します。
   * </p>
   */
  public Items() {
    super();
    children = new ArrayList<>();
  }

  /**
   * 新しいインスタンスを取得します。
   * <p>
   * このメソッドは、Itemsクラスの新しいインスタンスを返します。
   * </p>
   * @return 新しいItemsオブジェクト
   */
  protected Lists getNewInstance() {
    return new Items();
  }

  /**
   * アイテムのリストを取得します。
   * <p>
   * アイテムのリストは、ItemオブジェクトのArrayListとして保持されます。
   * </p>
   * @return アイテムのリスト
   */
  @Override
  public List<Item> getList() {
    return children;
  }

  /**
   * アイテムのリストを設定します。
   * <p>
   * 引数として渡されたリストを、アイテムのリストとして設定します。
   * </p>
   * @param children アイテムのリスト
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void setList(List<?> children) {
    this.children = (ArrayList<Item>)children;
  }

  /**
   * Baseオブジェクトをリストに追加します。
   * <p>
   * 引数として渡されたBaseオブジェクトがItemのインスタンスであれば、
   * Itemとしてリストに追加します。
   * </p>
   * @param one 追加するBaseオブジェクト
   */
  protected void add(Base one) {
    super.add(one);
    if (one instanceof Item) {
      add((Item)one);
    }
  }
  /**
   * アイテムをリストに追加します。
   * <p>
   * 同じ種類のアイテムが既に存在する場合は、その数量を増やします。
   * </p>
   * @param item 追加するアイテム
   */
  protected void add(Item item) {
    Item one = getSameTypeItem(item);
    if (one != null) {
      one.addCount(item);
    }else{
      super.add(item);
    }
  }

  /**
   * アイテムの文字列表現を返します。
   * <p>
   * アイテムのリストに含まれるすべてのアイテムの文字列表現を連結して返します。
   * </p>
   * @return アイテムの文字列表現
   */
  public String toString() {
    String str = "";
    for(Item one: children) {
      str += one.toString() + " ";
    }
    return str;
  }

  /**
   * アイテムを他のItemsオブジェクトに転送します。
   * <p>
   * 引数として渡されたItemsオブジェクトに、現在のアイテムのリストを追加します。
   * </p>
   * @param items 転送先のItemsオブジェクト
   */
  public void transfer(Items items) {
    for(Item item: (ArrayList<Item>)items.getList()) {
      add(item);
    }
    items.getList().clear();
  }

  /**
   * アイテムのリストをAnswers<Item>形式で取得します。
   * <p>
   * アイテムの種類に応じて、ItemTypeを指定し、アイテムの情報を含むAnswers<Item>オブジェクトを返します。
   * </p>
   * @param type アイテムの種類（ItemType）
   * @param info アイテムに関する追加情報
   * @return アイテムのAnswers<Item>オブジェクト
   */
  public Answers<Item> toAnswers(ItemType type, String info) {
    Answers<Item> list = new Answers<>();
    for (Item one: children) {
      if ((type==ItemType.Item && one.getItemType().isItem()) ||
            type==ItemType.Gear && one.getItemType().isGear() ||
            type==ItemType.Money && one.getItemType().isMoney()) {
          list.add(new Answer<Item>(one.toString(), one, info));
      }
    }
    return list;
  }

  /**
   * アイテムの数量を減らします。
   * <p>
   * 指定されたアイテムの数量を減らし、数量が0以下になった場合はリストから削除します。
   * </p>
   * @param item 減らすアイテム
   * @param count 減らす数量
   * @return 減らしたアイテム
   */
  private Item doDecItem(Item item, int count) {
    if (item.getCount() <= count) {
      remove(item);
    }else{
      item.setCount(item.getCount() - count);
    }
    return item;
  }

  /**
   * アイテムの数量を減らします。
   * <p>
   * 指定されたアイテムの数量を減らします。
   * </p>
   * @param item 減らすアイテム
   * @param count 減らす数量
   * @return 減らしたアイテム
   */
  public Item decItem(Item item, int count) {
    for(Item one: children) {
      if (one == item) {
        return doDecItem(item, count);
      }
    }
    return null;
  }

  /**
   * アイテムの数量を減らします。
   * <p>
   * 指定された名前のアイテムの数量を減らします。
   * </p>
   * @param name 減らすアイテムの名前
   * @param count 減らす数量
   * @return 減らしたアイテム
   */
  public Item decItem(String name, int count) {
    Item item = findItem(name);
    if (item != null) {
      return doDecItem(item, count);
    }
    return null;
  }

  /**
   * アイテムのリストから指定された名前のアイテムを検索します。
   * <p>
   * 指定された名前のアイテムをリストから検索し、最初に見つかったアイテムを返します。
   * </p>
   * @param name 検索するアイテムの名前
   * @return 見つかったアイテム、見つからない場合はnull
   */
  private Item findItem(String name) {
    for(Item one: children) {
      if (one.name.equals(name)) {
        return one;
      }
    }
    return null;
  }

  /**
   * アイテムの同じ種類のアイテムを取得します。
   * <p>
   * 指定されたアイテムと同じ種類のアイテムをリストから検索し、最初に見つかったアイテムを返します。
   * </p>
   * @param item 検索するアイテム
   * @return 同じ種類のアイテム、見つからない場合はnull
   */
  protected Item getSameTypeItem(Item item) {
    if (item.isGear()) {
      return null;
    }
    for(Item one: children) {
      if (one.getItemType() == item.getItemType() && one.name.equals(item.name)) {
        return one;
      }
    }
    return null;
  }
}