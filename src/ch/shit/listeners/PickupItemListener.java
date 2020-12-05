package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.WeaponUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PickupItemListener implements Listener {

    private static Main plugin;

    public PickupItemListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e){
        ItemStack is = e.getItem().getItemStack();
        if (WeaponUtil.weapons.containsKey(is.getType())){
            WeaponUtil.setLore(is);
        }
    }
}
