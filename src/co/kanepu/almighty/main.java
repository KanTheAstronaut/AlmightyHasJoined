package co.kanepu.almighty;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class main extends JavaPlugin implements Listener{

	FileConfiguration config = this.getConfig();
	File conf;
	@Override
	public void onEnable() {
        	config.addDefault("joinmsg", "'&6THE ALMIGHTY %player% JOINED!'");
        	config.addDefault("quitmsg", "'&6THE ALMIGHTY %player% LEFT!'");
        	config.addDefault("onlyonjoin", true);
        	config.options().copyDefaults(true);
        	saveConfig();
		conf = new File(getDataFolder(), "config.yml");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
	}
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {  	 
		String joinmsg = config.getString("joinmsg");
		if (joinmsg.contains("%player%"))
			joinmsg = joinmsg.replace("%player%", e.getPlayer().getDisplayName());
		if (joinmsg.contains("%world%"))
			joinmsg = joinmsg.replace("%world%", e.getPlayer().getWorld().getName());
		if (joinmsg.contains("%time%"))
			joinmsg = joinmsg.replace("time%", Long.toString(e.getPlayer().getWorld().getTime()));
        if (e.getPlayer().hasPermission("almighty.join")) {
       	  e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinmsg));
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {  	 
		String quitmsg = config.getString("quitmsg");
		if (quitmsg.contains("%player%"))
			quitmsg = quitmsg.replace("%player%", e.getPlayer().getDisplayName());
		if (quitmsg.contains("%world%"))
			quitmsg = quitmsg.replace("%world%", e.getPlayer().getWorld().getName());
		if (quitmsg.contains("%time%"))
			quitmsg = quitmsg.replace("time%", Long.toString(e.getPlayer().getWorld().getTime()));
        if (e.getPlayer().hasPermission("almighty.quit") && !config.getBoolean("onlyonjoin")) {
       	  e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', quitmsg));
        }
    }
    
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("almightyreload") && sender.hasPermission("almighty.reload")) {
			sender.sendMessage(ChatColor.GREEN + "The configuration file has been reloaded!");
			config = YamlConfiguration.loadConfiguration(conf);
			ConsoleCommandSender console = getServer().getConsoleSender();
			console.sendMessage(ChatColor.GOLD + "[AlmightyHasJoined] " + ChatColor.GREEN + "The configuration file has been reloaded!");
		}
		return false;
	}
}
