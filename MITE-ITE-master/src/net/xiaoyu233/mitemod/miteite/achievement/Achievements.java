package net.xiaoyu233.mitemod.miteite.achievement;

import net.minecraft.Achievement;
import net.minecraft.AchievementList;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.trans.entity.EntityBatTrans;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class Achievements {

    public static Achievement vibraniumIngot;
    public static Achievement wearAllVibraniumPlateArmor;
    public static Achievement vibraniumAnvil;
    public static Achievement vibraniumFurnace;

    public static Achievement openShop;
    public static Achievement rideBat;
    public static Achievement spawnBlock;
    public static Achievement gemSettingBlock;

    public static Achievement ringKillerCopper;
    public static Achievement itemDynamicCoreIron;
    public static Achievement killZombieBoss;
    public static Achievement fishFortune;
    public static void registerAchievements(){
        vibraniumIngot = new Achievement(getNextAchievementID(),"vibraniumIngot",-2,17, Items.VIBRANIUM_INGOT, AchievementList.adamantiumIngot).registerAchievement();
        wearAllVibraniumPlateArmor = new Achievement(getNextAchievementID(),"vibraniumArmor",12,1,Items.VIBRANIUM_CHESTPLATE,AchievementList.wearAllAdamantiumPlateArmor).setSpecial().registerAchievement();
        vibraniumAnvil = new Achievement(getNextAchievementID(),"vibraniumAnvil",0,17, Blocks.anvilVibranium, vibraniumIngot).setSpecial().registerAchievement();
        vibraniumFurnace = new Achievement(getNextAchievementID(),"vibraniumFurnace",-4,17,Blocks.furnaceVibraniumIdle,vibraniumIngot).setSpecial().registerAchievement();

        openShop = (new Achievement(getNextAchievementID(), "openShop", -4, 2, new ItemStack(Blocks.blockColorful, 1, 1), AchievementList.openInventory)).setIndependent().registerAchievement();
        rideBat = new Achievement(getNextAchievementID(),"rideBat",9,-5, Item.saddle, AchievementList.flyPig).setSpecial().registerAchievement();
        fishFortune = new Achievement(getNextAchievementID(),"fishFortune",12,3, Item.fishingRodIron, AchievementList.fishingRod).registerAchievement();
        spawnBlock = new Achievement(getNextAchievementID(),"spawnBlock",-5,7, Blocks.blockSpawn, AchievementList.diamonds).setFlipped().registerAchievement();
        gemSettingBlock = new Achievement(getNextAchievementID(),"gemSettingBlock",1,5, Blocks.gemSetting, AchievementList.acquireIron).registerAchievement();
        ringKillerCopper = new Achievement(getNextAchievementID(),"ringKillerCopper",-2,3, Items.ringKillerCopper, AchievementList.emeralds).registerAchievement();
        itemDynamicCoreIron = new Achievement(getNextAchievementID(),"itemDynamicCoreIron",3,3, Items.itemDynamicCoreIron, AchievementList.acquireIron).registerAchievement();
        killZombieBoss = new Achievement(getNextAchievementID(),"killZombieBoss",0,11, Items.VIBRANIUM_WAR_HAMMER, AchievementList.portal).registerAchievement();
    }
    private static int getNextAchievementID(){
        return Constant.nextAchievementID++;
    }
}
