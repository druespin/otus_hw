package cell;

public interface ICell {

    int getNominal();

    int getCurrentAmount();

    void withdraw(int amount);

    void recharge(int amount);
}
