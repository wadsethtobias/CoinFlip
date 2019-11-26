package me.toqless.coinflip.utils;

import me.toqless.coinflip.CoinFlip;
import me.toqless.lancore.economy.API;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class AnimationManager {
    private FileConfiguration config;
    private String title;

    public AnimationManager() {
        this.config = CoinFlip.getInstance().getConfig();
        this.title = this.config.getString("AnimationGUI.Title");
    }

    public ItemStack players(final Player winner) {
        final ItemStack p1 = new ItemStack(Material.PLAYER_HEAD, 1);
        final SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
        meta.setDisplayName(Chat.color("&f&l") + winner.getName());
        meta.setOwner(winner.getName());
        p1.setItemMeta((ItemMeta)meta);
        return p1;
    }

    public void fillGlass(final int j, final Inventory animation) {
        for (int i = 0; i < 27; ++i) {
            animation.setItem(i, CoinFlip.getInstance().getMenuManager().createGlass(1, j));
        }
    }

    public void setAnimation(final Player p, final Player winner, final Player loser, final double amount) {
        final DecimalFormat df = new DecimalFormat("#,###");
        final Inventory animation = Bukkit.createInventory((InventoryHolder)null, 27, Chat.color(this.title));
        p.openInventory(animation);
        new BukkitRunnable() {
            int counter = 0;
            int counter2 = 1;
            int counter3 = 1;

            public void run() {
                AnimationManager.this.fillGlass(this.counter, animation);
                p.playSound(p.getLocation(), Sound.BLOCK_WOOD_HIT, 1.0f, 1.0f);
                if (this.counter % this.counter2 == 0) {
                    this.counter = 0;
                    ++this.counter2;
                }
                if (this.counter3 == 1) {
                    animation.setItem(13, AnimationManager.this.players(loser));
                    p.updateInventory();
                    --this.counter3;
                }
                else {
                    animation.setItem(13, AnimationManager.this.players(winner));
                    p.updateInventory();
                    ++this.counter3;
                }
                ++this.counter;
                if (this.counter2 > 6) {
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    AnimationManager.this.fillGlass(15, animation);
                    animation.setItem(13, AnimationManager.this.players(winner));
                    p.updateInventory();
                    API.addBalance(winner, (long) amount);
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        if (!CoinFlip.getInstance().getBroadcast().inEntry(p)) {
                            p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.WinBroadcast").replaceAll("%winner%", winner.getName()).replaceAll("%loser%", loser.getName()).replaceAll("%amount%", df.format(amount))));
                        }
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)CoinFlip.getInstance(), 0L, 5L);
    }
}
