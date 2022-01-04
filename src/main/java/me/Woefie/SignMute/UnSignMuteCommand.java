package me.Woefie.SignMute;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class UnSignMuteCommand implements CommandExecutor {

    public FileConfiguration config = SignMuteMain.instance.getConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("unsignmute")) {
            if (!sender.hasPermission("woefie.signmuteplugin.unsignmute")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return false;
            }
            if (args.length == 1) {
                Player player = (Player) Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage("Player is not online");
                    return true;
                }
                if (player.isOnline()) {
                    if (alreadySignMuted(player)) {
                        unSignMutePlayer(player);
                        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UnSignMuted Player");
                        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You have been unsignmuted");
                    } else {
                        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player is not SignMuted");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player is not online!");
                }

            }else {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Incorrect usage! Correct usage /UnSignMute {player}");
            }
        }
        return false;
    }
    public boolean alreadySignMuted(Player player) {
        if (config.getInt(player.getName() + ".time") <= 0) {
            config.set(player.getName() + ".uuid", null);
            SignMuteMain.instance.saveConfig();
            return false;
        } else if (config.getString(player.getName() + ".uuid") == null) return false;
        else if (config.getString(player.getName() + ".uuid").equals(player.getUniqueId().toString())) {

            return true;
        } else {
            return false;
        }
    }

    public void unSignMutePlayer(Player player) {
        if (alreadySignMuted(player)) {
            config.set(player.getName()+ ".time",  0);
            config.set(player.getName() + ".uuid", null);
            SignMuteMain.instance.saveConfig();
        } else {
            return;
        }

    }
}
