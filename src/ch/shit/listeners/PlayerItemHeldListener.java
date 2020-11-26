package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemHeldListener implements Listener {

    private static Main plugin;

    public PlayerItemHeldListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerChangeItem(PlayerItemHeldEvent e){
        ItemStack i = e.getPlayer().getInventory().getItem(e.getPreviousSlot());

        //If the previous Item isn't null
        //Otherwise an error-message occurs, because you can't getType() of a null-item.
        if (i != null){
            if (i.getType() == Material.ORANGE_DYE){

                //If the player switches from orange dye to something else
                //and he is in zoom-mode, toggle zoom-mode
                if (Main.zoomedPlayers.contains(e.getPlayer())){
                    PlayerUtil.toggleZoom(e.getPlayer());
                }
            }
        }
    }
}
