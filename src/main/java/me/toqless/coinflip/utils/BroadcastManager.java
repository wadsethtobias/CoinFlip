package me.toqless.coinflip.utils;

import me.toqless.coinflip.CoinFlip;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BroadcastManager {
    private HashMap<Player, Boolean> broadcast;

    public BroadcastManager() {
        this.broadcast = new HashMap<Player, Boolean>();
    }

    public boolean inEntry(final Player p) {
        return this.broadcast.containsKey(p);
    }

    public void createEntry(final Player p) {
        this.broadcast.put(p, true);
    }

    public void removeEntry(final Player p) {
        this.broadcast.remove(p);
    }

    public String toString(final Player p) {
        if (this.inEntry(p)) {
            return CoinFlip.getInstance().getConfig().getString("Messages.ToggleON");
        }
        return CoinFlip.getInstance().getConfig().getString("Messages.ToggleOFF");
    }
}
