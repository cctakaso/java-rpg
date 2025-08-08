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

/**
 * JSONデータの読み込みと解析（デシリアライズ）を担当するユーティリティクラス。
 * <p>
 * GoogleのGsonライブラリを利用して、JSONファイルをJavaオブジェクトに変換します。
 * リソースフォルダ内のJSONファイルを読み込み、ゲームデータとして利用可能な形式に変換する役割を担います。
 * </p>
 */
public class Json {
  // Gsonライブラリのインスタンス。スレッドセーフなので、staticで共有して良い。
  static Gson gson = new Gson();

  /**
   * クラス名（文字列）から、Gsonがデシリアライズに必要とする型情報（Type）を生成します。
   * <p>
   * JSONのファイル名（例: "Characters.json"）から、それがどのJavaの型に対応するのかを判断し、
   * {@code TypeToken} を使って具体的な型（例: {@code HashMap<String, Character>}）を返します。
   * </p>
   * @param className 型を特定するためのクラス名（通常はファイル名から拡張子を除いたもの）
   * @return 対応するJavaの型情報（Type）。見つからない場合はnull。
   */
  private static Type getTypeFromClassName(String className) {
    Type type = null;
    try{
      // ファイル名に応じて、適切な型をハードコードでマッピングしている
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
    }catch(Exception e){ // 本来は具体的な例外をキャッチすべき
      e.printStackTrace();
    }
    return type;
  }

  /**
   * 指定されたリソースパス内のすべてのJSONファイルを読み込み、データマップに移します。
   * @param datas 読み込んだデータを格納する先のマップ（Dicオブジェクト）
   * @param pathName JSONファイルが格納されているリソースフォルダのパス（例: "/dic"）
   */
  public static void getMapFromResouceJsons(HashMap<String, HashMap<String, Base>> datas, String pathName) {
    URL folderUrl = Json.class.getResource(pathName);
    List<String> fileNames = new ArrayList<>();
    if (folderUrl == null) {
      throw new IllegalArgumentException("Folder not found: " + pathName);
    }
    try {
        // 指定されたフォルダ内のファイル一覧を取得
        fileNames = Files.list(Paths.get(folderUrl.toURI()))
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
    } catch (IOException | URISyntaxException e) {
        throw new RuntimeException("Error reading folder: " + pathName, e);
    }

    // 各JSONファイルに対して処理を実行
    for(String fileName: fileNames) {
      // ファイル名から拡張子".json"を取り除き、クラス名として利用
      String  className = fileName.substring(0, fileName.length()-5);
      // クラス名から対応するJavaの型を取得
      Type type = getTypeFromClassName(className);
      // 実際にJSONファイルを読み込み、オブジェクトに変換
      HashMap<String, Base> map = Json.getMapfromJson(type, pathName+"/"+fileName);
      System.out.println(map);
      // 読み込んだデータを、クラス名をキーとして全体のデータマップに格納
      datas.put(className, map);
    }

  }

  /**
   * 一つのJSONファイルを読み込み、指定された型のHashMapに変換する内部メソッド。
   * @param type 変換先の型情報
   * @param fileName 読み込むJSONファイルのリソースパス
   * @return JSONから生成されたHashMap。失敗した場合はnull。
   */
	private static HashMap<String, Base> getMapfromJson(Type type, String fileName) {
    // try-with-resources構文で、ストリームが自動的にクローズされるようにする
    try (InputStream is = Json.class.getResourceAsStream(fileName);
        JsonReader reader =
          new JsonReader(new BufferedReader(new InputStreamReader(is)))) {
      if (gson==null) {
  			gson = new Gson();
	  	}
      // Gsonのメイン機能。JSONリーダーと型情報を渡してデシリアライズを実行。
      HashMap<String, Base> obj = (HashMap<String, Base>)gson.fromJson(reader, type);
      return obj;
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return null;
  }
}
