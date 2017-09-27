package com.mcndsj.CSGO_Lobby.KitSystem;

import com.mcndsj.Lobby_Display.Lobby_Display;
import com.mcndsj.Lobby_Statistics.Api.QueryCallBack;
import com.mcndsj.Lobby_Statistics.Cache.Data;
import com.mcndsj.Lobby_Statistics.Cache.DataType;
import com.mcndsj.Lobby_Statistics.lobby_Statistics;
import com.mcndsj.CSGO_Lobby.Utils.WallSQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matthew on 8/06/2016.
 */
public class KitCallBack implements QueryCallBack{

    private static List<String> buffer = Collections.synchronizedList(new ArrayList<String>());

    private String name ;
    private KitType type;

    public KitCallBack(String name,KitType type){
        this.name = name;
        this.type = type;
        synchronized (buffer) {
            this.buffer.add(name);
        }
    }

    @Override
    public void onResult(Data data) {
        Player p = Bukkit.getPlayer(name);
        if(p == null)
            return;
        int cash = -1;
        try{
            cash = (int) data.get("Coins");
        }catch(Exception e){
            p.sendMessage(ChatColor.AQUA + "职业 >> 代币不足,购买失败.积极参与游戏可以获取更多的代币!");
            return;
        }

        HashMap<KitType,Integer> levelMap = WallSQLUtils.getKitMap(Bukkit.getPlayer(name));
        int price = type.getPrice(levelMap.get(type) + 1);
        int afterLevel = levelMap.get(type) + 1;

        if(price > cash){
            p.sendMessage(ChatColor.AQUA + "职业 >> 代币不足,购买失败.积极参与游戏可以获取更多的代币!");
            return;
        }

        //take the money
        try{
            WallSQLUtils.modifyWallMoney((int)data.get("id"),cash - price);
        }catch(Exception e){
            e.printStackTrace();
            p.sendMessage(ChatColor.AQUA + "职业 >> 内部错误204,请联系管理员!");
            return;
        }
        // set the level
        try {
            WallSQLUtils.modifyKit(p, (int)data.get("id"),type,afterLevel);
        }catch(Exception e){
            e.printStackTrace();
            p.sendMessage(ChatColor.AQUA + "职业 >> 内部错误205,请联系管理员!");
            return;
        }

        // clear cache
        lobby_Statistics.getInstance().getApi().invalidCache(name, DataType.walls);
        p.sendMessage(" " );
        p.sendMessage(" " );
        boolean isUnlock = false;
        if(!levelMap.containsKey(type) || levelMap.get(type) == 0) {
            p.sendMessage(ChatColor.AQUA + "职业 >> 花费代币"+ price + "成功购买职业");
            isUnlock = true;
        }else{
            p.sendMessage(ChatColor.AQUA + "职业 >> 花费代币"+ price + "成功升级职业 " + type.getDisplayNameWithColor() + ChatColor.AQUA + " 当前职业等级: " +ChatColor.GREEN + (levelMap.get(type) + 1));
            isUnlock =  afterLevel % (SkillType.MAX_STEP +1) == 0;
        }

        if(isUnlock){
            p.sendMessage(ChatColor.AQUA + "职业 >> 解锁新技能" );
            p.sendMessage(ChatColor.YELLOW + type.getLastSkillDes(afterLevel) );
        }else{
            p.sendMessage(ChatColor.AQUA + "职业 >> 技能效果提升 ");
            p.sendMessage(ChatColor.YELLOW + type.getLastSkillDes(afterLevel - 1) );
            p.sendMessage(ChatColor.AQUA + " ↓↓↓ 效果晋升 ↓↓↓");
            p.sendMessage(ChatColor.YELLOW + type.getLastSkillDes(afterLevel) );
        }
        p.sendMessage(" " );
        p.sendMessage(" " );

        p.playSound(p.getLocation(), Sound.LEVEL_UP,1F,1F);

        synchronized (buffer) {
            buffer.remove(name);
        }
        Lobby_Display.getInstance().getApi().refreshBoard(p);
    }

    public static boolean isCallingBack(String name){
        synchronized (buffer) {
            return buffer.contains(name);
        }
    }


}
