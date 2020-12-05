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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class PlayerDropListener implements Listener {

    private static Main plugin;
    private static Plugin config = Main.getPlugin(Main.class);
    private static Map<Material, String> weapons = WeaponUtil.weapons;

    public PlayerDropListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        ItemStack itemStack = e.getItemDrop().getItemStack();
        Material mat = itemStack.getType();

        //If the item that gets dropped is a weapon
        //And if the player isn't dropping the item by hand.
        if (weapons.containsKey(mat) && !PlayerUtil.itemDroppingPlayers.contains(p)){
            e.setCancelled(true);

            //Add player to the reloading players list, so the shooting in the Interact Event doesn't get called
            //Cause by dropping an item the interact event gets called. For whatever reason.
            //Get the player out of zoom-Mode
            PlayerUtil.playerLastReload.put(p, System.currentTimeMillis());
            if (PlayerUtil.zoomedPlayers.contains(p)){
                PlayerUtil.toggleZoom(p, null);
            }

            //Reload weapon and make actionbar.
            WeaponUtil.reloadWeapon(p, e.getItemDrop());
            WeaponUtil.makeActionbar(p, itemStack, config.getConfig().getInt("gun." + weapons.get(mat) + ".magazineSize"));
        }
    }
}
