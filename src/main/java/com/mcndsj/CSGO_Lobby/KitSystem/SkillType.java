package com.mcndsj.CSGO_Lobby.KitSystem;

import com.mcndsj.CSGO_Lobby.Utils.StringUtils;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * Created by Matthew on 8/06/2016.
 */
public enum SkillType {

    // ------------------------- Cook --------------------------------
    Cook_1(1,"小厨","开局获得@level * 3@块面包和@level@个蛋糕."),
    Cook_4(4,"厨艺精通","制作食物时有30%的概率获取@level@倍的食物."),
    Cook_6(6,"神农","使用锄右击作物可以直接催熟,消耗@100 / level@%的耐久度."),
    Cook_8(8,"食神","在食用食物时有@10 * level@%概率恢复周围@level@数友军的等量饱食度"),

    // ------------------------- Archer --------------------------------
    Archer_1(1,"弓不离手","开局获取一把单弓和@level * 5@个弓箭."),
    Archer_4(4,"爆破射手","开局获得@level + 0@个TNT,当背包内含有TNT时,弓箭附带爆炸效果并消耗TNT."),
    Archer_6(6,"灵魂一击","使用远程武器造成的伤害将提升@10 * level@%."),
    Archer_8(8,"涂毒","弓箭有@5 * level@%的概率附加中毒效果"),

    // -------------------------  Assassin --------------------------------
    Assassin_1(1,"急速","击杀敌人后速度II 状态持续@level@秒."),
    Assassin_4(4,"背刺","在身后攻击敌人时有@10 * level@%的概率使其进入反胃状态.."),
    Assassin_6(6,"致命一击","发动致命一击时会额外造成@8 * level@%的伤害."),
    Assassin_8(8,"盗贼天赋","右键<隐身斗篷>后进入@level@秒隐身效果，造成伤害隐身消失"),

    // -------------------------  Enderman --------------------------------
    Enderman_1(1,"粒子破碎","获得物品右击记录当前位置在@1 + level * 2@秒内再次右键可回到记录位置,在回到该位置后2秒内免疫箭的伤害."),
    Enderman_4(4,"粒子护甲","如果你没有穿胸甲，且在7秒内没有受到伤害，那么你会免疫接下来的@level@次伤害."),
    Enderman_6(6,"粒子武器","攻击时如果你使用铁剑有@5 + 3 * level@%概率使敌人定身."),
    Enderman_8(8,"粒子感知","获得指南针，右击后将指向随机一个敌人的位置."),

    // -------------------------  BloodSeeker --------------------------------
    BloodSeeker_1(1,"嗜血狂魔","杀死任意实体时恢复@level * 2@点生命值."),
    BloodSeeker_4(4,"吸血之斧","使用石斧武器攻击时可以恢复@5 * level@%的流失生命值,但不会造成伤害."),
    BloodSeeker_6(6,"撕裂伤口","攻击时有@10 * level@%的概率附加流血效果 持续1秒."),
    BloodSeeker_8(8,"秒杀","敌人生命值在@2 + level@以下时，造成任意伤害将直接秒杀敌人.");


    public static int MAX_STEP = 3;

    private int unlockLevel;
    private String des;
    private String skillName;

    SkillType(int unlock_level,String skillName,String des){
        this.des = "- " + des;
        this.skillName = skillName;
        this.unlockLevel = unlock_level;
    }


    public int getUnlockLevel(){
        return unlockLevel;
    }

    public String getSkillName(){
        return skillName;
    }



    public String getDescriptionOneline(int level){
        return ChatColor.YELLOW + getSkillName() + ": " + ChatColor.GOLD + StringUtils.calSkillPlaceHolders(des,level - unlockLevel >= MAX_STEP ? MAX_STEP : (level - unlockLevel+1));
    }

    public void getDescription(int level,List<String> list){
        String whole = StringUtils.calSkillPlaceHolders(des,level - unlockLevel >= MAX_STEP ? MAX_STEP : (level - unlockLevel+1));
        if(whole.length() < 23){
            list.add( ChatColor.GOLD  +   whole);
        }else{
            boolean isFirst = true;
            int count = 0;
            int last = 0;
            boolean shouldSet = false;
            while(count < whole.length() ){
                if ((count % 23 == 0 && count != 0) || count == whole.length() - 1 || shouldSet) {
                    if(whole.charAt(count) == ChatColor.COLOR_CHAR ||whole.charAt(count - 1)  == ChatColor.COLOR_CHAR  ){
                        shouldSet = true;

                    }else {
                        shouldSet = false;
                        if (isFirst) {
                            list.add(ChatColor.GOLD + whole.substring(last, count));
                            last = count;
                            isFirst = false;
                        } else {
                            list.add(ChatColor.GOLD + " " + whole.substring(last, count));
                            last = count;
                        }
                    }
                }
                count ++;
            }
        }
    }


}
