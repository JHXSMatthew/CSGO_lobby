package com.mcndsj.CSGO_Lobby.ServerSelector;

import com.mcndsj.LobbyServerSelector.Api.SignClickQuery;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 27/06/2016.
 */
public class SignClickCSGO implements SignClickQuery {

    private List<String> allow = new ArrayList<String>();
    

    @Override
    public boolean allowJoin(Player player) {
       if(allow.contains(player.getName())){
           return true;
       }else{
           player.sendMessage(ChatColor.AQUA + "提示 >> 您未确认使用材质包,请在大厅内等待材质包加载完毕!!" );
           sendTextdownloadComponent(player);
           return false;
       }
    }

    public void add(String s){
        if(!allow.contains(s)){
            allow.add(s);
        }
    }

    public void remove(String s){
        allow.remove(s);
    }

    public void sendTextdownloadComponent(Player p){
        TextComponent component = new TextComponent();
        component.setText("如果自动下载失败,请点击下载  →→下载材质包←←");
        component.setBold(true);
        component.setUnderlined(true);
        component.setColor(net.md_5.bungee.api.ChatColor.RED);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/reat"));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("点击接收材质包").create()));
        p.spigot().sendMessage(component);
    }
}
