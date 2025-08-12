package rpg.model.objects;
import java.lang.reflect.Field;
import java.util.*;

import rpg.Adventure;
import rpg.utils.*;

/**
 * Baseクラスを継承するオブジェクトのリストを管理する抽象クラスです。
 * このクラスは、リストの基本的な操作（追加、削除、クローン）や、
 * 辞書データからのリストの初期化機能を提供します。
 * @param <T> リストに含まれる要素の型
 */
public abstract class Lists extends Base{
  /**
   * 辞書からオブジェクトを生成する際に使用する名前のリスト
   */
  protected ArrayList<String> names;
  /**
   * 辞書からオブジェクトを生成する際に使用する数量のリスト
   */
  protected ArrayList<Integer> numbers;
  /**
   * オブジェクトのランダムな位置を生成するための座標
   */
  protected Pt randomPt;

  /**
   * 管理しているオブジェクトのリストを取得します。
   * @return オブジェクトのリスト
   */
  public abstract List<?> getList();

  /**
   * 管理するオブジェクトのリストを設定します。
   * @param list 設定するオブジェクトのリスト
   */
  protected abstract void setList(List<?> list);

  /**
   * このクラスの新しいインスタンスを生成します。
   * @return 新しいListsインスタンス
   */
  protected abstract Lists getNewInstance();

  /**
   * デフォルトコンストラクタ
   */
  public Lists() {
    super();
  }

  /**
   * リストのサイズを返します。
   * @return リストの要素数
   */
  public int size() {
    return getList().size();
  }

  /**
   * 指定されたインデックスの要素をリストから削除します。
   * @param index 削除する要素のインデックス
   * @return 削除された要素
   */
  public Object remove(int index) {
    return getList().remove(index);
  }

  /**
   * 指定されたインスタンスをリストから削除します。
   * @param target 削除する対象のインスタンス
   * @return 削除されたインスタンス。見つからない場合はnull。
   */
  @SuppressWarnings("unchecked")
  public Base remove(Base target) {
    List<Base> list = (List<Base>)getList();
    int index = 0;
    for (Base one: list) {
      if (one == target) {
        list.remove(index);
        return target;
      }
      index++;
    }
    return null;
  }

  /**
   * リストに要素を追加します。
   * @param one 追加する要素
   */
  @SuppressWarnings("unchecked")
  protected void add(Base one) {
    List<Base> list = (List<Base>)getList();
    list.add(one);
  }

  /**
   * ターゲットリストに含まれるインスタンスを現在のリストから削除します。
   * <p>
   * 削除中に例外が発生した場合は、処理を中断し、エラーを無視します。
   * これは、例えば削除対象のインスタンスが既にリストに存在しない場合などに発生し得ます。
   * </p>
   * @param targetLsts 削除対象のインスタンスを含むListsオブジェクト
   */
  @SuppressWarnings("unchecked")
  public void removeHitInstance(Lists targetLsts) {
    try {
      List<Base> lst = (List<Base>)getList();
      List<Base> targetLst = (List<Base>)targetLsts.getList();
      for(Base one: targetLst) {
        lst.remove(lst.indexOf(one));
      }
    }catch(Exception ex) {
      // エラーが発生した場合は何もしない
    }
  }

  /**
   * 指定された座標に存在するインスタンスのリストを取得します。
   * <p>
   * 検索中に例外が発生した場合は、処理を中断し、エラーを無視します。
   * </p>
   * @param pt 検索する座標
   * @return 指定された座標に存在するインスタンスを含む新しいListsオブジェクト。見つからない場合はnull。
   */
  @SuppressWarnings("unchecked")
  public Lists getHitInstance(Pt pt) {
    try {
      Lists lsts = getNewInstance();
      List<Base> lst = (List<Base>)getList();
      for(Base one: lst) {
        if (one.pt.isEquals(pt)) {
          lsts.add(one);
        }
      }
    return lst.size()> 0 ? lsts:null;
    }catch(Exception ex) {
      // エラーが発生した場合は何もしない
    }
    return null;
  }

