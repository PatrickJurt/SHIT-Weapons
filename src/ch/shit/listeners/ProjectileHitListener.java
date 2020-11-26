package ch.shit.listeners;

import ch.shit.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {

    private static Main plugin;

    public ProjectileHitListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e){

        //If arrow is a bullet, remove it on hit
        if (e.getEntity().hasMetadata("Bullet")){
            e.getEntity().remove();
        }
    }
}
