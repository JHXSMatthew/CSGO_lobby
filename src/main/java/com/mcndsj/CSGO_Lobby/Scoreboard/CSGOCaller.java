package com.mcndsj.CSGO_Lobby.Scoreboard;

import com.mcndsj.Lobby_Display.API.BoardCaller;
import com.mcndsj.Lobby_Display.API.BoardRep;
import com.mcndsj.Lobby_Display.API.EntryContainer;
import com.mcndsj.Lobby_Display.Animations.WhiteAndGold;
import com.mcndsj.Lobby_Display.Scoreboard.BoardEntity;
import com.mcndsj.Lobby_Display.Scoreboard.BoardEntityStatsCallBack;
import com.mcndsj.Lobby_Statistics.Cache.DataType;
import com.mcndsj.Lobby_Statistics.lobby_Statistics;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Matthew on 2016/4/17.
 */
public class CSGOCaller implements BoardCaller {
    BoardRep rep = null;

    public CSGOCaller(){
        Collection c = new HashSet<EntryContainer>();
        c.add(new EntryContainer(0, "www.mcndsj.com",null,false));
        c.add(new EntryContainer(1, "  ",null,false));
        c.add(new EntryContainer(2, "爆头: $1",null,true));
        c.add(new EntryContainer(3, "击杀: $1",null,true));
        c.add(new EntryContainer(4, " ",null,false));
        c.add(new EntryContainer(5, "胜场: $1",null,true));
        c.add(new EntryContainer(6, "场次: $1",null,true));
        c.add(new EntryContainer(7, "   ",null,false));

        rep = new BoardRep("CS:GO",new WhiteAndGold("CS:GO"),c);
    }

    public BoardRep getRep() {
        return rep;
    }

    public void newBoadEntryQuery(BoardEntity boardEntity, EntryContainer entryContainer) {
        String[] array;
        switch(entryContainer.index){
            case 2:
                array = new String[1];
                array[0] ="Headshots";
                break;
            case 3:
                array = new String[1];
                array[0] ="Kills";
                break;
            case 5:
                array = new String[1];
                array[0] ="Wins";
                break;
            case 6:
                array = new String[1];
                array[0] ="Games";
                break;
            default:
                boardEntity.setEntry(entryContainer.index,entryContainer.entry);
                return;
        }
        lobby_Statistics.getInstance().getApi().getData(boardEntity.getPlayer().getName(),
                DataType.CSGO,
                new BoardEntityStatsCallBack(array,entryContainer,boardEntity));
    }

}
