package me.toqless.coinflip.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CoinManager {
    private HashMap<Player, CoinEntry> coins;

    public CoinManager() {
        this.coins = new HashMap<Player, CoinEntry>();
    }

    public HashMap<Player, CoinEntry> getEntry() {
        return this.coins;
    }

    public void createEntry(final Player p, final double amount, final boolean side) {
        final CoinEntry entry = new CoinEntry(amount, side);
        this.coins.put(p, entry);
    }

    public void removeEntry(final Player p) {
        this.coins.remove(p);
    }

    public boolean inEntry(final Player p) {
        return this.coins.containsKey(p);
    }

    public String getSideConverted(final Player p) {
        if (this.coins.get(p).getSide()) {
            return "Heads";
        }
        return "Tails";
    }

    public boolean getBooleanConverted(final String side) {
        return side.equalsIgnoreCase("heads") || side.equalsIgnoreCase("head");
    }
}
