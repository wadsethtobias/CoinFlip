package me.toqless.coinflip.utils;

import org.bukkit.ChatColor;

public class Chat {
    public static String color(final String args) {
        return ChatColor.translateAlternateColorCodes('&', args);
    }
}
