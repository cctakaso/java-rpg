package education.tutorials.vendingMachine.c_objectOriented;

import java.util.ArrayList;
import java.util.List;

// 自動販売機本体の機能を持つクラス
public class VendingMachine {
    // フィールド
    private List<Drink> drinks; // Drinkオブジェクトのリストで商品を管理
    private int currentAmount; // 現在の投入金額

    // コンストラクタ
    public VendingMachine() {
        this.drinks = new ArrayList<>();
        this.currentAmount = 0;
        // 初期商品をセット
        this.drinks.add(new Drink("コーラ", 150, 5));
        this.drinks.add(new Drink("お茶", 120, 3));
        this.drinks.add(new Drink("水", 100, 10));
    }

    // お金を受け入れるメソッド
    public void insertMoney(int money) {
        this.currentAmount += money;
        System.out.println("現在の投入金額: " + this.currentAmount + "円");
    }

    // 購入可能なドリンク一覧を表示するメソッド
    public void displayAvailableDrinks() {
        System.out.println("--- 購入可能なドリンク ---");
        for (int i = 0; i < drinks.size(); i++) {
            Drink drink = drinks.get(i);
            // 在庫があり、値段が投入金額以下の場合
            if (drink.hasStock() && drink.getPrice() <= this.currentAmount) {
                System.out.println((i + 1) + ". " + drink.getName() + " (" + drink.getPrice() + "円)");
            }
        }
        System.out.println("------------------------");
    }

    // ドリンクを購入するメソッド
    public void purchase(int drinkIndex) {
        // 選択されたドリンクを取得
        Drink selectedDrink = drinks.get(drinkIndex);

        // 購入可能か最終チェック
        if (!selectedDrink.hasStock()) {
            System.out.println("エラー: 売り切れです。");
            return; // 処理を中断
        }
        if (selectedDrink.getPrice() > this.currentAmount) {
            System.out.println("エラー: お金が足りません。");
            return; // 処理を中断
        }
        
        // 在庫を減らす
        selectedDrink.decreaseStock();
        // 投入金額から代金を引く
        this.currentAmount -= selectedDrink.getPrice();
        
        // ドリンク排出
        System.out.println("ガコン！「" + selectedDrink.getName() + "」が出てきました。");
    }

    // お釣りを返すメソッド
    public int returnChange() {
        int change = this.currentAmount;
        this.currentAmount = 0; // 投入金額をリセット
        return change;
    }
}