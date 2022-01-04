package me.Woefie.SignMute;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SignMuteListener implements Listener {

    public FileConfiguration config = SignMuteMain.instance.getConfig();

    @EventHandler
    public void onBlockPlace( BlockPlaceEvent event) {

        Player player = event.getPlayer();
        if (event.getBlock().getState() instanceof Sign) {
            if (alreadySignMuted(player) == true) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are signmuted");
                event.setCancelled(true);
            }
        }
    }

    public boolean alreadySignMuted(Player player) {
        if (config.getInt(player.getName() + ".time") <= 0) {
            config.set(player.getName() + ".uuid", null);
            SignMuteMain.instance.saveConfig();
            return false;
        } else if (config.getString(player.getName() + ".uuid") == null) return false;
        else if (config.getString(player.getName() + ".uuid").equals(player.getUniqueId().toString())) {
            return true;
        }
        else return false;
    }

}
