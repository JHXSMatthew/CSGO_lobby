package com.mcndsj.CSGO_Lobby.commands;

import com.mcndsj.CSGO_Lobby.CSGO_Lobby;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.rpapi.ResourcePackAPI;
import us.myles.ViaVersion.api.ViaVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matthew on 1/07/2016.
 */
public class Reat implements CommandExecutor {
    List<String> timeTable = new ArrayList<String>();

    public Reat(){
        new BukkitRunnable(){
            @Override
            public void run() {
                timeTable.clear();
            }
        }.runTaskTimer(CSGO_Lobby.get(),0,20 * 30);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            if(timeTable.contains(commandSender.getName())){
                commandSender.sendMessage(ChatColor.AQUA + "提示 >> 材质包正在下载中,请稍等!");
                return true;
            }
            Player p = (Player) commandSender;
            if(ViaVersion.getInstance().isPorted(p)) {
                ResourcePackAPI.setResourcepack(p,"https://www.dropbox.com/s/zuyen2p3l4ghgok/CounterStrike19.zip?dl=1","f1b8ed12b150e86edec5d5ecc28443f905d1f3da");
            }else {
                ResourcePackAPI.setResourcepack(p,"https://www.dropbox.com/s/pgi16yjz0r19lv4/CounterStrike.zip?dl=1","f1b8ed12b150e86edec5d5ecc28443f905d1f3da");
            }
            p.sendMessage(ChatColor.AQUA + "提示 >> 发送材质包……");
            timeTable.add(commandSender.getName());
            return true;
        }
        return false;
    }


}
