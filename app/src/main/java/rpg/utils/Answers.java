package rpg.utils;
import java.util.*;

/**
 * 複数の選択肢（{@link Answer}）をまとめて管理するクラス。
 * <p>
 * 質問に対する回答の選択肢リストとして機能します。
 * 選択肢の追加、表示、ユーザーによる選択の受付など、
 * 対話式の選択処理における中心的な役割を担います。
 * </p>
 * @param <T> 各選択肢に関連付けられる値の型
 */
public class Answers<T>{
  // Answerオブジェクトを格納するリスト
  private final List<Answer<T>> list = new ArrayList<>();

  /**
   * リストに新しい選択肢を追加します。
   * <p>
   * 追加される選択肢には、現在のリストサイズに基づいたインデックスが自動的に設定されます。
   * </p>
   * @param answer 追加するAnswerオブジェクト
   */
  public void add(Answer<T> answer) {
      answer.setIndex(list.size());
      list.add(answer);
  }

  /**
   * 別のAnswersインスタンスが持つ全ての選択肢を、このリストに追加します。
   * @param answers 追加したい選択肢を持つAnswersオブジェクト
   */
  public void add(Answers<T> answers) {
      for (Answer<T> answer : answers.getList()) {
          this.add(answer);
      }
  }

  /**
   * 現在の選択肢の数を返します。
   * @return 選択肢の数
   */
  public int size() {
      return list.size();
  }

  /**
   * 指定されたインデックスの選択肢を取得します。
   * @param index 取得したい選択肢のインデックス（0から始まる）
   * @return 指定されたAnswerオブジェクト
   */
  public Answer<T> get(int index) {
      return list.get(index);
  }

  /**
   * 内部で保持している選択肢のリストを返します。
   * @return 選択肢の{@code List<Answer<T>>}
   */
  public List<Answer<T>> getList() {
      return list;
  }

  /**
   * コンソールに選択肢を整形して表示し、ユーザーからの入力を待ちます。
   * @param scanner ユーザー入力のためのScannerオブジェクト。nullの場合、ランダムに選択されます。
   * @param name プロンプトに表示する名前（例: プレイヤー名）
   * @param isPutTaileZero 選択肢の0番目を末尾に表示するかどうかのフラグ
   * @return ユーザーによって選ばれた、またはランダムに選ばれたAnswerオブジェクト
   */
  public Answer<T> printChoice(Scanner scanner, String name, boolean isPutTaileZero) {
    String str = "";
    // 選択肢の表示文字列を生成
    if (isPutTaileZero) {
      // 0番目を最後に表示する特殊な形式
      for(int index=1; index<this.size(); index++) {
        str += index + ":" + this.get(index).getLabel() + " ";
      }
      if (this.size()>0) {
        str += "0:" + this.get(0).getLabel();
      }
    }else{
      // 通常の形式 (1, 2, 3, ...)
      for(int index=0; index<this.size(); index++) {
        str += index+1 + ":" + this.get(index).getLabel() + " ";
      }
    }
    // scanがnullでなければ、生成した文字列を表示
    if (scanner!=null) {
      System.out.println();
      System.out.println("次のどれを選びますか？");
      System.out.println(str);
    }
    // 実際の選択処理を呼び出す
    return choice(scanner, name, isPutTaileZero);
  }

  /**
   * ユーザーからの選択を受け付ける内部メソッド。
   * @param scanner ユーザー入力のためのScannerオブジェクト。nullの場合、ランダムに選択されます。
   * @param name プロンプトに表示する名前
   * @param isPutTaileZero 0番目の扱いに関するフラグ
   * @return 選択されたAnswerオブジェクト
   */
  private Answer<T> choice(Scanner scanner, String name, boolean isPutTaileZero) {
    int choice = 0;
    // scanがnull、つまり自動選択の場合
    if (scanner == null) {
      Random random = new Random();
      choice = random.nextInt(this.size());
    }else if (this.size()>0){
      // ユーザーに入力を促す
      while(true) {
        try{
          System.out.print(name!=null ? name+">":">");
          choice = scanner.nextInt();
          // isPutTaileZeroがfalseの場合、ユーザー入力(1-based)をindex(0-based)に変換
          if (!isPutTaileZero) {
            choice--;
          }
          // 入力値が有効範囲内かチェック
          if (choice >= 0 && choice < this.size()) {
            break; // 有効な入力なのでループを抜ける
          }
        }catch (InputMismatchException ex){
          scanner.nextLine(); // 入力バッファをクリア
        } catch (NoSuchElementException e) {
          System.out.println("入力がありません。もう一度入力してください。");
          scanner.next(); // 無効な入力をスキップ
          System.exit(-1);
        }catch(Exception ex) {
          ex.printStackTrace();
          System.err.println(ex.toString());
          System.exit(-1);
        }
        System.out.println("正しい番号を入力して下さい");
      }
    }
    return this.get(choice);
  }
}
