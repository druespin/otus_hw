package cell;

import exception.OutOfCashException;

public class Cell implements ICell {

    private final int nominal;
    private int currentAmount;

    public Cell(int nominal, int initialAmount) {
        this.currentAmount = initialAmount;
        this.nominal = nominal;
    }

    @Override
    public int getNominal() {
        return nominal;
    }

    @Override
    public int getCurrentAmount() {
        return currentAmount;
    }

    @Override
    public void recharge(int amount) {
        currentAmount = currentAmount + amount;
        System.out.println("Добавлено " + amount + " банкнот номиналом " + this.getNominal());
    }

    @Override
    public void withdraw(int amount) {
        currentAmount = currentAmount - amount;
        System.out.println("Снято " + amount + " банкнот номиналом " + nominal + "\n");
    }
}
