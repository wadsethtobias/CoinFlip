package me.toqless.coinflip.commands;

import me.toqless.coinflip.CoinFlip;
import me.toqless.coinflip.utils.Chat;
import me.toqless.coinflip.utils.CoinManager;
import me.toqless.coinflip.utils.InventoryManager;
import me.toqless.lancore.economy.API;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class CoinFlipCommand implements CommandExecutor {
    private CoinManager coins;
    private InventoryManager menu;

    public CoinFlipCommand() {
        this.coins = CoinFlip.getInstance().getCoins();
        this.menu = CoinFlip.getInstance().getMenuManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("coinflip")) {
            final Player p = (Player)sender;
            final DecimalFormat df = new DecimalFormat("#,###");
            if (args.length == 0) {
                p.openInventory(CoinFlip.getInstance().getMenu());
                return false;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("toggle")) {
                    if (!CoinFlip.getInstance().getBroadcast().inEntry(p)) {
                        CoinFlip.getInstance().getBroadcast().createEntry(p);
                        p.sendMessage(Chat.color(CoinFlip.getInstance().getBroadcast().toString(p)));
                        return false;
                    }
                    CoinFlip.getInstance().getBroadcast().removeEntry(p);
                    p.sendMessage(Chat.color(CoinFlip.getInstance().getBroadcast().toString(p)));
                    return false;
                }
                else {
                    if (!args[0].equalsIgnoreCase("cancel")) {
                        p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.CanceledHelp")));
                        return false;
                    }
                    if (this.coins.inEntry(p)) {
                        final double amount = this.coins.getEntry().get(p).getAmount();
                        API.addBalance(p, (long) amount);
                        this.coins.removeEntry(p);
                        this.menu.updateInv();
                        p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.ReceivedMoney").replaceAll("%amount%", df.format(amount))));
                        p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.Canceled")));
                        return false;
                    }
                    p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.NotInBet")));
                }
            }
            if (args.length == 2) {
                try {
                    final double amount = Double.parseDouble(args[0]);
                    final boolean side = this.coins.getBooleanConverted(args[1]);
                    if (this.coins.getEntry().size() < this.menu.getSize()) {
                        if (!this.coins.inEntry(p)) {
                            if (API.getBalance(p) >= amount) {
                                if (amount >= CoinFlip.getInstance().getConfig().getInt("minAmount")) {
                                    API.takeBalance(p, (long) amount);
                                    p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.LostMoney").replaceAll("%amount%", df.format(amount))));
                                    p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.Entered").replaceAll("%amount%", df.format(amount))));
                                    if (!CoinFlip.getInstance().getStats().inEntry(p)) {
                                        CoinFlip.getInstance().getStats().createEntry(p);
                                    }
                                    this.coins.createEntry(p, amount, side);
                                    this.menu.updateInv();
                                    return false;
                                }
                                p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.NotEnoughEnterMoney")));
                            }
                            else {
                                p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.NotEnoughMoney")));
                            }
                        }
                        else {
                            p.sendMessage(Chat.color(CoinFlip.getInstance().getConfig().getString("Messages.AlreadyInBet")));
                        }
                    }
                }
                catch (NumberFormatException e) {
                    p.sendMessage(Chat.color("&e8[&e&l!&8] &cAnvändning: /coinflip [mängd] [heads/tails]"));
                }
            }
        }
        return false;
    }
}
