package ch.shit.main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
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
* class PlayerClickEvent
*  * onPlayerRightClick()
*  * onPlayerLeftClick()
*
* class PlayerDropEvent
*
* */

