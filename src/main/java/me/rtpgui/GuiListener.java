package me.rtpgui;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiListener implements Listener {

    private final JavaPlugin plugin;

    public GuiListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals(RtpMenu.TITLE))
            return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player player)) return;
        if (e.getCurrentItem() == null) return;

        int slot = e.getSlot();

        ConfigurationSection worlds =
                plugin.getConfig()
                        .getConfigurationSection("customworlds");

        for (String key : worlds.getKeys(false)) {

            ConfigurationSection sec =
                    worlds.getConfigurationSection(key);

            if (!sec.getBoolean("enabled")) continue;
            if (sec.getInt("slot") != slot) continue;

            String command = sec.getString("command")
                    .replace("%player%", player.getName());

            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    command
            );

            playSound(player);
            player.closeInventory();
            return;
        }
    }

    private void playSound(Player player) {

        try {
            String soundName =
                    plugin.getConfig().getString("click-sound");

            Sound sound = Sound.valueOf(soundName);
            player.playSound(player.getLocation(), sound, 1f, 1f);

        } catch (Exception ignored) {}
    }
}