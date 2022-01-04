package me.Woefie.SignMute;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SignMuteMain extends JavaPlugin {

    public static SignMuteMain instance;
    public FileConfiguration config = this.getConfig();
    public Plugin plugin = this;
    public ConsoleCommandSender cs = this.getServer().getConsoleSender();
    public void onEnable() {

        this.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "signmute: time handler activated");
        runnable();
        instance = this;
        this.getServer().getPluginManager().registerEvents(new SignMuteListener(), this);
        this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Signmute loaded succesfully");
        getCommand("signmute").setExecutor(new SignMuteCommand());
        getCommand("unsignmute").setExecutor(new UnSignMuteCommand());

        this.saveDefaultConfig();
    }


    public void onDisable() {

    }

    public void runnable() {


        new BukkitRunnable() {

            @Override
            public void run() {
                cs.sendMessage(ChatColor.YELLOW + "signmute: time handler activated");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    int i = config.getInt(p.getName() + ".time");
                    cs.sendMessage("check" + i + p.getName());
                    if (config.getString(p.getName() + ".uuid") == null) {
                    }else if (i < 0) {
                        config.set(p.getName() + ".uuid", null);
                        plugin.saveConfig();
                    } else if (i == 0) {
                        config.set(p.getName() + ".uuid", null);
                        plugin.saveConfig();
                    }   else {
                        int newtime = i - 10;
                        config.set(p.getName() + ".time", newtime );
                        plugin.saveConfig();
                    }
                    plugin.saveConfig();
                }
                plugin.saveConfig();
                cs.sendMessage(ChatColor.YELLOW + "signmute: time handler succesful");
                return;
            }
        }.runTaskTimer(plugin, 0, 20 * 600);
    }
}

