package rpg.objects;

import java.lang.reflect.Field;
import java.util.*;
import rpg.objects.*;
import rpg.types.*;
import rpg.utils.*;

public abstract class Base implements Cloneable, Reflection{
  protected String name;
  protected Pt pt;

  public Base() {
    this.name = new String();
    this.pt = new Pt();
  }

  public String getName() {
    return this.name;
  }
  public Pt getPt() {
    return this.pt;
  }
  public void setPt(Pt pt) {
    this.pt = pt;
  }

  public String toPrinting() {
    return this.name;
  }

  public Base clone(int num, Pt randomPt) {
    Base copy = null;
    try {
      copy = (Base)super.clone();
      copy.name = this.name != null ? this.name:null;
      copy.pt = this.pt!=null ? this.pt.clone():null;
      if (randomPt != null) {
        Random rand = new Random();
        copy.pt = new Pt(rand.nextInt(randomPt.x)/10*10, rand.nextInt(randomPt.y)/10*10);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return copy;
  }

  public void copy(Base base) {
    this.name = base.name;
    this.pt = base.pt;
  }

  public void scanClasses() {
    Class<?> cls = this.getClass();
    while(cls != null && cls.getPackageName() == "rpg.objects") { //!cls.isAssignableFrom(Base.class) && !cls.getSuperclass().isAssignableFrom(Enum.class)) {
      this.scanClass(cls);
      cls = cls.getSuperclass();
    }
    if (this instanceof Lists) {
      ((Lists)this).SetFromDic();
    }
  }


  public void scanClass(Class<?> cls) {
    Field[] fields = cls.getDeclaredFields();
    for (Field field: fields) {
      try {
        //if (!field.getDeclaredAnnotations().equals("rpg.objects.Characters")) {
        //  continue;
        //}
        Object val = field.get(this);
        if (val == null) {
        }else if (val instanceof Lists) {
          ((Lists)val).SetFromDic();
        }else if (val instanceof List) {
          for(Object son: (List)val) {
            if (son instanceof Reflection) {
              ((Reflection)son).scanClasses();
            }
          }
        }else{
          if (val instanceof Reflection) {
            ((Reflection)val).scanClasses();
          }
        }
      }catch(Exception ex) {
        ex.printStackTrace();
        System.err.println(ex.toString());
      }
    }
  }


}
