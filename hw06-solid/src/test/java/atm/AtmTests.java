package atm;

import org.testng.annotations.Test;

public class AtmTests {

    @Test
    public void withdrawTest() {
        Atm atm = new Atm(10, 10, 4, 5);
        atm.withdrawCash(4900);
    }

    @Test
    public void rechargeTest() {
        Atm atm = new Atm(0, 10, 0, 0);
        atm.rechargeCell(atm.getCellByNominal(100), 10);
        atm.rechargeCell(atm.getCellByNominal(500), 20);
        atm.rechargeCell(atm.getCellByNominal(1000), 7);
    }

    @Test
    public void outOfCashTest() {
        Atm atm = new Atm(2, 0, 0, 1);
        atm.withdrawCash(1200);
    }
}
