package com.mcndsj.CSGO_Lobby.KitSystem;

import com.mcndsj.Lobby_Statistics.Cache.DataType;
import com.mcndsj.Lobby_Statistics.lobby_Statistics;
import com.mcndsj.CSGO_Lobby.Utils.ItemFactory;
import com.mcndsj.CSGO_Lobby.Utils.WallSQLUtils;
import com.mcndsj.CSGO_Lobby.CSGO_Lobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Matthew on 8/06/2016.
 */
public class KitController implements Listener {
    private static String name = "职业装配";
    private static String itemName = ChatColor.GREEN + "职业-Kits";

    public KitController(){
        CSGO_Lobby.get().getServer().getPluginManager().registerEvents(this, CSGO_Lobby.get());
    }

    ExecutorService executorService  = Executors.newSingleThreadExecutor();

    public void openInv(final Player player){
        if(player == null){
            return;
        }
        if(!player.isOnline()){
            return;
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<KitType,Integer> levelMap = WallSQLUtils.getKitMap(player);
                if(levelMap == null)
                    return;

                Inventory inv = Bukkit.createInventory(null,54,name);
                for(KitType type : KitType.values()){
                    if(!levelMap.containsKey(type) || levelMap.get(type) <= 0){
                        inv.addItem(fillInfo(type.getItem(),0, type.getMaxLevel(),type.getPrice(1), null,type.getDescription(type.getMaxLevel())));
                    }else if(levelMap.get(type) >= type.getMaxLevel()){
                        inv.addItem(fillInfo(type.getItem(),levelMap.get(type), type.getMaxLevel(),-1,type.getDescription(levelMap.get(type)),type.getDescription(type.getMaxLevel())));
                    }else {
                        inv.addItem(fillInfo(type.getItem(),levelMap.get(type), type.getMaxLevel(),type.getPrice(levelMap.get(type) + 1),type.getDescription(levelMap.get(type)),type.getDescription(type.getMaxLevel())));
                    }
                }
                player.openInventory(inv);
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt){
        evt.getPlayer().getInventory().setItem(1, ItemFactory.create(Material.EMERALD,(byte)0,itemName  , "右击查看职业信息."));
    }

    @EventHandler
    public void onOpenInv(PlayerInteractEvent evt){
        if(evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(evt.getItem() != null && evt.getItem().getType() != Material.AIR){
                if(evt.getItem().getType().equals(Material.EMERALD) && evt.getItem().getItemMeta().hasDisplayName() && evt.getItem().getItemMeta().getDisplayName().equals(itemName)){
                        openInv(evt.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent evt){
        if(evt.getCurrentItem() == null || evt.getCurrentItem().getType() == Material.AIR){
            return;
        }
        if(evt.getClickedInventory().getTitle() == null || !evt.getClickedInventory().getTitle().equals(name)){
            return;
        }

        evt.setCancelled(true);
        for(KitType type : KitType.values()){
            if(type.getDisplayNameWithColor().equals(evt.getCurrentItem().getItemMeta().getDisplayName())){
                if(!KitCallBack.isCallingBack(evt.getWhoClicked().getName()))
                    lobby_Statistics.getInstance().getApi().getData(evt.getWhoClicked().getName(), DataType.walls, new KitCallBack(evt.getWhoClicked().getName(), type));
                break;

            }
        }
        evt.getWhoClicked().closeInventory();
    }

    private ItemStack fillInfo(ItemStack source, int current, int max,int price, List<String> currentDes,List<String> fullStatus){
        ItemMeta meta = source.getItemMeta();
        List<String> lore = meta.getLore();
        if(price == -1){
            lore.add(" ");
            lore.add(ChatColor.YELLOW + "当前等级: " + ChatColor.GREEN + current);
            lore.add(ChatColor.YELLOW + "最高等级: " + ChatColor.GREEN + max);
            lore.add(ChatColor.YELLOW + "当前技能表:");
            lore.addAll(currentDes);
            lore.add("   ");
            lore.add(ChatColor.RED + ChatColor.BOLD.toString()+ "登峰造极");
      }else{
            if(current == 0 || currentDes == null){
                lore.add(" ");
                lore.add(ChatColor.YELLOW + "当前等级: " + ChatColor.GREEN + current);
                lore.add(ChatColor.YELLOW + "最高等级: "  +ChatColor.GREEN + max);
                lore.add(ChatColor.YELLOW + "购买价格: "  +ChatColor.GREEN + price);
                lore.add(ChatColor.RED + "未拥有");;
            }else{
                lore.add(" ");
                lore.add(ChatColor.YELLOW + "当前等级: " + ChatColor.GREEN + current);
                lore.add(ChatColor.YELLOW + "最高等级: " + ChatColor.GREEN + max);
                lore.add(ChatColor.YELLOW + "购买价格: "  +ChatColor.GREEN + price);
                lore.add(ChatColor.YELLOW + "当前技能表:");
                lore.addAll(currentDes);
            }
            lore.add("  ");
            lore.add(ChatColor.YELLOW + "满级技能表:");
            lore.addAll(fullStatus);

        }
        meta.setLore(lore);
        source.setItemMeta(meta);
        return source;
    }
}
