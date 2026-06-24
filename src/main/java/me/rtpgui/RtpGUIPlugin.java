package me.rtpgui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RtpGUIPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager()
                .registerEvents(new GuiListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (!(sender instanceof Player player)) {
            return true;
        }

        if (!command.getName().equalsIgnoreCase("rtp")) {
            return true;
        }


        if (args.length == 0) {
            new RtpMenu(this).open(player);
            return true;
        }

        String search = args[0].toLowerCase();

        var worlds = getConfig().getConfigurationSection("customworlds");

        if (worlds == null) {
            return true;
        }

        for (String key : worlds.getKeys(false)) {

            var section = worlds.getConfigurationSection(key);

            if (section == null) {
                continue;
            }

            if (!section.getBoolean("enabled")) {
                continue;
            }

            String worldName = section.getString("world-name", "");

            String simpleName = worldName
                    .replace("world_minecraft_", "")
                    .toLowerCase();

            if (!simpleName.equals(search)) {
                continue;
            }

            String cmd = section.getString("command", "")
                    .replace("%player%", player.getName());

            getServer().dispatchCommand(
                    getServer().getConsoleSender(),
                    cmd
            );

            return true;
        }

        player.sendMessage("§cUnknown RTP world.");
        return true;
    }
}