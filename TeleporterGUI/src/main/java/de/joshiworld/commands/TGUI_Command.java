package de.joshiworld.commands;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
public class TGUI_Command implements CommandExecutor {
    
    public static List<ItemStack> items = new ArrayList<ItemStack>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            System.out.println("§cDu musst ein Spieler sein");
            return true;
        } else {
            Player p = (Player) sender;
            
            if(cmd.getName().equalsIgnoreCase("TGUI")) {
                File file = new File("plugins/TGUI/config.json");
                JSONParser parser = new JSONParser();
                
                try {
                    JSONObject json = (JSONObject) parser.parse(new FileReader(file));
                    
                    if(args.length == 0) {
                    Inventory inv = Bukkit.createInventory(null, Integer.valueOf(json.get("inventory_size").toString()), json.get("inventory_name").toString());
                    
                    items.clear();
                    
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
                } else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("help")) {
                        p.sendMessage("§c/tgui §8» §7Öffnet das Teleporter-Inventar");
                        p.sendMessage("§c/tgui help §8» §7Listet alle Befehle auf");
                        p.sendMessage("§c/tgui resetCfg §8» §7Resetet die Config");
                    } else if(args[0].equalsIgnoreCase("resetCfg")) {
                        file.delete();
                        file.createNewFile();
                        
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
                        
                        try(FileWriter fileW = new FileWriter(file.getPath())) {
                            fileW.write(json.toJSONString());
                            fileW.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        p.sendMessage("§aDie Config wurde resetet");
                    } else {
                        p.sendMessage("§cFalscher Syntax!");
                    }
                } else {
                    p.sendMessage("§cFalscher Syntax!");
                }
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(TGUI_Command.class.getName()).log(Level.SEVERE, null, ex);
                    p.sendMessage("§cconfig.json exestiert nicht!");
                }
                return true;
            }
        }
        return false;
    }
}
