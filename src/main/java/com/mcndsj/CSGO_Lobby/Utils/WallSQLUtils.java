package com.mcndsj.CSGO_Lobby.Utils;

import com.mcndsj.JHXSMatthew.Shared.GameManager;
import com.mcndsj.CSGO_Lobby.KitSystem.KitType;
import org.bukkit.entity.Player;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Created by Matthew on 8/06/2016.
 */
public class WallSQLUtils {

    public static HashMap<KitType,Integer> getKitMap(Player gp){
        HashMap<KitType,Integer> map = null;
        try (Connection c = GameManager.getInstance().getConnection();
             Statement s = c.createStatement();
             ResultSet result = s.executeQuery("SELECT * FROM `TheWallsKits` WHERE id= (SELECT TheWalls.id FROM TheWalls WHERE TheWalls.Name = '"+gp.getName()+"');");){
            if(result.next()){
                map = new HashMap<KitType,Integer>();
                for(KitType type : KitType.values())
                    map.put(type,result.getInt(type.getDbName()));
            }else{
                map = new HashMap<KitType,Integer>();
                for(KitType type : KitType.values())
                    map.put(type,0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public static void modifyKit(Player p,int id , KitType kit, int level){
        try(Connection c = GameManager.getInstance().getConnection();
        Statement s = c.createStatement();){
            if(isPlayerInKitTable(p)) {
                s.executeUpdate("UPDATE TheWallsKits SET `" + kit.getDbName() + "`=" + level + " WHERE `id`=" + id + ";");
            }else{
                s.executeUpdate("INSERT INTO TheWallsKits (`id`,`"+ kit.getDbName() +"`) VALUES ("+ id+","+ level+");");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modifyWallMoney(int id,int amount){
        try(Connection c = GameManager.getInstance().getConnection();
            Statement s = c.createStatement();){
            s.executeUpdate("UPDATE TheWalls SET `Coins`=" + amount + " WHERE `id`=" + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlayerInKitTable(Player p){
        try(Connection c = GameManager.getInstance().getConnection();
         Statement s = c.createStatement();
            ResultSet result = s.executeQuery("SELECT current FROM `TheWallsKits` WHERE id= (SELECT TheWalls.id FROM TheWalls WHERE TheWalls.Name = '"+p.getName()+"');");){
            if(result.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
