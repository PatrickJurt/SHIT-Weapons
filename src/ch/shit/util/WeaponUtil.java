package ch.shit.util;

import ch.shit.main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponUtil {

    private static Main plugin;
    static Plugin config = Main.getPlugin(Main.class);
    public static Map<Material, String> weapons = new HashMap<>();
    public static Map<String, Material> ammo = new HashMap<>();

    public WeaponUtil(Main plugin){
        this.plugin = plugin;
    }

    public static void shootBullet(Player p, String gun){
        //Adjust Projectile Position to be on eye-height.
        Location loc = p.getLocation();
        loc.add(0, 1.6, 0);

        Vector dir = loc.getDirection();

        //Add spread to the direction-Vector
        if (PlayerUtil.zoomedPlayers.contains(p)) {
            dir = BulletUtil.addSpread(dir, config.getConfig().getDouble("gun." + gun + ".scopedSpread"));
        }else {
            dir = BulletUtil.addSpread(dir, config.getConfig().getDouble("gun." + gun + ".spread"));
        }

        //Spawn arrow, set vector, velocity, shooter and add Metadata
        Arrow arrow = p.getWorld().spawn(loc, Arrow.class);
        arrow.setVelocity(dir.multiply(5));
        arrow.setShooter(p);
        arrow.setMetadata("Bullet", new FixedMetadataValue(plugin, "yes!"));

    }

    public static void reloadWeapon(Player p, Item weapon){
        ItemStack itemStack = weapon.getItemStack();
        String gun = weapons.get(itemStack.getType());

        int magazineSize = config.getConfig().getInt("gun." + gun + ".magazineSize");
        Inventory inv = p.getInventory();

        //If the player has any ammo.
        if (inv.contains(ammo.get(gun), 1)){
            //ammoCount = How much ammo has to be reloaded.
            int ammoCount = magazineSize - getAmmoFromLore(itemStack);

            //For every ItemStack in Inventory remove in total magazineSize amount of ammo.
            for (ItemStack i : inv) {
                if (i != null && ammoCount != 0) {
                    if (i.getType() == WeaponUtil.ammo.get(gun)) {
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
            setAmmoInLore(itemStack, ammoInWeapon);


        //If player has no ammo in inventory.
        }else{
            p.sendMessage("No ammo in Inventory.");
        }
    }

    //Set ammo of -weapon- to -ammoInWeapon
    public static void setAmmoInLore(ItemStack weapon, int ammo){
        ItemMeta meta = weapon.getItemMeta();
        List<String> lore = meta.getLore();

        lore.remove(1);
        lore.add("Ammo: " + ammo);
        meta.setLore(lore);
        weapon.setItemMeta(meta);
    }


    //Returns the amount of ammo in the weapon
    public static int getAmmoFromLore(ItemStack weapon){

        //If Itemstack is bigger than 1, you should'nt shoot with it. :)
        if (weapon.getAmount() > 1){
            return 999;
        }

        //Get Line of lore where ammo is displayed. Remove "Ammo:" and return leftover int
        for (String line : weapon.getItemMeta().getLore()){
            if (line.startsWith("Ammo:")){
                String intString = line.replace("Ammo: ", "");
                return Integer.parseInt(intString);
            }
        }
        return 999;
    }

    public static void makeActionbar(Player p, ItemStack weapon, int magazineSize){
        int ammo = getAmmoFromLore(weapon);
        if (ammo == 999){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Something's wrong, please contact someone!"));
            return;
        }

        //Get percentage of ammo left in the weapon
        double percentage = 100.0 * ammo / magazineSize;
        int i = 0;

        //First blocks are in green
        String msg = "§a";
        for (i = 5; i <= percentage; i += 5){
            msg += "█";
        }
        //Rest should be red.
        msg += "§4";
        for(int j = i; j <= 100; j += 5){
            msg += "█";
        }

        //print in the actionbar.
        ChatColor.translateAlternateColorCodes('&', msg);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
    }

    public static void setInitialLore(ItemStack i){
        ItemMeta meta = i.getItemMeta();

        //Lore
        List<String> lore = new ArrayList<>();
        lore.add(getDisplayNameFromItem(i));
        lore.add("Ammo: " + 0);
        meta.setLore(lore);
        i.setItemMeta(meta);
    }

    /*
    Get display name of a weapon using the Material.
     */
    public static String getDisplayNameFromItem(ItemStack i){
        for (Map.Entry<Material, String> entry : weapons.entrySet()) {
            if (i.getType() == entry.getKey()) return config.getConfig().getString("gun." + entry.getValue() + ".displayName");
        }
        return "invalidWeapon";
    }
}
