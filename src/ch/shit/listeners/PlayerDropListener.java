package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import ch.shit.util.WeaponUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDropListener implements Listener {

    private static Main plugin;
    private static FileConfiguration config;

    public PlayerDropListener(Main plugin, FileConfiguration config){
        this.plugin = plugin;
        this.config = config;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        ItemStack is = e.getItemDrop().getItemStack();

        //If the item that gets dropped is a weapon
        //And if the player isn't dropping the item by hand.
        if (WeaponUtil.weapons.containsKey(is.getType()) && !PlayerUtil.itemDroppingPlayers.contains(p)){
            e.setCancelled(true);

            WeaponUtil.giveFreshWeaponALore(is);

            //Add him to the reloading players, so the shooting in the Interact Event doesnt get called
            //Cause by dropping an item the interact event gets called. For whatever reason.
            //Get the player out of zoom-Mode
            PlayerUtil.playerLastReload.put(p, System.currentTimeMillis());
            if (PlayerUtil.zoomedPlayers.contains(p)){
                PlayerUtil.toggleZoom(p, null);
            }

            WeaponUtil.reloadWeapon(p, e.getItemDrop());

            WeaponUtil.makeActionbar(p, is, config.getInt("gun." + WeaponUtil.weapons.get(is.getType()) + ".magazineSize"));
        }
    }
}
