package education.tutorials.vendingMachine.a_monolithic;
import java.util.Scanner;

public class AppStep1 {

    public static void main(String[] args) {
        // --- フィールド（変数）の定義 ---

        // ドリンクの情報を配列で管理する
        String[] drinkNames = {"コーラ", "お茶", "水"};
        int[] prices = {150, 120, 100};
        int[] stocks = {5, 3, 10};

        // 投入された合計金額
        int totalAmount = 0;

        // ユーザーからの入力を受け取るためのScanner
        Scanner scanner = new Scanner(System.in);

        // --- 処理部分 ---

        System.out.println("--- ドリンク自動販売機 ---");

        // お金の投入処理
        System.out.print("お金を投入してください: ");
        int insertedMoney = scanner.nextInt();
        totalAmount += insertedMoney;
        System.out.println("現在の投入金額: " + totalAmount + "円");

        // 購入可能なドリンクの表示
        System.out.println("--- 購入可能なドリンク ---");
        for (int i = 0; i < drinkNames.length; i++) {
            // 在庫があり、かつ値段が投入金額以下の場合に表示
            if (stocks[i] > 0 && prices[i] <= totalAmount) {
                System.out.println((i + 1) + ". " + drinkNames[i] + " (" + prices[i] + "円)");
            }
        }
        System.out.println("------------------------");

        // ドリンクの選択
        System.out.print("購入したいドリンクの番号を選択してください: ");
        int choice = scanner.nextInt();
        int choiceIndex = choice - 1; // 配列のインデックスに合わせる

        // 選択されたドリンクが購入可能かチェック
        if (choiceIndex >= 0 && choiceIndex < drinkNames.length &&
            stocks[choiceIndex] > 0 && prices[choiceIndex] <= totalAmount) {

            // 購入処理
            String selectedDrink = drinkNames[choiceIndex];
            int price = prices[choiceIndex];

            // 1. 在庫を減らす
            stocks[choiceIndex]--;

            // 2. ドリンクを排出する
            System.out.println("ガコン！「" + selectedDrink + "」が出てきました。");

            // 3. お釣りを計算して排出する
            int change = totalAmount - price;
            System.out.println("お釣りは " + change + "円です。ありがとうございました。");

        } else {
            // 購入できなかった場合
            System.out.println("エラー: 選択されたドリンクは購入できませんでした。");
            System.out.println("お釣りは " + totalAmount + "円です。");
        }
        
        // scannerを閉じる
        scanner.close();
    }
}