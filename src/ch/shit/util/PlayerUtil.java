package ch.shit.util;

import ch.shit.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PlayerUtil {

    private static Main plugin;

    public static Set<Player> zoomedPlayers = new HashSet<>();
    public static Map<Player, Long> playerLastReload = new HashMap<>();
    public static Set<Player> itemDroppingPlayers = new HashSet<>();
    static Plugin config = Main.getPlugin(Main.class);

    public PlayerUtil(Main plugin){
        this.plugin = plugin;
    }



    //This method toggles the zoom-Effect for a player.
    public static void toggleZoom(Player p, String gun){
        if (p != null) {

            //Remove zoom if player is in zoom-Mode, put him in zoom-Mode otherwise.
            if (zoomedPlayers.contains(p)) {
                p.removePotionEffect(PotionEffectType.SLOW);
                zoomedPlayers.remove(p);
            } else {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, config.getConfig().getInt("gun." + gun + ".scopedZoom")));
                zoomedPlayers.add(p);
            }
        }
    }
}
