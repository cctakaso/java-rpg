package rpg.objects;
import java.lang.reflect.Field;
import java.util.*;

import rpg.Adventure;
import rpg.utils.*;
import rpg.types.*;

public abstract class Lists extends Base{
  protected ArrayList<String> names;
  protected ArrayList<Integer> numbers;
  protected Pt randomPt;

  public abstract List<?> getList();
  protected abstract void setList(List<?> list);
  protected abstract Lists getNewInstance();

  public Lists() {
    super();
  }

  public int size() {
    return getList().size();
  }

  public Base remove(int index) {
    List<Base> list = (List<Base>)getList();
    return list.remove(index);
  }

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

  protected void add(Base one) {
    List<Base> list = (List<Base>)getList();
    list.add(one);
  }

  public void removeHitInstance(Lists targetLsts) {
    try {
      Lists lsts = getNewInstance();
      List<Base> lst = (List<Base>)getList();
      List<Base> targetLst = (List<Base>)targetLsts.getList();
      for(Base one: targetLst) {
        lst.remove(lst.indexOf(one));
      }
    }catch(Exception ex) {
    }
  }

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
    }
    return null;
  }

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

  public void SetFromDic() {
    List<Base> list = (List<Base>)getList();
    if (this.names == null) {
      return;
    } else {
      if (list.size()>0) {
        for (int i=0; i<list.size(); i++) {
          list.set(i, list.get(i).clone(i, this.randomPt));
        }
      }else{
        int index = 0;
        if (this.names != null && this.numbers != null) {
          if (this.names.size() != this.numbers.size()) {
            System.out.println("Error: count unmatch > numbers != names");
          }
        }
        for (String name: this.names) {
          String type = this.getClass().getName();
          type = type.substring(type.lastIndexOf(".")+1);
          List<Base> objs = Adventure.getDicClones(type, name, this.numbers!=null ? this.numbers.get(index):1, this.randomPt);
          if (objs == null) {
            //商人が取り扱う場合は、GearsもItemsタイプで取得される
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

  private void SetOtherFields() {
    List<Base> listDst = (List<Base>)getList();
    Class<?> cls = this.getClass();
    Field[] fields = cls.getDeclaredFields();
    for (Field field: fields) {
      try{
        Object val = field.get(this);
        if (val instanceof DicList) {
          String dstFieldName = field.getName().substring(0, field.getName().length()-1);
          DicList listSrc = (DicList)val;
          Class<?> dstCls = cls;
          Field dstField = null;
          while(dstField==null) {
            try{
              dstField = dstCls.getDeclaredField(dstFieldName);
            }catch(Exception ex) {
              dstCls = dstCls.getSuperclass();
            }
          }
          int index=0;
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
