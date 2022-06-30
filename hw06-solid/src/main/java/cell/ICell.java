package cell;

public interface ICell {

    int getNominal();

    int getCurrentAmount();

    void withdraw(int noteAmount);

    void recharge(int noteAmount);
}
