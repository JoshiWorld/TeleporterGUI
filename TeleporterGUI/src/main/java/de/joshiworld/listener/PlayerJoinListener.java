package de.joshiworld.listener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author JoshiWorld
 */
public class PlayerJoinListener implements Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        File ordner = new File("plugins/TGUI/");
        if(!ordner.exists()) {
            ordner.mkdir();
        }
        File file = new File("plugins/TGUI/config.json");
        String path = file.getPath();
        
        if(!file.exists()) {
            JSONObject json = (JSONObject) new JSONObject();
            
            json.put("tgui_item_name", "§aTGUI");
            json.put("tgui_item_t", 341);
            json.put("tgui_item_hotbar", 4);
            json.put("inventory_size", 27);
            json.put("inventory_name", "§eTeleporterGUI");
            
            json.put("inv_items", 3);
            for(int i = Integer.valueOf(json.get("inv_items").toString()); i > 0; i--) {
                json.put("inv_item_" + String.valueOf(i) + "_name", String.valueOf(i));
                json.put("inv_item_" + String.valueOf(i) + "_item", i);
                json.put("inv_item_" + String.valueOf(i) + "_slot", i);
            }
            
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PlayerJoinListener.class.getName()).log(Level.SEVERE, null, ex);
                e.getPlayer().sendMessage("§cconfig.json konnte nicht erstellt werden");
            }
            
            try(FileWriter fileW = new FileWriter(path)) {
		fileW.write(json.toJSONString());
                fileW.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        JSONParser parser = new JSONParser();
        
        try {
            JSONObject json = (JSONObject) parser.parse(new FileReader(file));
            
            ItemStack item = new ItemStack(Integer.valueOf(json.get("tgui_item_t").toString()));
            ItemMeta itemMet = item.getItemMeta();
            itemMet.setDisplayName(json.get("tgui_item_name").toString());
            item.setItemMeta(itemMet);
            
            e.getPlayer().getInventory().clear();
            e.getPlayer().getInventory().setItem(Integer.valueOf(json.get("tgui_item_hotbar").toString()), item);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(PlayerJoinListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
