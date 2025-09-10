package education.tutorials.vendingMachine.c_objectOriented;

import java.util.Scanner;

// プログラムを実行し、ユーザーとのやり取りを担当するクラス
public class AppStep3 {
    public static void main(String[] args) {
        // VendingMachineのインスタンスを生成
        VendingMachine vm = new VendingMachine();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- ドリンク自動販売機 ---");

        // 1. お金の投入
        System.out.print("お金を投入してください: ");
        int money = scanner.nextInt();
        vm.insertMoney(money);

        // 2. 購入可能なドリンクを表示
        vm.displayAvailableDrinks();

        // 3. ドリンクを選択
        System.out.print("購入したいドリンクの番号を選択してください: ");
        int choice = scanner.nextInt();
        int choiceIndex = choice - 1;

        // 4. 購入処理
        // ここでは簡単なチェックのみ。詳細なチェックはVendingMachineクラスに任せる
        if (choiceIndex >= 0 && choiceIndex < 3) { // 商品が3種類なので
            vm.purchase(choiceIndex);
        } else {
            System.out.println("エラー: 存在しない商品番号です。");
        }
        
        // 5. お釣りの排出
        int change = vm.returnChange();
        System.out.println("お釣りは " + change + "円です。ありがとうございました。");
        
        scanner.close();
    }
}