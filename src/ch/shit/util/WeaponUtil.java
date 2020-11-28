package ch.shit.util;

import ch.shit.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

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

        //Add spread to the direction-Vector not in zoom-Mode.
        }else {
            dir = BulletUtil.addSpread(p.getLocation().getDirection(), 0.1);
        }

        //Spawn arrow, set vector, velocity, shooter and add Metadata
        Arrow arrow = p.getWorld().spawn(loc, Arrow.class);
        arrow.setVelocity(dir.multiply(5));
        arrow.setShooter(p);
        arrow.setMetadata("Bullet", new FixedMetadataValue(plugin, "yes!"));

    }


    public static void reloadWeapon(Player p, Item weapon){

        int magazineSize = 10;
        Inventory inv = p.getInventory();

        //If the player has any ammo.
        if (inv.contains(Material.ORANGE_TULIP, 1)){
            //ammoCount = How much ammo has to be reloaded.
            int ammoCount = magazineSize - getAmmoFromLore(weapon.getItemStack());

            //For every ItemStack in Inventory remove in total magazineSize amount of ammo.
            for (ItemStack i : inv) {
                if (i != null && ammoCount != 0) {
                    if (i.getType() == Material.ORANGE_TULIP) {
                        if (i.getAmount() > ammoCount) {
                            i.setAmount(i.getAmount() - ammoCount);
                            ammoCount = 0;
                        } else {
                            ammoCount -= i.getAmount();
                            i.setAmount(0);
                        }
                    }
                }
            }

            //Set lore with the amount of ammo in the weapon.
            int ammoInWeapon = magazineSize - ammoCount;
            setAmmoInLore(weapon.getItemStack(), ammoInWeapon);


        //If player has no ammo in inventory.
        }else{
            p.sendMessage("No ammo in Inventory.");
        }
    }

    //Set ammo of -weapon- to -ammoInWeapon
    public static void setAmmoInLore(ItemStack weapon, int ammo){
        ItemMeta meta = weapon.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("Ammo: " + ammo);
        meta.setLore(lore);
        weapon.setItemMeta(meta);
    }


    //Returns the amount of ammo in the weapon
    public static int getAmmoFromLore(ItemStack weapon){
        for (String line : weapon.getItemMeta().getLore()){
            if (line.startsWith("Ammo:")){
                String intString = line.replace("Ammo: ", "");
                return Integer.parseInt(intString);
            }
        }
        return 999;
    }
}