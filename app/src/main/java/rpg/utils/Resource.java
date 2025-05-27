package rpg.utils;
import java.lang.reflect.Field;
import java.util.*;

import rpg.objects.Lists;
import rpg.objects.Base;


public class Resource {

  public static void load(Dic dic, String pathName) {
    Json.getMapFromResouceJsons(dic, pathName);
    buildScan(dic);
    System.out.println("Dic.load end");
  }

  //-----------------------------------------//

  static void buildScan(HashMap<String, HashMap<String, Base>> dic) {
    for(String type: dic.keySet()) {
      HashMap<String, Base> objs = dic.get(type);
      if (objs == null) {
        continue;
      }
      for(String key: objs.keySet()) {
        Base obj = objs.get(key);
        if (obj == null) {
          continue;
        }
        if (obj instanceof Base) {
          ((Base)obj).scanClasses();
        }
        //scanClasses(obj);
      }
    }
  }

  static void scanClasses(Object obj) {
    Class<?> cls = obj.getClass();
    while(cls != null && cls.getPackageName() == "rpg.objects") { //!cls.isAssignableFrom(Base.class) && !cls.getSuperclass().isAssignableFrom(Enum.class)) {
      scanClass(obj, cls);
      cls = cls.getSuperclass();
    }
    if (obj instanceof Lists) {
      ((Lists)obj).SetFromDic();
    }
  }


  static void scanClass(Object obj, Class<?> cls) {
    Field[] fields = cls.getDeclaredFields();
    for (Field field: fields) {
      try {
        Object val = field.get(obj);
        if (val == null) {
        }else if (val instanceof Lists) {
          ((Lists)val).SetFromDic();
        }else if (val instanceof List) {
          for(Object son: (List)val) {
            scanClasses(son);
          }
        }else{
          scanClasses(val);
        }
      }catch(Exception ex) {
        ex.printStackTrace();
      }
    }
  }

}
