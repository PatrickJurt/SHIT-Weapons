package ch.shit.util;

import org.bukkit.util.Vector;

public class BulletUtil {

    public static Vector addSpread(Vector v, double spread){
        Vector a = new Vector(Integer.MAX_VALUE, Integer.MAX_VALUE,Integer.MAX_VALUE);

        while(Math.sqrt((a.getX()*a.getX()) + (a.getY()*a.getY()) + (a.getZ()*a.getZ())) > spread){
            a = new Vector(0, 0, 0);
            a.setX((Math.random() * spread * 2) - spread);
            a.setY((Math.random() * spread * 2) - spread);
            a.setZ((Math.random() * spread * 2) - spread);
        }

        double vAbs = getAbs(v);

        v.add(a);

        v.setX((v.getX()*vAbs)/getAbs(v));
        v.setY((v.getY()*vAbs)/getAbs(v));
        v.setZ((v.getZ()*vAbs)/getAbs(v));

        return v;
    }


    public static double getAbs(Vector v){
        return Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) + Math.pow(v.getZ(), 2));
    }
}
