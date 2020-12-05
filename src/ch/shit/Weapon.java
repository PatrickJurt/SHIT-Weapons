package ch.shit;

import ch.shit.main.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Weapon {

    String gunType;
    double spread;
    double scopedSpread;
    double attackSpeed;
    double magazineSize;

    private Main plugin;

    static Plugin config = Main.getPlugin(Main.class);

    public Weapon(Main plugin, ItemStack i){
        this.plugin = plugin;

        setAttributesFromConfig(i.getType());
        setMetaData(i);
    }

    public void setAttributesFromConfig(Material mat){
        if (mat == Material.ORANGE_DYE) this.gunType = "pistol";
        if (mat == Material.YELLOW_DYE) this.gunType = "ar";

        if (this.gunType != null){
            this.spread = config.getConfig().getDouble("gun." + gunType + ".spread");
            this.scopedSpread = config.getConfig().getDouble("gun." + gunType + ".scopedSpread");
            this.attackSpeed = config.getConfig().getDouble("gun." + gunType + ".attackSpeed");
            this.magazineSize = config.getConfig().getDouble("gun." + gunType + ".magazineSize");
        }
    }

    public void setMetaData(ItemStack i){
        ItemMeta meta = i.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(gunType.substring(0,1).toUpperCase() + gunType.substring(1));
        lore.add("Ammo: " + 0);
        meta.setLore(lore);
        i.setItemMeta(meta);
    }
}
