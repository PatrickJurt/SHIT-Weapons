package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import ch.shit.util.WeaponUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

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

                    WeaponUtil.getAmmoFromLore(e.getItem());

                    //Trigger the zoomEffect
                    PlayerUtil.toggleZoom(e.getPlayer());
                    e.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void onLeftClick(PlayerInteractEvent e) {

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {

            if (e.getHand() == EquipmentSlot.HAND) {

                Player p = e.getPlayer();
                //If player has orange Dye in his hand
                if (p.getInventory().getItemInMainHand().getType() == Material.ORANGE_DYE) {

                    //If player has reloaded just before and Event is called from dropevent
                    //If during a reload of the plugin players stay on the server and try to shoot without reloading
                    //There will be an Error-message.
                    if (System.currentTimeMillis() - PlayerUtil.playerLastReload.get(p) > 100) {

                        //Cancel the block-braking or hitting.
                        e.setCancelled(true);

                        //Get ammo in weapon.
                        ItemStack weapon = p.getInventory().getItemInMainHand();
                        int ammo = WeaponUtil.getAmmoFromLore(weapon);

                        if (ammo > 0) {
                            //Actually shoot the Bullet
                            WeaponUtil.shootBullet(p);

                            //Remove 1 ammo from weapon
                            WeaponUtil.setAmmoInLore(weapon, ammo - 1);
                        }
                        //Actionbar for ammodisplay
                        WeaponUtil.makeActionbar(p, weapon, 10);
                    }
                }
            }
        }
    }
}
