package rpg.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Files;

import rpg.objects.Character;
import rpg.objects.Fields;
import rpg.objects.Gear;
import rpg.objects.Item;
import rpg.objects.Attack;
import rpg.objects.Talk;
import rpg.objects.Party;
import rpg.objects.Base;

public class Json {
  static Gson gson = new Gson();

  private static <T> Type getTypeFromClassName(String className) {
    Type type = null;
    try{
      if (className.equals("Characters")) {
        type = new TypeToken<HashMap<String, Character>>(){}.getType();
      }else if (className.equals("Fields")) {
        type = new TypeToken<HashMap<String, Fields>>(){}.getType();
      }else if (className.equals("Gears")) {
        type = new TypeToken<HashMap<String, Gear>>(){}.getType();
      }else if (className.equals("Items")) {
        type = new TypeToken<HashMap<String, Item>>(){}.getType();
      }else if (className.equals("Attacks")) {
        type = new TypeToken<HashMap<String, Attack>>(){}.getType();
      }else if (className.equals("Talks")) {
        type = new TypeToken<HashMap<String, Talk>>(){}.getType();
      }else if (className.equals("Parties")) {
        type = new TypeToken<HashMap<String, Party>>(){}.getType();
      }
    }catch(Exception e){} //you must specify concrete Exception here
    return type;
  }

  public static void getMapFromResouceJsons(HashMap<String, HashMap<String, Base>> datas, String pathName) {
    URL folderUrl = Json.class.getResource(pathName);
    List<String> fileNames = new ArrayList<>();
    if (folderUrl == null) {
      throw new IllegalArgumentException("Folder not found: " + pathName);
    }
    try {
        fileNames = Files.list(Paths.get(folderUrl.toURI()))
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
    } catch (IOException | URISyntaxException e) {
        throw new RuntimeException("Error reading folder: " + pathName, e);
    }

    //Collections.sort(fileNames);
    for(String fileName: fileNames) {
      //int index = fileName.indexOf(".")+1;
      String  className = fileName.substring(0, fileName.length()-5);
      Type type = getTypeFromClassName(className);
      HashMap<String, Base> map = Json.getMapfromJson(type, pathName+"/"+fileName);
      System.out.println(map);
      datas.put(className, map);
    }

  }

	private static HashMap<String, Base> getMapfromJson(Type type, String fileName) {
// JSONファイルからの読み込み
    try (InputStream is = Json.class.getResourceAsStream(fileName);
        JsonReader reader =
          new JsonReader(new BufferedReader(new InputStreamReader(is)))) {
      if (gson==null) {
  			gson = new Gson();
	  	}
      //Type mapType = new TypeToken<Map<String, String>>() {}.getType();
      HashMap<String, Base> obj = (HashMap<String, Base>)gson.fromJson(reader, type);
      return obj;
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return null;
  }
}
