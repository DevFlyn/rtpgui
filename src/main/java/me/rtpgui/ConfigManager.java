package me.rtpgui.config;

import me.rtpgui.RtpGUIPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Set;

public class ConfigManager {

    private final RtpGUIPlugin plugin;

    public ConfigManager(RtpGUIPlugin plugin) {
        this.plugin = plugin;
    }

    public int getSize() {
        return plugin.getConfig().getInt("gui.size");
    }

    public String getTitle() {
        return plugin.getConfig().getString("gui.title");
    }

    public boolean fillBackground() {
        return plugin.getConfig().getBoolean("gui.fill-background");
    }

    public String getBackgroundMaterial() {
        return plugin.getConfig().getString("gui.background-material");
    }

    public ConfigurationSection getCustomWorlds() {
        return plugin.getConfig().getConfigurationSection("customworlds");
    }

    public Set<String> getWorldKeys() {
        return getCustomWorlds().getKeys(false);
    }
}