package co.kanepu.almightyhasjoined;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class main extends JavaPlugin implements Listener {
    FileConfiguration config = this.getConfig();
    File conf;
    @Override
    public void onEnable() {
        config.addDefault("joinmsg", "'&6THE ALMIGHTY %player% JOINED!'");
        config.addDefault("quitmsg", "'&6THE ALMIGHTY %player% LEFT!'");
        config.addDefault("onlyonjoin", true);
        config.addDefault("disablejoinmsg", false);
        config.addDefault("disablequitmsg", false);
        config.addDefault("makemsgprivate", false);
        config.options().copyDefaults(true);
        saveConfig();
        conf = new File(getDataFolder(), "config.yml");
        getServer().getPluginManager().registerEvents(this, this);
        if (config.getBoolean("onlyonjoin") && config.getBoolean("onlyonquit")) {
            config.set("onlyonquit", false);
            saveConfig();
            config = YamlConfiguration.loadConfiguration(conf);
        } else if (!config.getBoolean("onlyonjoin") && !config.getBoolean("onlyonquit")) {
            config.set("onlyonjoin", true);
            saveConfig();
            config = YamlConfiguration.loadConfiguration(conf);
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        e.setJoinMessage(null);
        if (!config.getBoolean("disablejoinmsg")) {
            String joinmsg = config.getString("joinmsg");
            if (joinmsg.contains("%player%"))
                joinmsg = joinmsg.replace("%player%", e.getPlayer().getDisplayName());
            if (joinmsg.contains("%world%"))
                joinmsg = joinmsg.replace("%world%", e.getPlayer().getWorld().getName());
            if (joinmsg.contains("%time%"))
                joinmsg = joinmsg.replace("time%", Long.toString(e.getPlayer().getWorld().getTime()));
            if (e.getPlayer().hasPermission("almighty.join") && !config.getBoolean("makemsgprivate") && !config.getBoolean("onlyonquit"))
                e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinmsg));
            else if (e.getPlayer().hasPermission("almighty.join") && config.getBoolean("makemsgprivate") && !config.getBoolean("onlyonquit"))
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', joinmsg));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        e.setQuitMessage(null);
        if (!config.getBoolean("disablequitmsg")) {
            String quitmsg = config.getString("quitmsg");
            if (quitmsg.contains("%player%"))
                quitmsg = quitmsg.replace("%player%", e.getPlayer().getDisplayName());
            if (quitmsg.contains("%world%"))
                quitmsg = quitmsg.replace("%world%", e.getPlayer().getWorld().getName());
            if (quitmsg.contains("%time%"))
                quitmsg = quitmsg.replace("time%", Long.toString(e.getPlayer().getWorld().getTime()));
            if (e.getPlayer().hasPermission("almighty.quit") && !config.getBoolean("onlyonjoin") && !config.getBoolean("makemsgprivate"))
                e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', quitmsg));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("almightyreload") && sender.hasPermission("almighty.reload")) {
            sender.sendMessage(ChatColor.GREEN + "The configuration file has been reloaded!");
            config = YamlConfiguration.loadConfiguration(conf);
            if (sender instanceof ProxiedPlayer)
                ProxyServer.getInstance().getPluginManager().getPlugin("AlmightyHasJoined").getLogger().info(ChatColor.GOLD + "[AlmightyHasJoined] " + ChatColor.GREEN + "The configuration file has been reloaded!");
        }
        return false;
    }
}
