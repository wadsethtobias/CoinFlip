package me.toqless.coinflip.events;

import me.toqless.coinflip.CoinFlip;
import me.toqless.coinflip.utils.Chat;
import me.toqless.coinflip.utils.CoinManager;
import me.toqless.coinflip.utils.InventoryManager;
import me.toqless.lancore.economy.API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {
    @EventHandler
    public void onClickEvent(final InventoryClickEvent e) {
        final Player p = (Player)e.getWhoClicked();
        final InventoryView open = e.getView();
        final ItemStack item = e.getCurrentItem();
        final CoinManager coins = CoinFlip.getInstance().getCoins();
        final InventoryManager menu = CoinFlip.getInstance().getMenuManager();
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        if (open.getTitle().equals(Chat.color(CoinFlip.getInstance().getMenuManager().getTitle())) || open.getTitle().equals(Chat.color(CoinFlip.getInstance().getConfig().getString("AnimationGUI.Title")))) {
            e.setCancelled(true);
            if (item.equals((Object)menu.playerRefresh())) {
                p.closeInventory();
                p.openInventory(menu.getMenu());
                return;
            }
            final Player other = Bukkit.getServer().getPlayer(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
            if (item.equals((Object)menu.playerHelp())) {
                return;
            }
            if (coins.getEntry().containsKey(p)) {
                return;
            }
            if (item.getItemMeta().getDisplayName().equals(Chat.color("&0."))) {
                return;
            }
            if (item.getType().equals((Object) Material.PLAYER_HEAD)) {
                if (coins.inEntry(other)) {
                    double amount = coins.getEntry().get(other).getAmount();
                    if (API.getBalance(p) > amount) {
                        API.takeBalance(p, (long) amount);
                        p.closeInventory();
                        coins.removeEntry(other);
                        final Player winner = CoinFlip.getInstance().getStats().getWinner(p, other);
                        amount *= 2.0;
                        if (p.equals(winner)) {
                            CoinFlip.getInstance().getAnimation().setAnimation(p, p, other, amount);
                        }
                        else {
                            CoinFlip.getInstance().getAnimation().setAnimation(p, other, p, amount);
                        }
                        menu.updateInv();
                    }
                    else {
                        p.closeInventory();
                        p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.NotEnoughMoney")));
                    }
                }
            }
            else if (item.getItemMeta().getDisplayName().equals(Chat.color("&f&l" + other.getName()))) {
                return;
            }
        }
    }
}
