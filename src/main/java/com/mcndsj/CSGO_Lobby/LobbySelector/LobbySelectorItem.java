package com.mcndsj.CSGO_Lobby.LobbySelector;

import com.mcndsj.lobby_Gui.Items.AbstractItem;
import com.mcndsj.lobby_Gui.Utils.ItemFactory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Matthew on 2016/4/17.
 */
public class LobbySelectorItem extends AbstractItem{
    private static ItemStack item = ItemFactory.create(Material.COMPASS, (byte)0, ChatColor.GOLD + "大厅-Menu", "右键打开菜单." ,"Right Click to open menu");

    public LobbySelectorItem() {
        super("选择大厅",45 ,Material.COMPASS, item.getItemMeta(),2);
        // TODO Auto-generated constructor stub
    }
}
