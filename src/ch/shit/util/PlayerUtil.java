package ch.shit.util;

import ch.shit.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerUtil {

    private static Main plugin;

    public static List<Player> zoomedPlayers = new ArrayList<>();
    public static Map<Player, Long> playerLastReload = new HashMap<>();
    public static List<Player> itemDroppingPlayers = new ArrayList<>();

    public PlayerUtil(Main plugin){
        this.plugin = plugin;
    }


    //This method toggles the zoom-Effect for a player.
    public static void toggleZoom(Player p){
        if (p != null) {

            //Remove zoom if player is in zoom-Mode, put him in zoom-Mode otherwise.
            if (zoomedPlayers.contains(p)) {
                p.removePotionEffect(PotionEffectType.SLOW);
                zoomedPlayers.remove(p);
            } else {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
                zoomedPlayers.add(p);
            }
        }
    }
}
