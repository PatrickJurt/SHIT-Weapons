package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import ch.shit.util.WeaponUtil;
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
        ItemStack iPrev = e.getPlayer().getInventory().getItem(e.getPreviousSlot());
        ItemStack iCurr = e.getPlayer().getInventory().getItem(e.getNewSlot());

        //If the previous Item isn't null
        //Otherwise an error-message occurs, because you can't getType() of a null-item.
        if (iPrev != null){
            if (iPrev.getType() == Material.ORANGE_DYE){

                //If the player switches from orange dye to something else
                //and he is in zoom-mode, toggle zoom-mode
                if (PlayerUtil.zoomedPlayers.contains(e.getPlayer())){
                    PlayerUtil.toggleZoom(e.getPlayer());
                }
            }
        }

        if (iCurr != null){
            if (iCurr.getType() == Material.ORANGE_DYE){
                WeaponUtil.makeActionbar(e.getPlayer(), iCurr, 10);
            }
        }
    }
}
