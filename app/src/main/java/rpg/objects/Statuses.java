package rpg.objects;

import java.util.ArrayList;
import java.util.List;

import rpg.types.StatusType;

public class Statuses extends Lists{
  public ArrayList<Status> children;

  public Statuses() {
    super();
    children = new ArrayList<Status>();
  }

  protected Lists getNewInstance() {
    return new Statuses();
  }

  public List<?> getList() {
    return children;
  }

  protected void setList(List<?> children) {
    this.children = (ArrayList<Status>)children;
  }
  public String toPrinting() {
    String str = "";
    for(Status status: (ArrayList<Status>)getList()) {
      str += status.toPrinting() + " ";
    }
    return str;
  }

  public int getPoint(StatusType st) {
    Status status = getStatus(st);
    if (status != null) {
        return status.point;
    }
    return 0;
  }

  public int getRatedPoint(StatusType st) {
    Status status = getStatus(st);
    if (status != null) {
        return status.point * status.rate/100;
    }
    return 0;
  }

  private Status getStatus(StatusType st) {
    for (Status status: children) {
      if (status.type == st) {
        return status;
      }
    }
    return null;
  }
}
