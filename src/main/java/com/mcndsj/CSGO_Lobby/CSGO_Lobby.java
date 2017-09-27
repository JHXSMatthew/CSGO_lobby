package com.mcndsj.CSGO_Lobby;

import com.mcndsj.CSGO_Lobby.commands.Reat;
import com.mcndsj.Lobby_Display.Lobby_Display;
import com.mcndsj.CSGO_Lobby.KitSystem.KitController;
import com.mcndsj.CSGO_Lobby.Listeners.PlayerListener;
import com.mcndsj.CSGO_Lobby.Scoreboard.CSGOCaller;
import com.mcndsj.CSGO_Lobby.ServerSelector.ServerSelectorController;
import com.mcndsj.lobby_Control.LobbyControlAPIs;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Matthew on 2016/4/16.
 */
public class CSGO_Lobby extends JavaPlugin{
    private ServerSelectorController control;
    private static CSGO_Lobby instance;
    private KitController kit;
    public void onEnable() {
        instance = this;
        Lobby_Display.getInstance().getApi().registerBoardCaller(new CSGOCaller());
        control = new ServerSelectorController();
        //kit = new KitController();


       //getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        LobbyControlAPIs.registerRestartPort(Config.startingPort);
        getServer().getPluginCommand("reat").setExecutor(new Reat());
    }

    public static CSGO_Lobby get(){
        return instance;
    }
}