  /**
   * このListsオブジェクトのディープコピーを作成します。
   * @return このオブジェクトのクローン
   */
  @SuppressWarnings("unchecked")
  @Override
  public Lists clone() {
    Lists copy = null;
    try {
      copy = (Lists)super.clone();
      copy.names = this.names!=null ? (ArrayList<String>)this.names.clone():null;
      List<Base> list = (List<Base>)getList();
      List<Base> copyList = new ArrayList<Base>();
      setList(copyList);
      int num = 1;
      for(Base one: list) {
        copyList.add(one.clone(num++, this.randomPt));
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  /**
   * 辞書データ（names, numbers）を元にリストを初期化または更新します。
   * namesフィールドに設定された名前とnumbersフィールドに設定された数量を使って、
   * Adventureクラスの辞書からオブジェクトをクローンし、リストに追加します。
   */
  @SuppressWarnings("unchecked")
  public void SetFromDic() {
    List<Base> list = (List<Base>)getList();
    if (this.names == null) {
      return;
    } else {
      if (list.size()>0) {
        // リストが既に存在する場合は、各要素をクローンして更新する
        for (int i=0; i<list.size(); i++) {
          list.set(i, list.get(i).clone(i, this.randomPt));
        }
      }else{
        // リストが空の場合は、辞書から新しい要素を生成する
        int index = 0;
        if (this.names != null && this.numbers != null) {
          if (this.names.size() != this.numbers.size()) {
            System.out.println("Error: count unmatch > numbers != names");
          }
        }
        for (String name: this.names) {
          String type = this.getClass().getName();
          type = type.substring(type.lastIndexOf(".")+1);
          // Adventureの辞書からオブジェクトをクローン
          List<Base> objs = Adventure.getDicClones(type, name, this.numbers!=null ? this.numbers.get(index):1, this.randomPt);
          if (objs == null) {
            // 特殊ケース：Itemsが見つからない場合、Gearsも検索する。
            // これは、例えば商人がアイテムとしてギアを販売する場合など、
            // ItemsとGearsが相互に関連するデータとして扱われることを想定しています。
            if (type.equals("Items")) {
              objs = Adventure.getDicClones("Gears", name, this.numbers!=null ? this.numbers.get(index):1, this.randomPt);
            }
            if (objs == null) {
              continue;
            }
          }
          list.addAll(objs);
          index++;
        }
        SetOtherFields();
      }
    }
  }

  /**
   * リフレクションを使用して、このクラスの他のフィールド値をリスト内の各オブジェクトに設定します。
   * <p>
   * ArrayList<?>型のフィールドを検出し、その内容をリスト内の対応するオブジェクトのフィールドに設定します。
   * この際、スーパークラスのフィールドも考慮して検索を行います。
   * </p>
   */
  @SuppressWarnings("unchecked")
  private void SetOtherFields() {
    List<Base> listDst = (List<Base>)getList();
    Class<?> cls = this.getClass();
    Field[] fields = cls.getDeclaredFields();
    for (Field field: fields) {
      try{
        Object val = field.get(this);
        // フィールドがArrayListのインスタンスかチェック
        if (val instanceof ArrayList) {
          // 対応する宛先フィールド名を取得（例：talks -> talk）
          String dstFieldName = field.getName().substring(0, field.getName().length()-1);
          ArrayList<?> listSrc = (ArrayList<?>)val;
          Class<?> dstCls = cls;
          Field dstField = null;
          // スーパークラスを含めて宛先フィールドを検索
          while(dstField==null) {
            try{
              dstField = dstCls.getDeclaredField(dstFieldName);
            }catch(Exception ex) {
              dstCls = dstCls.getSuperclass();
            }
          }
          int index=0;
          // ソースリストの各要素を宛先オブジェクトのフィールドに設定
          for(Object srcObj:listSrc) {
            Object dstObj = listDst.get(index++);
            dstField.set(dstObj, srcObj);
          }
        }
      }catch(Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}