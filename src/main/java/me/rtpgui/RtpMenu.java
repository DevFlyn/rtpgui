package me.rtpgui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class RtpMenu {

    public static String TITLE;

    public static final List<String> WORLDS = new ArrayList<>();

    private final JavaPlugin plugin;

    public RtpMenu(JavaPlugin plugin) {
        this.plugin = plugin;
        loadWorlds();
    }

    private void loadWorlds() {

        WORLDS.clear();

        ConfigurationSection worlds =
                plugin.getConfig().getConfigurationSection("customworlds");

        if (worlds == null) {
            return;
        }

        for (String key : worlds.getKeys(false)) {

            ConfigurationSection sec =
                    worlds.getConfigurationSection(key);

            if (sec == null) continue;
            if (!sec.getBoolean("enabled")) continue;

            WORLDS.add(key.toLowerCase());
        }
    }

    public void open(Player player) {

        var config = plugin.getConfig();

        ConfigurationSection gui =
                config.getConfigurationSection("gui");

        int size = gui.getInt("size");
        TITLE = ColorUtil.color(gui.getString("title"));

        Inventory inv =
                Bukkit.createInventory(null, size, TITLE);

        ConfigurationSection worlds =
                config.getConfigurationSection("customworlds");

        for (String key : worlds.getKeys(false)) {

            ConfigurationSection sec =
                    worlds.getConfigurationSection(key);

            if (!sec.getBoolean("enabled")) continue;

            Material material =
                    Material.valueOf(sec.getString("item-material"));

            int slot = sec.getInt("slot");

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(
                    ColorUtil.color(
                            parse(sec.getString("item-display-name"), player)
                    )
            );

            List<String> lore = new ArrayList<>();

            for (String line : sec.getStringList("item-lore")) {
                lore.add(ColorUtil.color(parse(line, player)));
            }

            meta.setLore(lore);
            item.setItemMeta(meta);

            inv.setItem(slot, item);
        }

        player.openInventory(inv);
    }

    private String parse(String text, Player player) {

        if (text == null) return "";

        text = text.replace("%player%", player.getName());

        while (text.contains("%world_players_")) {

            int start = text.indexOf("%world_players_") + 15;
            int end = text.indexOf("%", start);

            if (end == -1) break;

            String worldName = text.substring(start, end);
            World world = Bukkit.getWorld(worldName);

            int count = world == null ? 0 : world.getPlayers().size();

            text = text.replace(
                    "%world_players_" + worldName + "%",
                    String.valueOf(count)
            );
        }

        return text;
    }
}