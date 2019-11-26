package me.toqless.coinflip.utils;

import java.util.HashMap;

public class StatsEntry {
    private int winStats;
    private int loseStats;
    private HashMap<Integer, Integer> statRatio;

    public StatsEntry() {
        this.winStats = 0;
        this.loseStats = 0;
        this.statRatio = new HashMap<Integer, Integer>();
    }

    public HashMap<Integer, Integer> getStatRatio() {
        return this.statRatio;
    }

    public int getWinStats() {
        return this.winStats;
    }

    public int getLoseStats() {
        return this.loseStats;
    }

    public void incrementWin() {
        ++this.winStats;
    }

    public void incrementLose() {
        ++this.loseStats;
    }
}
