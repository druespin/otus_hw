package atm;

import cash.CashStorage;
import exception.OutOfCashException;
import nominal.NominalSet;


public class Atm {

    private final CashStorage cashStorage;
    private final NominalSet nominals;

    public Atm(CashStorage cashStorage) {
        this.cashStorage = cashStorage;
        this.nominals = new NominalSet(cashStorage.getCellNominals());
    }

    public void rechargeAtm(int nominal, int amount) {
        cashStorage.rechargeCell(cashStorage.getCellByNominal(nominal), amount);
    }

    public int withdrawFromAtm(int cashSum) {
        int leftSum = cashSum;
        if (leftSum > cashStorage.getAtmBalance()) {
            throw new OutOfCashException("В банкомате недостаточно средств");
        }

        int maxNominal = nominals.getMaxNominal();
        var maxCell = cashStorage.getCellByNominal(maxNominal);

        int takenAmount;
        if (maxCell.getCurrentAmount() >= leftSum / maxNominal) {
            takenAmount = leftSum / maxNominal;
            cashStorage.withdrawFromCell(maxCell, takenAmount);
        } else {
            takenAmount = maxCell.getCurrentAmount();
            cashStorage.withdrawFromCell(maxCell, takenAmount);
        }
        nominals.removeNominal(maxNominal);

        int remainder = leftSum - takenAmount * maxNominal;
        if (remainder != 0) {
            if (nominals.getSize() == 0) {
                throw new OutOfCashException("В банкомате нет банкнот номиналом " + remainder);
            } else {
                withdrawFromAtm(remainder);
            }
        }
        return cashSum;
    }

    public void printBalance() {
        cashStorage.printBalance();
    }
}
