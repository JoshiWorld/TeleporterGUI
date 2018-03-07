package de.joshiworld.listener;

import de.joshiworld.commands.TGUI_Command;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

/**
 *
 * @author JoshiWorld
 */
public class InventoryClickListener implements Listener {
    
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        
        e.setCancelled(true);
        
        if(e.getSlotType() == SlotType.CONTAINER) {
            if(e.getCurrentItem().getTypeId() != 0 && e.getCurrentItem() != null) {
                if(TGUI_Command.items.contains(e.getCurrentItem())) {
                    p.closeInventory();
                    p.teleport(new Location(p.getWorld(), p.getLocation().getX()+10, p.getLocation().getY()+10, p.getLocation().getZ()+10));
                    p.sendMessage("§aDu hast das richtige Item ausgewählt :)");
                } else {
                    p.closeInventory();
                    p.sendMessage("§cDiesmal hattest du kein Glück :)");
                }
            } 
        } 
    }
    
}
