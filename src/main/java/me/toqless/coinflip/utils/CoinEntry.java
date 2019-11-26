package me.toqless.coinflip.utils;

public class CoinEntry {
    private double amount;
    private boolean side;

    public CoinEntry(final double amount, final boolean side) {
        this.amount = amount;
        this.side = side;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean getSide() {
        return this.side;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public void setSide(final boolean side) {
        this.side = side;
    }
}
