package ch.shit.util;

import ch.shit.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class WeaponUtil {

    private static Main plugin;

    public WeaponUtil(Main plugin){
        this.plugin = plugin;
    }

    public static void shootBullet(Player p){
        //Adjust Projectile Position to be on eye-height.
        Location loc = p.getLocation();
        loc.add(0, 1.6, 0);

        Vector dir;

        //Add spread to the direction-Vector if player is in zoom-Mode
        if (PlayerUtil.zoomedPlayers.contains(p)) {
            dir = BulletUtil.addSpread(p.getLocation().getDirection(), 0.01);
        }else {
            //Add spread to the direction-Vector
            dir = BulletUtil.addSpread(p.getLocation().getDirection(), 0.1);
        }

        //Spawn arrow, set vector, velocity, shooter and add Metadata
        Arrow arrow = p.getWorld().spawn(loc, Arrow.class);
        arrow.setVelocity(dir.multiply(5));
        arrow.setShooter(p);
        arrow.setMetadata("Bullet", new FixedMetadataValue(plugin, "yes!"));
    }

    public static void reloadWeapon(Player p, Item weapon){

        Inventory inv = p.getInventory();

        //If the player hasn't any ammo at all.
        if (!(inv.contains(Material.ORANGE_TULIP, 1))){
            p.sendMessage("No ammo in Inventory.");

        //If player has one magazine size or more ammo in his inventory.
        }else if (inv.contains(Material.ORANGE_TULIP, 10)){
            int ammoCount = 10;
            for (ItemStack i : inv){
                if (i != null && ammoCount != 0){
                    if (i.getType() == Material.ORANGE_TULIP){
                        if (i.getAmount() > ammoCount){
                            i.setAmount(i.getAmount() - ammoCount);
                            ammoCount = 0;
                        }else{
                            ammoCount -= i.getAmount();
                            i.setAmount(0);
                        }
                    }
                }
            }

            /*
            TODO
            Reload weapon with bullets.
             */

        //Player has between 0 and magazine size ammo in his inventory.
        }else{
            int ammoCount = 0;
            for (ItemStack i : inv){
                if (i != null){
                    if (i.getType() == Material.ORANGE_TULIP){
                        ammoCount += i.getAmount();
                    }
                }
            }
            inv.remove(Material.ORANGE_TULIP);

            /*TODO
            Reload weapon with bullets.
             */
        }


    }
}
