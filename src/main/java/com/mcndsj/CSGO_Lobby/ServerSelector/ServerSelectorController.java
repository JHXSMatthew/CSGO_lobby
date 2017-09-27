package com.mcndsj.CSGO_Lobby.ServerSelector;

import com.mcndsj.CSGO_Lobby.CSGO_Lobby;
import com.mcndsj.CSGO_Lobby.Listeners.PlayerListener;
import com.mcndsj.LobbyServerSelector.LobbyServerSelector;

/**
 * Created by Matthew on 2016/4/18.
 */
public class ServerSelectorController {
    public ServerSelectorController(){
        SignClickCSGO query = new SignClickCSGO();
        LobbyServerSelector.getInstance().getApi().register("csgo_bomb","爆破模式",query);
        CSGO_Lobby.get().getServer().getPluginManager().registerEvents(new PlayerListener(query), CSGO_Lobby.get());
    }
}
