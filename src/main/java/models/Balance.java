package models;

public class Balance {

    private int amountInPence;

    public Balance(int amountInPence) {
        this.amountInPence = amountInPence;
    }

    public int getAmountInPence() {
        return amountInPence;
    }

    public void setAmountInPence(int amountInPence) {
        this.amountInPence = amountInPence;
    }

    public boolean updateAmountInPence(int amountInPence) {

        if (amountInPence + this.amountInPence < 0) return false;

        else {
            this.amountInPence += amountInPence;
            return true;
        }
    }

    @Override
    public String toString() {
        return "amount=" + amountInPence;
    }
}
