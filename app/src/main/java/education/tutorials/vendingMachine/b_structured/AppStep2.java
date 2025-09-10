package education.tutorials.vendingMachine.b_structured;

import java.util.Scanner;

public class AppStep2 {

    // ドリンク情報をクラスの静的フィールドとして持つ
    static String[] drinkNames = {"コーラ", "お茶", "水"};
    static int[] prices = {150, 120, 100};
    static int[] stocks = {5, 3, 10};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalAmount = 0;

        System.out.println("--- ドリンク自動販売機 ---");

        // 1. お金の投入
        totalAmount = insertMoney(scanner);
        System.out.println("現在の投入金額: " + totalAmount + "円");

        // 2. 購入可能なドリンクを表示
        displayAvailableDrinks(totalAmount);

        // 3. ドリンクを選択
        int choiceIndex = selectDrink(scanner, totalAmount);

        // 4. 購入処理
        if (choiceIndex != -1) { // -1は不正な選択を示す
            // ドリンクを排出
            dispenseDrink(drinkNames[choiceIndex]);
            
            // 在庫を減らす
            stocks[choiceIndex]--;

            // お釣りを計算して排出
            int change = totalAmount - prices[choiceIndex];
            returnChange(change);
        } else {
            // 購入失敗時
            System.out.println("エラー: 購入処理を中断しました。");
            returnChange(totalAmount); // 投入金額をそのままお釣りとして返す
        }

        scanner.close();
    }

    /**
     * お金の投入を受け付けるメソッド
     * @param scanner ユーザー入力用
     * @return 投入された金額
     */
    public static int insertMoney(Scanner scanner) {
        System.out.print("お金を投入してください: ");
        return scanner.nextInt();
    }

    /**
     * 購入可能なドリンクの一覧を表示するメソッド
     * @param totalAmount 現在の投入金額
     */
    public static void displayAvailableDrinks(int totalAmount) {
        System.out.println("--- 購入可能なドリンク ---");
        for (int i = 0; i < drinkNames.length; i++) {
            if (stocks[i] > 0 && prices[i] <= totalAmount) {
                System.out.println((i + 1) + ". " + drinkNames[i] + " (" + prices[i] + "円)");
            }
        }
        System.out.println("------------------------");
    }
    
    /**
     * ユーザーにドリンクを選択させるメソッド
     * @param scanner ユーザー入力用
     * @param totalAmount 現在の投入金額
     * @return 選択されたドリンクのインデックス。不正な場合は-1
     */
    public static int selectDrink(Scanner scanner, int totalAmount) {
        System.out.print("購入したいドリンクの番号を選択してください: ");
        int choice = scanner.nextInt();
        int choiceIndex = choice - 1;

        // 選択が妥当かチェック
        if (choiceIndex >= 0 && choiceIndex < drinkNames.length &&
            stocks[choiceIndex] > 0 && prices[choiceIndex] <= totalAmount) {
            return choiceIndex;
        } else {
            return -1; // 不正な選択
        }
    }

    /**
     * ドリンクを排出したことを表示するメソッド
     * @param drinkName 排出されたドリンクの名前
     */
    public static void dispenseDrink(String drinkName) {
        System.out.println("ガコン！「" + drinkName + "」が出てきました。");
    }

    /**
     * お釣りを排出したことを表示するメソッド
     * @param change お釣りの金額
     */
    public static void returnChange(int change) {
        System.out.println("お釣りは " + change + "円です。ありがとうございました。");
    }
}