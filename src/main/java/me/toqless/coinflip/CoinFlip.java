package me.toqless.coinflip;

import me.toqless.coinflip.commands.CoinFlipCommand;
import me.toqless.coinflip.events.ClickEvent;
import me.toqless.coinflip.events.PlayerQuitEvent;
import me.toqless.coinflip.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoinFlip extends JavaPlugin {
    private CoinManager coins;
    private StatsManager stats;
    private InventoryManager menu;
    private AnimationManager animation;
    private BroadcastManager broadcast;

    public void onEnable() {
        this.saveDefaultConfig();
        if (Bukkit.getServer().getPluginManager().getPlugin("LanCore") == null) {
            System.out.println(String.format("[CoinFlip] - Disabled due to no Economy dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        this.broadcast = new BroadcastManager();
        this.stats = new StatsManager();
        this.coins = new CoinManager();
        this.menu = new InventoryManager();
        this.animation = new AnimationManager();
        this.getCommand("coinflip").setExecutor((CommandExecutor)new CoinFlipCommand());
        this.getServer().getPluginManager().registerEvents((Listener)new ClickEvent(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerQuitEvent(), (Plugin)this);
    }

    public void onDisable() {
    }

    public static CoinFlip getInstance() {
        return (CoinFlip)JavaPlugin.getPlugin((Class<CoinFlip>)CoinFlip.class);
    }

    public BroadcastManager getBroadcast() {
        return this.broadcast;
    }

    public StatsManager getStats() {
        return this.stats;
    }

    public CoinManager getCoins() {
        return this.coins;
    }

    public Inventory getMenu() {
        return this.menu.getMenu();
    }

    public AnimationManager getAnimation() {
        return this.animation;
    }

    public InventoryManager getMenuManager() {
        return this.menu;
    }
}
