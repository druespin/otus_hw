package atm;

import cell.*;

public class Atm {

    private final Cell cell100;
    private final Cell cell200;
    private final Cell cell500;
    private final Cell cell1000;

    public Atm(int amount100, int amount200, int amount500, int amount1000) {
        this.cell100 = new Cell(100, amount100);
        this.cell200 = new Cell(200, amount200);
        this.cell500 = new Cell(500, amount500);
        this.cell1000 = new Cell(1000, amount1000);
        printBalance();
    }

    // пополнение одной ячейки, где noteAmount - кол-во банконот
    public void rechargeCell(ICell cell, int noteAmount) {
        System.out.print("Пополнение ячейки номиналом " + cell.getNominal() + ": ");
        cell.recharge(noteAmount);
        printBalance();
    }

    // снятие суммы денег = cashSum
    public void withdrawCash(int cashSum) {
        System.out.println("Снятие " + cashSum);
        if (cashSum < 1000) {
            withdrawCashUnder1000(cashSum);
        } else {
            cell1000.withdraw(cashSum / 1000);
            if (cashSum % 1000 > 0) {
                withdrawCashUnder1000(cashSum % 1000);
            }
        }
        printBalance();
    }

    private void withdrawCashUnder1000(int cashSum) {
        switch (cashSum) {
            case 100 -> cell100.withdraw(1);
            case 200 -> cell200.withdraw(1);
            case 300 -> {
                cell100.withdraw(1);
                cell200.withdraw(1);
            }
            case 400 -> cell200.withdraw(2);
            case 500 -> cell500.withdraw(1);
            case 600 -> cell200.withdraw(3);
            case 700 -> {
                cell200.withdraw(1);
                cell500.withdraw(1);
            }
            case 800 -> cell200.withdraw(4);
            case 900 -> {
                cell500.withdraw(1);
                cell200.withdraw(2);
            }
        }
    }

    public Cell getCellByNominal(int nominal) {
        return switch (nominal) {
            case 100 -> cell100;
            case 200 -> cell200;
            case 500 -> cell500;
            case 1000 -> cell1000;
            default -> throw new IllegalStateException("Unexpected value: " + nominal);
        };
    }

    private void printBalance() {
        System.out.println("Баланс банкомата: \n" +
                "1000 - " + cell1000.getCurrentAmount() + "\n" +
                "500 - " + cell500.getCurrentAmount() + "\n" +
                "200 - " + cell200.getCurrentAmount() + "\n" +
                "100 - " + cell100.getCurrentAmount() + "\n" +
                "Остаток: " + (100 * cell100.getCurrentAmount() +
                                200 * cell200.getCurrentAmount() +
                                500 * cell500.getCurrentAmount() +
                                1000 * cell1000.getCurrentAmount() +
                "\n--------")
        );
    }
}
