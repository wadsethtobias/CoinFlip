package me.toqless.coinflip.events;

import me.toqless.coinflip.CoinFlip;
import me.toqless.lancore.economy.API;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {
    @EventHandler
    public void onQuitEvent(final org.bukkit.event.player.PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (CoinFlip.getInstance().getCoins().inEntry(p)) {
            API.addBalance(p, (long) CoinFlip.getInstance().getCoins().getEntry().get(p).getAmount());
            CoinFlip.getInstance().getCoins().removeEntry(p);
            CoinFlip.getInstance().getMenuManager().updateInv();
        }
    }
}
