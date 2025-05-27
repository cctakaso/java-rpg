package rpg.objects;
import java.util.ArrayList;

import rpg.types.CharacterType;
import rpg.types.ItemType;
import rpg.types.StatusType;
import rpg.utils.Pt;

public class Item extends Base{
  protected ItemType itemType;
  protected int count;
  protected Statuses statuses;

  public Item() {
    this(100);
  }
  public Item(int weight) {
    super();
    this.count = 1;
    statuses = new Statuses();
    statuses.add(new Status(StatusType.Weight, weight));
  }

  public ItemType getItemType() {
    return this.itemType;
  }

  public int getCount() {
    return this.count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  public Statuses getStatuses() {
    return this.statuses;
  }

  public String toPrinting() {
    return super.toPrinting() + "x " + this.count + "["+ this.statuses.toPrinting() + "]";
  }

  public void addCount(Item item) {
    this.count += item.count;
  }

  public Item clone() {
    return this.clone(0, null);
  }

  public Item clone(int num, Pt randomPt) {
    Item copy = null;
    try {
      copy = (Item)super.clone(num, randomPt);
      copy.itemType = this.itemType;
      copy.count = this.count;
      copy.statuses = (Statuses)this.statuses.clone();
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public boolean isGear() {
    return this.itemType.isGear();
  }

  public boolean isMoney() {
    return this.itemType.isMoney();
  }

  public int getPrice() {
    for (Status status: (ArrayList<Status>)this.statuses.getList()) {
      if (status.type == StatusType.Money) {
        return status.point;
      }
    }
    return 0;
  }

}
