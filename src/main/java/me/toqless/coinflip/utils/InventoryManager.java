package me.toqless.coinflip.utils;

import me.toqless.coinflip.CoinFlip;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryManager {
    private FileConfiguration config;
    private int size;
    private String title;
    private Material material;
    private Inventory menu;
    private HashMap<Player, CoinEntry> coins;

    public InventoryManager() {
        this.config = CoinFlip.getInstance().getConfig();
        this.size = this.config.getInt("GUI.Size");
        this.title = this.config.getString("GUI.Title");
        this.coins = CoinFlip.getInstance().getCoins().getEntry();
        this.menu = Bukkit.createInventory((InventoryHolder)null, this.size, Chat.color(this.title));
        for (int i = 0; i < this.size; ++i) {
            this.menu.setItem(i, this.createGlass(1, 15));
            this.menu.setItem(this.size - 2, this.playerHelp());
            this.menu.setItem(this.size - 1, this.playerRefresh());
        }
    }

    public String getTitle() {
        return this.title;
    }

    public Inventory getMenu() {
        return this.menu;
    }

    public int getSize() {
        return this.size;
    }

    public void updateInv() {
        int index = 0;
        this.menu = Bukkit.createInventory((InventoryHolder)null, this.size, Chat.color(this.title));
        for (int i = 0; i < this.size - 2; ++i) {
            this.menu.setItem(i, this.createGlass(1, 15));
        }
        for (final Player p : this.coins.keySet()) {
            this.menu.setItem(index, this.playerBet(p));
            ++index;
        }
        this.menu.setItem(this.size - 2, this.playerHelp());
        this.menu.setItem(this.size - 1, this.playerRefresh());
    }

    public ItemStack playerBet(final Player p) {
        final DecimalFormat df = new DecimalFormat("#,###");
        final ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        final SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
        final List<String> desc = (List<String>)this.config.getStringList("GUI.SkullItem.lore");
        final List<String> lore = new ArrayList<String>();
        for (final String text : desc) {
            lore.add(Chat.color(text.replaceAll("%money%", df.format(this.coins.get(p).getAmount())).replaceAll("%side%", CoinFlip.getInstance().getCoins().getSideConverted(p))));
        }
        meta.setDisplayName(Chat.color(this.config.getString("GUI.SkullItem.name").replaceAll("%name%", p.getName())));
        meta.setOwner(p.getName());
        meta.setLore((List)lore);
        item.setItemMeta((ItemMeta)meta);
        return item;
    }

    public ItemStack playerHelp() {
        final String type = this.config.getString("GUI.BookInfo.type");
        this.material = Material.getMaterial(type);
        final ItemStack item = new ItemStack(this.material, 1);
        final ItemMeta meta = item.getItemMeta();
        final List<String> desc = (List<String>)this.config.getStringList("GUI.BookInfo.lore");
        final List<String> lore = new ArrayList<String>();
        for (final String text : desc) {
            lore.add(Chat.color(text));
        }
        meta.setDisplayName(Chat.color(this.config.getString("GUI.BookInfo.name")));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack playerRefresh() {
        final String type = this.config.getString("GUI.RefreshItem.type");
        this.material = Material.getMaterial(type);
        final ItemStack item = new ItemStack(this.material, 1);
        final ItemMeta meta = item.getItemMeta();
        final List<String> desc = (List<String>)this.config.getStringList("GUI.RefreshItem.lore");
        final List<String> lore = new ArrayList<String>();
        for (final String text : desc) {
            lore.add(Chat.color(text));
        }
        meta.setDisplayName(Chat.color(this.config.getString("GUI.RefreshItem.name")));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createGlass(final int amount, final int data) {
        final ItemStack glass = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE, amount, (short)(byte)data);
        final ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(Chat.color("&0."));
        glass.setItemMeta(meta);
        return glass;
    }
}
