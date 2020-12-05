package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    private static Main plugin;

    public InventoryCloseListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){

        //Remove player who close their inventory from the droppingItemList
        PlayerUtil.itemDroppingPlayers.remove(e.getPlayer());
    }
}
