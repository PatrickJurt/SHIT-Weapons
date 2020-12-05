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
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class PlayerInteractListener implements Listener {

    private static Main plugin;
    private static Plugin config = Main.getPlugin(Main.class);
    private static Map<Material, String> weapons = WeaponUtil.weapons;

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

                //If Item in Hand is a Weapon
                Material mat = e.getPlayer().getInventory().getItemInMainHand().getType();
                if(weapons.containsKey(mat)){
                    String gun = weapons.get(mat);

                    //Trigger the zoomEffect
                    PlayerUtil.toggleZoom(e.getPlayer(), gun);
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
                ItemStack itemStack = p.getInventory().getItemInMainHand();
                Material mat = itemStack.getType();

                //If player has a weapon in his hand
                if (weapons.containsKey(mat)) {
                    String gun = weapons.get(mat);

                    //If player has only one item in hand.
                    if (itemStack.getAmount() == 1) {

                        //If player has reloaded just before and Event is called from Drop-Event
                        //If during a reload of the plugin players stay on the server and try to shoot without reloading
                        //There will be an Error-message.
                        if (System.currentTimeMillis() - PlayerUtil.playerLastReload.get(p) > 100) {

                            //Cancel the block-braking or hitting.
                            e.setCancelled(true);

                            //Get ammo in weapon.
                            int ammo = WeaponUtil.getAmmoFromLore(itemStack);

                            if (ammo > 0) {
                                //Actually shoot the Bullet
                                WeaponUtil.shootBullet(p, gun);

                                //Remove 1 ammo from weapon
                                WeaponUtil.setAmmoInLore(itemStack, ammo - 1);
                            }
                            //Actionbar for AmmoDisplay
                            WeaponUtil.makeActionbar(p, itemStack, config.getConfig().getInt("gun." + gun + ".magazineSize"));
                        }

                    //Player has more than 1 weapon in ItemSlot
                    }else{
                        p.sendMessage("You can't shoot more than 1 weapon at the same time.");
                    }
                }
            }
        }
    }
}
