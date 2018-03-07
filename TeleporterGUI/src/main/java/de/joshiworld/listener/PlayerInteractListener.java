package de.joshiworld.listener;

import de.joshiworld.commands.TGUI_Command;
import static de.joshiworld.commands.TGUI_Command.items;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author JoshiWorld
 */
public class PlayerInteractListener implements Listener {
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        
        File file = new File("plugins/TGUI/config.json");
        JSONParser parser = new JSONParser();
        
        try {
            JSONObject json = (JSONObject) parser.parse(new FileReader(file));
            
            if((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (e.getItem().getTypeId() == Integer.valueOf(json.get("tgui_item_t").toString()))) {
                Inventory inv = Bukkit.createInventory(null, Integer.valueOf(json.get("inventory_size").toString()), json.get("inventory_name").toString());
                TGUI_Command.items.clear();
                
                for(int i = Integer.valueOf(json.get("inv_items").toString()); i > 0; i--) {
                        ItemStack item = new ItemStack(Integer.valueOf(json.get("inv_item_" + String.valueOf(i) + "_item").toString()));
                        ItemMeta itemMet = item.getItemMeta();
                        itemMet.setDisplayName(json.get("inv_item_" + String.valueOf(i) + "_name").toString());
                        item.setItemMeta(itemMet);
                        
                        int rdm = new Random().nextInt(Integer.valueOf(json.get("inv_items").toString())-1+1)+1;
                        
                        if(item.getItemMeta().getDisplayName().contains(String.valueOf(rdm))) {
                            items.add(item);
                        }
                        
                        inv.setItem(Integer.valueOf(json.get("inv_item_" + String.valueOf(i) + "_slot").toString()), item);
                    }
                
                p.openInventory(inv);
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(PlayerInteractListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
