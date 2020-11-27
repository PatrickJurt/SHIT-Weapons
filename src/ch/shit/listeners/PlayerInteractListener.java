package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import ch.shit.util.WeaponUtil;
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

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            //Event is triggered for both main and offhand.
            //Only handle the event for main-hand.
            if (e.getHand() == EquipmentSlot.HAND){
                if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.ORANGE_DYE){
                    //Trigger the zoomEffect
                    PlayerUtil.toggleZoom(e.getPlayer());
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void onLeftClick(PlayerInteractEvent e) {

        //If player isn't reloading.
        if (!(PlayerUtil.reloadingPlayers.contains(e.getPlayer()))){

            //If player right-clicks
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {

                org.bukkit.entity.Player p = e.getPlayer();
                if (e.getHand() == EquipmentSlot.HAND) {

                    //If player has orange Dye in his hand
                    if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.ORANGE_DYE) {

                        //Actually shoot the Bullet
                        WeaponUtil.shootBullet(e.getPlayer());

                        //Cancel the block-braking or hitting.
                        e.setCancelled(true);
                    }
                }
            }
        }
        PlayerUtil.reloadingPlayers.remove(e.getPlayer());
    }
}
