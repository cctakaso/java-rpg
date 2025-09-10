package education.tutorials.restaurant.c_objectOriented;

public class AppStep3 {
    public static void main(String[] args) {
        System.out.println("---- レストランシミュレーション開始 (Step 3) ----");

        // 登場人物のインスタンス化
        Customer customer = new Customer("山田");
        Waiter waiter = new Waiter("田中");
        Cook cook = new Cook("佐藤");
        Cashier cashier = new Cashier("鈴木");

        // 1. お客が来店する
        customer.arrive();

        // 2. ウェイターは来店客を席に誘導する
        waiter.guideToSeat(customer, "A-1");

        // 3. ウェイターはメニューを提示して注文を受け付けオーダー用紙に書き込む
        customer.order(waiter, "ラーメン"); // 顧客が注文し、ウェイターがオーダーシートを作成

        // 4. ウェイターはコックにオーダー用紙と共に注文を伝える
        waiter.passOrderToCook(cook, customer.getOrderSheet());

        // 5. コックはオーダー用紙に基づき順番に料理を行う
        String cookedMeal = cook.cook(customer.getOrderSheet());

        // 6. 料理が出来たら、ウェイターは料理とそのオーダー用紙を、注文したお客の席に正しく届ける
        waiter.serveMeal(customer, cookedMeal);
        customer.eat(); // お客が食事をする

        // 7. お客は食べ終わったらオーダー用紙を持って会計場所に持っていく
        customer.goToCashier();

        // 8. 会計場所で、会計担当はお客が持ってきたオーダー用紙から金額を計算してお客に伝える
        // 9. お客はお金を支払い出店する
        customer.pay(cashier);
        customer.exit();

        System.out.println("---- レストランシミュレーション終了 (Step 3) ----");
    }
}
