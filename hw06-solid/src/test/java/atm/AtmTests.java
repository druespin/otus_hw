package atm;

import cash.CashStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AtmTests {

    private final CashStorage cashStorage = new CashStorage();

    @Test
    @DisplayName("Тест 1. Снятие денег")
    public void withdrawTest() {

        cashStorage.addCell(100, 20);
        cashStorage.addCell(200, 10);
        cashStorage.addCell(500, 10);
        cashStorage.addCell(1000, 1);

        Atm atm = new Atm(cashStorage);
        atm.printBalance();

        System.out.println("К выдаче " + atm.withdrawFromAtm(3900));
        atm.printBalance();
    }

    @Test
    @DisplayName("Тест 2. Пополнение банкомата")
    public void rechargeTest() {

        cashStorage.addCell(100, 0);
        cashStorage.addCell(200, 0);
        cashStorage.addCell(500, 0);
        cashStorage.addCell(1000, 0);

        Atm atm = new Atm(cashStorage);
        atm.printBalance();

        atm.rechargeAtm(100, 10);
        atm.rechargeAtm(500, 20);
        atm.rechargeAtm(1000, 7);
        atm.printBalance();
    }

    @Test
    @DisplayName("Тест 3. Ошибка - недостаточно средств")
    public void outOfCashTest() {

        cashStorage.addCell(100, 2);
        cashStorage.addCell(50, 10);

        Atm atm = new Atm(cashStorage);
        atm.printBalance();

        atm.withdrawFromAtm(1000);
    }

    @Test
    @DisplayName("Тест 4. Ошибка - нет банкнот нужного номинала")
    public void noRequiredNominalTest() {

        cashStorage.addCell(100, 2);
        cashStorage.addCell(500, 10);

        Atm atm = new Atm(cashStorage);
        atm.printBalance();

        atm.withdrawFromAtm(750);
    }
}
