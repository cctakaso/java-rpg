package rpg;
/**
 * RPGアプリケーションのメインクラス。
 * <p>
 * このクラスは、Javaアプリケーションのエントリーポイントである{@code main}メソッドを含みます。
 * プログラムの起動、ゲームの初期化、そしてゲームループの開始を担当します。
 * </p>
 */

public class App {
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
        //System.out.println(new App().getGreeting());

        // 1. ゲームデータの初期化
        // Adventureクラスに、使用するマップデータの場所を教えて、ゲームの準備をさせる
        Adventure.initialize("/map/vol.1");
        System.out.println("let's start!");

        // 2. ゲームのメインループを開始
        // Adventureクラスに、ゲームのタイトルを渡して、冒険を開始させる
        Adventure.start("冒険の書・第一章");

        // ゲームループが終了したら、最後のメッセージを表示
        System.out.println("end");
    }
}
