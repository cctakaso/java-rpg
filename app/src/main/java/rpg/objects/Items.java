package rpg.objects;
import java.util.*;

import rpg.types.*;
import rpg.utils.*;

public class Items extends Lists{
  public ArrayList<Item> children;

  public Items() {
    super();
    children = new ArrayList<>();
  }

  protected Lists getNewInstance() {
    return new Items();
  }

  public List<?> getList() {
    return children;
  }

  protected void setList(List<?> children) {
    this.children = (ArrayList<Item>)children;
  }
  protected void add(Item item) {
    Item one = getSameTypeItem(item);
    if (one != null) {
      one.addCount(item);
    }else{
      super.add(item);
    }
  }

  public String toPrinting() {
    String str = "";
    for(Item one: children) {
      str += one.toPrinting() + " ";
    }
    return str;
  }

  public void transfer(Items items) {
    for(Item item: (ArrayList<Item>)items.getList()) {
      add(item);
    }
    items.getList().clear();
  }

  public Answers<Item> toAnswers(ItemType type, String info) {
    Answers<Item> list = new Answers<>();
    for (Item one: children) {
      if ((type==ItemType.Item && one.getItemType().isItem()) ||
            type==ItemType.Gear && one.getItemType().isGear() ||
            type==ItemType.Money && one.getItemType().isMoney()) {
          list.add(new Answer<Item>(one.toPrinting(), one, info));
      }
    }
    return list;
  }

  private Item doDecItem(Item item, int count) {
    if (item.getCount() <= count) {
      remove(item);
    }else{
      item.setCount(item.getCount() - count);
    }
    return item;
  }
  
  protected Item decItem(Item item, int count) {
    for(Item one: children) {
      if (one == item) {
        return doDecItem(item, count);
      }
    }
    return null;
  }

  protected Item decItem(String name, int count) {
    for(Item one: children) {
      if (one.name == name) {
        return doDecItem(one, count);
      }
    }
    return null;
  }

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
