package cash;

import cell.Cell;
import cell.ICell;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CashStorage {

    private final Map<Integer, Cell> cellMap = new HashMap<>();

    public Set<Integer> getCellNominals() {
        return Set.copyOf(cellMap.keySet());
    }

    // добавление ячейки
    public void addCell(int nominal, int noteAmount) {
        cellMap.put(nominal, new Cell(nominal, noteAmount));
    }

    // пополнение ячейки, noteAmount - кол-во банконот
    public void rechargeCell(ICell cell, int noteAmount) {
        cell.recharge(noteAmount);
    }

    // снятие денег из ячейки, noteAmount - кол-во банконот
    public void withdrawFromCell(ICell cell, int noteAmount) {
        cell.withdraw(noteAmount);
    }

    public Cell getCellByNominal(int nominal) {
        return cellMap.get(nominal);
    }

    public int getAtmBalance() {
        int sum = 0;
        for (Cell cell: cellMap.values()) {
            sum += cell.getNominal() * cell.getCurrentAmount();
        }
        return sum;
    }

    public void printBalance() {
        System.out.println("\nБаланс банкомата: ");
        for (Cell cell: cellMap.values()) {
            System.out.println(cell.getNominal() + " - " + cell.getCurrentAmount());
        }
        System.out.println("Общая сумма: " + getAtmBalance() + "\n");
    }
}
