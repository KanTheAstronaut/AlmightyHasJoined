package co.kanepu.almightyhasjoined;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;


public class bungeereload extends Command {

    public bungeereload(bungeemain This) {
        super("almightyreload","almighty.reload", "");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(ProxyServer.getInstance().getPluginsFolder().getAbsolutePath() + "\\AlmightyHasJoined", "config.yml"));
            sender.sendMessage(new ComponentBuilder(ChatColor.GOLD + "[AlmightyHasJoined] " + ChatColor.GREEN + "The configuration file has been reloaded!").create());
            if (sender instanceof ProxiedPlayer)
                ProxyServer.getInstance().getPluginManager().getPlugin("AlmightyHasJoined").getLogger().info(ChatColor.GREEN + "The configuration file has been reloaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


