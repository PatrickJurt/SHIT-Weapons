package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    private static Main plugin;

    public PlayerInteractListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {

        //If player right-clicks
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){

            //Event is triggeret for both main and offhand.
            //Only handle the event for main-hand.
            if (e.getHand() == EquipmentSlot.HAND){

                //If player has orange Dye in his hand
                if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.ORANGE_DYE){

                    //Trigger the zoomEffect
                    PlayerUtil.toggleZoom(e.getPlayer());
                }
            }
        }
    }
}
