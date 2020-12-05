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

        //If the item that gets dropped is a weapon
        //And if the player isnt dropping the item by hand.
        if (e.getItemDrop().getItemStack().getType() == Material.ORANGE_DYE && !PlayerUtil.itemDroppingPlayers.contains(p)){
            e.setCancelled(true);

            WeaponUtil.giveFreshWeaponALore(e.getItemDrop().getItemStack());

            //Add him to the reloading players, so the shooting in the Interact Event doesnt get called
            //Cause by dropping an item the interact event gets called. For whatever reason.
            //Get the player out of zoom-Mode
            PlayerUtil.playerLastReload.put(p, System.currentTimeMillis());
            if (PlayerUtil.zoomedPlayers.contains(e.getPlayer())){
                PlayerUtil.toggleZoom(e.getPlayer());
            }

            WeaponUtil.reloadWeapon(e.getPlayer(), e.getItemDrop());

            WeaponUtil.makeActionbar(p, e.getItemDrop().getItemStack(), 10);
        }
    }
}
