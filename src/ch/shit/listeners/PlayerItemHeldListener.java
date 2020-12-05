package ch.shit.listeners;

import ch.shit.main.Main;
import ch.shit.util.PlayerUtil;
import ch.shit.util.WeaponUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class PlayerItemHeldListener implements Listener {

    private static Main plugin;
    private static Plugin config = Main.getPlugin(Main.class);
    private static Map<Material, String> weapons = WeaponUtil.weapons;

    public PlayerItemHeldListener(Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerChangeItem(PlayerItemHeldEvent e){
        Player p = e.getPlayer();
        Inventory inv = e.getPlayer().getInventory();
        ItemStack iPrev = inv.getItem(e.getPreviousSlot());
        ItemStack iCurr = inv.getItem(e.getNewSlot());

        //Toggle zoom if previous item was a weapon
        if (iPrev != null){
            if (weapons.containsKey(iPrev.getType())){

                //If the player switches from orange dye to something else
                //and he is in zoom-mode, toggle zoom-mode
                if (PlayerUtil.zoomedPlayers.contains(p)){
                    PlayerUtil.toggleZoom(p, null);
                }
            }
        }

        if (iCurr != null){
            //If current Item is a weapon
            //Add lore if it isn't there yet.
            //Display actionbar.
            if (weapons.containsKey(iCurr.getType())){
                if (!iCurr.getItemMeta().hasLore()){
                    WeaponUtil.setInitialLore(iCurr);
                }
                WeaponUtil.makeActionbar(p, iCurr, config.getConfig().getInt("gun." + weapons.get(iCurr.getType()) + ".magazineSize"));
            }
        }
    }
}
