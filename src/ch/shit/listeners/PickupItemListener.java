package ch.shit.listeners;

import ch.shit.Weapon;
import ch.shit.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupItemListener implements Listener {

    private static Main plugin;

    public PickupItemListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e){
        if (e.getItem().getItemStack().getType() == Material.ORANGE_DYE ||
        e.getItem().getItemStack().getType() == Material.YELLOW_DYE){
            new Weapon(plugin, e.getItem().getItemStack());
        }
    }
}
