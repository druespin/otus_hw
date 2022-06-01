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
    public int getCurrentAmount() {
        return currentAmount;
    }

    @Override
    public void recharge(int amount) {
        currentAmount = currentAmount + amount;
        System.out.println("Добавлено " + amount + " банкнот");
    }

    @Override
    public int getNominal() {
        return nominal;
    }

    @Override
    public void withdraw(int amount) {
        currentAmount = currentAmount - amount;
        if (currentAmount < 0) {
            throw new OutOfCashException("Закончились банкноты номиналом " + this.getNominal());
        }
        System.out.println("Снято " + amount + " номиналом " + nominal);
    }
}
