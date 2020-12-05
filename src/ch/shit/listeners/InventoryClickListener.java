package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private static Main plugin;

    public InventoryClickListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

        //If a player is clicking on the side of the inventory, put him in a list
        //If then a item is dropping, he just dragged it out of the inventory.
        if (e.getClickedInventory() == null){
            Player p = (Player) e.getWhoClicked();
            if (!PlayerUtil.itemDroppingPlayers.contains(p)) {
                PlayerUtil.itemDroppingPlayers.add(p);
                return;
            }
        }
    }
}
