package co.kanepu.almightyhasjoined;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class bungeemain extends Plugin implements Listener {
    ConfigurationProvider configs = ConfigurationProvider.getProvider(YamlConfiguration.class);
    Configuration config;
    bungeereload re = new bungeereload(this);
    @Override
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
        ProxyServer.getInstance().getPluginManager().registerCommand(this, re);
        getLogger().info("Plugin made by KanTheAstronaut/Kanepu (v2)");
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config = configs.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getPluginManager().unregisterListener(this);
        ProxyServer.getInstance().getPluginManager().unregisterCommand(re);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        if (!config.getBoolean("disablejoinmsg")) {
            String joinmsg = config.getString("joinmsg");
            if (joinmsg.contains("%player%"))
                joinmsg = joinmsg.replace("%player%", e.getPlayer().getName());
            if (e.getPlayer().hasPermission("almighty.join") && !config.getBoolean("makemsgprivate") && !config.getBoolean("onlyonquit")) {
                String finalJoinmsg = joinmsg;
                ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
                    public void run() {
                        for (ProxiedPlayer x : e.getPlayer().getServer().getInfo().getPlayers())
                            x.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', finalJoinmsg)).create());
                    }
                }, 1, TimeUnit.SECONDS);
            } else if (e.getPlayer().hasPermission("almighty.join") && config.getBoolean("makemsgprivate") && !config.getBoolean("onlyonquit"))
                e.getPlayer().sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', joinmsg)).create());
        }
    }
    
    @EventHandler
    public void onServerDisconnect(ServerDisconnectEvent e) {
        if (!config.getBoolean("disablequitmsg")) {
            String quitmsg = config.getString("quitmsg");
            if (quitmsg.contains("%player%"))
                quitmsg = quitmsg.replace("%player%", e.getPlayer().getName());
            if (e.getPlayer().hasPermission("almighty.quit") && !config.getBoolean("makemsgprivate")) {
                String finalQuitmsg = quitmsg;
                ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
                    public void run() {
                        for (ProxiedPlayer x : e.getPlayer().getServer().getInfo().getPlayers())
                            x.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', finalQuitmsg)).create());
                    }
                }, 1, TimeUnit.SECONDS);
            }
        }
    }
}
