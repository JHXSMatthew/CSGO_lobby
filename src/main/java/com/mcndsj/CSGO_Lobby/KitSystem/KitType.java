package com.mcndsj.CSGO_Lobby.KitSystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import com.mcndsj.CSGO_Lobby.Utils.ItemFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 8/06/2016.
 */
public enum KitType {
    cook(200,1200,ItemFactory.create(Material.PORK,(byte)0, ChatColor.WHITE + "厨师",
            "衣食无忧,天下太平."),"cook",SkillType.Cook_1,SkillType.Cook_4,SkillType.Cook_6,SkillType.Cook_8),
    archer(200,1200,ItemFactory.create(Material.BOW,(byte)0,ChatColor.WHITE + "弓箭手",
            "以睹余物，皆丘山也."),"archer",SkillType.Archer_1,SkillType.Archer_4,SkillType.Archer_6,SkillType.Archer_8),
    assassin(200,1200,ItemFactory.create(Material.DIAMOND_SWORD,(byte)0,ChatColor.WHITE + "刺客",
            "十步杀一人,千里不留情."),"assassin",SkillType.Assassin_1,SkillType.Assassin_4,SkillType.Assassin_6,SkillType.Assassin_8),
    enderman(200,1200,ItemFactory.create(Material.ENDER_PORTAL_FRAME,(byte)0,ChatColor.WHITE + "末影使者",
            "掌控世间粒子的运动规则."),"enderman",SkillType.Enderman_1,SkillType.Enderman_4,SkillType.Enderman_6,SkillType.Enderman_8),
    bloodSeeker(200,1200,ItemFactory.create(Material.RED_ROSE,(byte)0, ChatColor.WHITE + "嗜血者",
            "遇人杀人，遇佛弑佛."),"bloodSeeker",SkillType.BloodSeeker_1,SkillType.BloodSeeker_4,SkillType.BloodSeeker_6,SkillType.BloodSeeker_8);

    private int price;
    private int modifier;
    private ItemStack item;
    private SkillType[] types;
    private String dbName;

    KitType(int i, int i1,ItemStack item,String dbName,SkillType ...types) {
        this.price = i;
        this.modifier = i1;
        this.types = types;
        this.dbName = dbName;
        this.item = item;
    }

    public String getDbName(){
        return dbName;
    }

    public String getDisplayName(){
        return ChatColor.stripColor(item.getItemMeta().getDisplayName());
    }

    public String getDisplayNameWithColor(){
        return item.getItemMeta().getDisplayName();
    }


    public ItemStack getItem(){
        return item.clone();
    }

    public int getMaxLevel(){
        return types[types.length - 1].getUnlockLevel() + SkillType.MAX_STEP;
    }

    public List<String> getDescription(int level){
        ArrayList<String> string = new ArrayList<>();
        for(SkillType basic : types){
            if(basic.getUnlockLevel() > level){
                continue;
            }
            basic.getDescription(level,string);
        }
        return string;
    }

    public String getLastSkillDes(int level){
        SkillType most =types[0];
        for(SkillType skill : types){
            if (level >= skill.getUnlockLevel()  && most.getUnlockLevel() < skill.getUnlockLevel()) {
                most = skill;
            }
        }
        return most.getDescriptionOneline(level);
    }

    public int getPrice(int level){
        int finalPrice = price;
        for(int i = 0 ; i < level ; i ++)
            finalPrice *= 2;
        if(level % 4 == 0 || level == 0 )
            finalPrice = finalPrice + modifier * ((level +1 )/4 + 1) * ((level +1)/4 + 1) ;
        return finalPrice;
    }

}
