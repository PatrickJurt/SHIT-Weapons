package ch.shit.util;

import org.bukkit.util.Vector;

public class BulletUtil {

    /*
    * Adds spread to a vector with max the length of spread.
    */
    public static Vector addSpread(Vector v, double spread){
        Vector a = new Vector(Integer.MAX_VALUE, Integer.MAX_VALUE,Integer.MAX_VALUE);

        //Create random vectors until one has a length of spread.
        while(Math.sqrt((a.getX()*a.getX()) + (a.getY()*a.getY()) + (a.getZ()*a.getZ())) > spread){
            a = new Vector(0, 0, 0);
            a.setX((Math.random() * spread * 2) - spread);
            a.setY((Math.random() * spread * 2) - spread);
            a.setZ((Math.random() * spread * 2) - spread);
        }

        //Get length of current direction-vector.
        double vAbs = getAbs(v);

        //Add created spread-vector to the existing direction-vector.
        v.add(a);

        //Compress the resulting vector until it has the length of the original vector.
        //But now it has a little bit a different direction.
        v.setX((v.getX()*vAbs)/getAbs(v));
        v.setY((v.getY()*vAbs)/getAbs(v));
        v.setZ((v.getZ()*vAbs)/getAbs(v));

        //Return the resulting vector with the spread added.
        return v;
    }


    /*
    * Get absolute value of a vector.
    */
    public static double getAbs(Vector v){
        return Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) + Math.pow(v.getZ(), 2));
    }
}
