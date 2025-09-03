package rpg;

import rpg.controller.console.Controller;
import rpg.view.console.View;

/**
 * RPGアプリケーションのメインクラス。
 * <p>
 * このクラスは、Javaアプリケーションのエントリーポイントである{@code main}メソッドを含みます。
 * プログラムの起動、ゲームの初期化、そしてゲームループの開始を担当します。
 * </p>
 */

public class App {
    public static final String VERSION = "0.1.0"; // アプリケーションのバージョン
    public static final String NAME = "Java RPG Adventure"; // アプリケーションの名前
    public static View view = null;   // ビューのインスタンス
    public static Controller controller = null;   // コントローラーのインスタンス

    /**
     * アプリケーションの挨拶メッセージを返します。
     * @return 挨拶文字列 "Hello World!"
     */
    public String getGreeting() {
        return "Hello World!";
    }

    /**
     * RPGアプリケーションを起動するためのメインメソッド。
     * <p>
     * JVM（Java仮想マシン）によって最初に呼び出されるエントリーポイントです。
     * {@link Adventure}クラスのインスタンスを生成し、ゲームを開始します。
     * </p>
     * @param args コマンドライン引数（このアプリケーションでは使用されません）
     */
    public static void main(String[] args) {
        // 基本的な挨拶メッセージを表示
        //App.view.printMessage(new App().getGreeting());
        if (view == null) {
            view = View.get();   // ビューのインスタンスを取得
        }
        if (controller == null) {
            controller = Controller.get();   // コントローラーのインスタンスを取得
        }


        // 1. ゲームデータの初期化
        // Adventureクラスに、ゲームのタイトルと使用するマップデータの場所を教えて、ゲームの準備をさせる
        Adventure adventure = new Adventure();

        App.view.initialize(adventure); // ビューの初期化
        App.controller.initialize(adventure); // コントローラーの初期化

        // 2. ゲームのメインループを開始
        App.controller.startGame(); // ゲーム開始のメソッドを呼び出し


        // ゲームループが終了したら、最後のメッセージを表示
        App.view.printMessage("end");
    }
}
