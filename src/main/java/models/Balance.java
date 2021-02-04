package models;

public class Balance {

    private int amountInPence;

    public Balance(int amount) {
        this.amountInPence = amount;
    }

    public int getAmount() {
        return amountInPence;
    }

    public void setAmountInPence(int amount) {
        this.amountInPence = amount;
    }

    public boolean updateAmount(int amount) {

        if (amount + this.amountInPence < 0) return false;

        else {
            this.amountInPence += amount;
            return true;
        }
    }

    @Override
    public String toString() {
        return "amount=" + amountInPence;
    }
}
