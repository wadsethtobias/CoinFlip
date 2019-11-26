package me.toqless.coinflip.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class StatsManager {
    private HashMap<Player, StatsEntry> stats;
    private StatsEntry entry;

    public StatsManager() {
        this.stats = new HashMap<Player, StatsEntry>();
    }

    public boolean inEntry(final Player p) {
        return this.stats.containsKey(p);
    }

    public void createEntry(final Player p) {
        this.entry = this.stats.getOrDefault(p, new StatsEntry());
        this.stats.put(p, this.entry);
    }

    public int getWinStats(final Player p) {
        return this.stats.get(p).getWinStats();
    }

    public int getLoseStats(final Player p) {
        return this.stats.get(p).getLoseStats();
    }

    public void incrementWin(final Player p) {
        this.entry.incrementWin();
        this.stats.put(p, this.entry);
    }

    public void incrementLose(final Player p) {
        this.entry.incrementLose();
        this.stats.put(p, this.entry);
    }

    public Player getWinner(final Player first, final Player second) {
        if (Math.random() > 0.5) {
            this.incrementWin(first);
            return first;
        }
        return second;
    }

    public void toString(final Player p) {
        final ArrayList<String> string = new ArrayList<String>();
        p.sendMessage(Chat.color("&e&l&n" + p.getName()) + " &e&l&nStats");
        p.sendMessage(Chat.color(""));
        p.sendMessage(Chat.color("&6&l* &eDagliga vinster: &f" + this.getWinStats(p)));
        p.sendMessage(Chat.color("&6&l* &eDagliga förluster: &f" + this.getLoseStats(p)));
        p.sendMessage(Chat.color(""));
        p.sendMessage(Chat.color("&7&o(( Stats återställs dagligen ))"));
    }
}
