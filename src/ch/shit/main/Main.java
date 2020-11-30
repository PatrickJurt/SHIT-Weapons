package ch.shit.main;

import ch.shit.listeners.*;
import ch.shit.util.BulletUtil;
import ch.shit.util.PlayerUtil;
import ch.shit.util.WeaponUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        new PlayerJoinListener(this);
        new PlayerInteractListener(this);
        new PlayerItemHeldListener(this);
        new PlayerDropListener(this);
        new ProjectileHitListener(this);

        new BulletUtil(this);
        new PlayerUtil(this);
        new WeaponUtil(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        System.out.println("-----------------------------");
        System.out.println("S.H.I.T - Weapons enabled.");
        System.out.println("-----------------------------");
    }


    @Override
    public void onDisable(){
        System.out.println("-----------------------------");
        System.out.println("S.H.I.T - Weapons disabled.");
        System.out.println("-----------------------------");
    }
}


/*
*
*
*
*
*
*
*
*
* -----------------------------------
* Brainstorming P
* -----------------------------------
* Maybe no mysql since we haven't got a db-server
* Could be done in a file
*
* config
* - weapons with all attributes
* - projectiles with all attributes
* - attachments with all attributes
*
* class Projectile
* - type
* - velocity
* - effects
* - damage
* - vector
*
* class ProjectileEffectEnum
* - none
* - smoke
*
* class Weapon
* - type
* - projectile-type
* - reload speed
* - projectile speed
* - aiming-zoom
* - magazine-type
* - scattering
*
* class Attachment
*
* --> class magazine
*    - type
*    - magazine-size
*    - reload speed factor
*
* --> class Optic
*    - type
*    - zoom
*
* class ProjectileHitListener
* * onProjectileHit()
*
*
* class PlayerClickListener
*  * onPlayerRightClick()
*  * onPlayerLeftClick()
*
* class PlayerDropEvent
*
* */

