package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.BulletUtil;
import ch.shit.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

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

            //Event is triggered for both main and offhand.
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


    @EventHandler
    public void onLeftClick(PlayerInteractEvent e){

        //If player right-clicks
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {

            Player p = e.getPlayer();
            if (e.getHand() == EquipmentSlot.HAND){

                //If player has orange Dye in his hand
                if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.ORANGE_DYE) {

                    //Adjust Projectile Position to be on eye-height.
                    Location loc = p.getLocation();
                    loc.add(0, 1.6, 0);

                    //Add spread to the direction-Vector
                    Vector dir = BulletUtil.addSpread(p.getLocation().getDirection(), 0.1);

                    //Spawn arrow, set vector, velocity, shooter and add Metadata
                    Arrow arrow = p.getWorld().spawn(loc, Arrow.class);
                    arrow.setVelocity(dir.multiply(5));
                    arrow.setShooter(p);
                    arrow.setMetadata("Bullet", new FixedMetadataValue(plugin, "yes!"));

                    //Cancel the block-braking or hitting.
                    e.setCancelled(true);
                }
            }
        }
    }
}
