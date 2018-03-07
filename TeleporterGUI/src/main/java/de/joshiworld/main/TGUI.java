package de.joshiworld.main;

import de.joshiworld.commands.TGUI_Command;
import de.joshiworld.listener.InventoryClickListener;
import de.joshiworld.listener.PlayerInteractListener;
import de.joshiworld.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author JoshiWorld
 */
public class TGUI extends JavaPlugin {
    
    public static TGUI instance;
    
    @Override
    public void onEnable() {
        
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        
        this.getCommand("tgui").setExecutor(new TGUI_Command());
    }
    
    @Override
    public void onDisable() {
        
    }
}
