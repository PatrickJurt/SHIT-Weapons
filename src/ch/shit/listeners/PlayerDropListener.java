package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import ch.shit.util.WeaponUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {

    private static Main plugin;

    public PlayerDropListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();

        if (e.getItemDrop().getItemStack().getType() == Material.ORANGE_DYE){
            //Add him to the reloading players, so the shooting in the Interact Event doesnt get called
            //Cause by dropping an item the interact event gets called. For whatever reason.
            //Get the player out of zoom-Mode
            PlayerUtil.reloadingPlayers.add(p);
            if (PlayerUtil.zoomedPlayers.contains(e.getPlayer())){
                PlayerUtil.toggleZoom(e.getPlayer());
            }

            WeaponUtil.reloadWeapon(e.getPlayer(), e.getItemDrop());


            e.setCancelled(true);
        }
    }
}
