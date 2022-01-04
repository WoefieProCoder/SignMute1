
package me.Woefie.SignMute;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SignMuteCommand implements CommandExecutor {

public FileConfiguration config = SignMuteMain.instance.getConfig();

public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (commandLabel.equalsIgnoreCase("signmute")) {
        if (!sender.hasPermission("woefie.signmuteplugin.signmute")) {
        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return false;
        }
        if (args.length != 3) {
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Incorrect usage. Use /signmute <player> <time> <multiplier>");
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Multipliers are m/h/d/w/mo/y/forever");
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "For instance /signmute Woefiepower 10 m");
        return false;
        }
        if (args.length == 3) {
        Player player = Bukkit.getPlayer(args[0]);
        int time = Integer.parseInt(args[1]);
        String multiplier = args[2];

        if (player == null) {
        sender.sendMessage("Player is not online");
        return true;
        }
        if (!player.isOnline()) {
        sender.sendMessage("Player is not online");
        return true;
        }
        if (alreadySignMuted(player)) {
        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player is already SignMuted");
        return true;
        } else {
        signMutePlayer( player, time, multiplier, sender);
        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Player has been signmuted");
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have been signmuted");
        return true;
        }
        }
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Incorrect usage. Use /signmute <player> <time> <multiplier>");
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Multipliers are m/h/d/w/mo/y/forever");
        return true;
        }
        return false;
        }

public void signMutePlayer(Player player, int time, String multiplier, CommandSender sender) {
        if (alreadySignMuted(player)) {
        return;
        } else {
        timeify (time, multiplier);
        if (timeify(time, multiplier) < 0) {
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Incorrect usage. Use /signmute <player> <time> <multiplier>");
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Multipliers are m/h/d/w/mo/y/forever");
        } else if (timeify(time, multiplier) > 99 * 1000000000 * 100000000) {
        sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You signmuted: " + player.getName() + " forever!" );
        } else {
        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You signmuted: " + player.getName() + " for:" + time + "" + multiplier + ".");
        config.set(player.getName() + ".uuid", player.getUniqueId().toString());
        config.set(player.getName() + ".time", timeify(time, multiplier));
        SignMuteMain.instance.saveConfig();
        }

        }
        }

public int timeify(int time, String multiplier) {
        if (multiplier.equalsIgnoreCase("m")) {
        return time * 1;
        } else if (multiplier.equalsIgnoreCase("h")) {
        return time * 60;
        } else if (multiplier.equalsIgnoreCase("d")) {
        return time * 1440;
        } else if (multiplier.equalsIgnoreCase("w")) {
        return time * 10080;
        } else if (multiplier.equalsIgnoreCase("mo")) {
        return time * 43829;
        } else if (multiplier.equalsIgnoreCase("y")) {
        return time * 525948;
        } else if (multiplier.equalsIgnoreCase("forever")) {
        return time * 100000000;
        } else return -1;

        }

public boolean alreadySignMuted(Player player) {
        if (config.getInt(player.getName() + ".time") <= 0) {
        config.set(player.getName() + ".uuid", null);
        SignMuteMain.instance.saveConfig();
        return false;
        }else if (config.getString(player.getName() + ".uuid") == null) return false;
        else if (config.getString(player.getName() + ".uuid").equals(player.getUniqueId().toString())) {

        return true;
        } else {
        return false;
        }
        }

