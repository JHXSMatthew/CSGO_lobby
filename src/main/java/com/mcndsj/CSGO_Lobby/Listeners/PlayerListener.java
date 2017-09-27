package com.mcndsj.CSGO_Lobby.Listeners;

import com.mcndsj.CSGO_Lobby.ServerSelector.SignClickCSGO;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.inventivetalent.rpapi.ResourcePackAPI;
import org.inventivetalent.rpapi.Status;
import us.myles.ViaVersion.api.ViaVersion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 2016/4/17.
 */
public class PlayerListener implements Listener {

    private SignClickCSGO query;

    public PlayerListener(SignClickCSGO query){
        this.query = query;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt){
        query.remove(evt.getPlayer().getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt){
        Location l = evt.getPlayer().getLocation().clone();
        l.setYaw(-90);
        evt.getPlayer().teleport(l);
        evt.setJoinMessage("");
        Player p = evt.getPlayer();
        if(ViaVersion.getInstance().isPorted(evt.getPlayer())) {
            ResourcePackAPI.setResourcepack(p,"http://rank.mcndsj.com/texts/CounterStrike19.zip","f1b8ed12b150e86edec5d5ecc28443f905d1f3da");
        }else {
            ResourcePackAPI.setResourcepack(p,"http://rank.mcndsj.com/texts/CounterStrike.zip","f1b8ed12b150e86edec5d5ecc28443f905d1f3da");
        }
    }


    @EventHandler
    public void onResourcepackStatus(PlayerResourcePackStatusEvent evt){
        if(evt.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED){
            query.add(evt.getPlayer().getName());
            evt.getPlayer().sendMessage(ChatColor.AQUA + "提示 >> 下载材质包完毕！");
            query.sendTextdownloadComponent(evt.getPlayer());
        }else if(evt.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD){
            evt.getPlayer().sendMessage(ChatColor.AQUA + "提示 >> 材质包下载失败！.");
            query.sendTextdownloadComponent(evt.getPlayer());
        }else if(evt.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED){
            evt.getPlayer().sendMessage(ChatColor.AQUA + "提示 >> 您取消了材质包下载！如果想再次下载材质包,你必须退出该大厅并再次进入.");
        }else if(evt.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED){
            evt.getPlayer().sendMessage(ChatColor.AQUA + "提示 >> 材质包下载传输中,请双手离开键盘等待下载完毕!");
            evt.getPlayer().sendMessage(ChatColor.AQUA + "提示 >> 材质包下载传输中,请双手离开键盘等待下载完毕!");
            evt.getPlayer().sendMessage(ChatColor.AQUA + "提示 >> 材质包下载传输中,请双手离开键盘等待下载完毕!");
            evt.getPlayer().sendMessage(ChatColor.AQUA + "提示 >> 材质包下载传输中,请双手离开键盘等待下载完毕!");
            evt.getPlayer().sendMessage(ChatColor.AQUA + "提示 >> 材质包下载传输中,请双手离开键盘等待下载完毕!");
        }
    }


}
