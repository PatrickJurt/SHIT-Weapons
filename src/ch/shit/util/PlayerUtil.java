package ch.shit.util;

import ch.shit.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerUtil {


    //This method toggles the zoom-Effect for a player.
    public static void toggleZoom(Player p){
        if (p != null) {
            if (Main.zoomedPlayers.contains(p)) {
                p.removePotionEffect(PotionEffectType.SLOW);
                Main.zoomedPlayers.remove(p);
            } else {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
                Main.zoomedPlayers.add(p);
            }
        }
    }
}
