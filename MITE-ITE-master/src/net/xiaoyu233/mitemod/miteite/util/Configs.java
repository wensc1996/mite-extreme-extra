package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Configs {
    public static Map <String, ConfigItem> wenscMap = new HashMap<>();

    public static class ConfigItem<T>{
        public String ConfigKey;
        public T ConfigValue;
        public T min;
        public T max;
        public boolean isNeedCompare = false;
        public String ConfigComment;
        ConfigItem(String key, T value, String comment){
            this.ConfigKey = key;
            this.ConfigValue = value;
            this.ConfigComment = comment;
        }
        ConfigItem(String key, T value, String comment, T min, T max){
            this.ConfigKey = key;
            this.ConfigValue = value;
            this.isNeedCompare = true;
            this.min = min;
            this.max = max;
            this.ConfigComment = comment + " [范围："+ min + "-" + max +"]";
        }
        public void setConfigValue(T configValue) {
            ConfigValue = configValue;
        }
        public T getConfigValue() {
            return this.ConfigValue;
        }
    }

    public static class wenscConfig {
        public static ConfigItem <Boolean> BlnGravel = new ConfigItem("BlnGravel", true, "是否增加燧石概率");
        public static ConfigItem <Boolean> isOpenStrongBoxBreakRecord = new ConfigItem("isOpenStrongBoxBreakRecord", true, "是否开启私人箱子破坏记录");
        public static ConfigItem <Boolean> isRecipeGATorch = new ConfigItem("isRecipeGATorch", true, "是否有一捆火把配方");
        public static ConfigItem <Boolean> isRecipeRingKiller = new ConfigItem("isRecipeRingKiller", true, "是否有魔灵刃配方");
        public static ConfigItem <Float> inWallDamageForPlayer = new ConfigItem("inWallDamageForPlayer", 10.0F, "玩家墙内窒息伤害(小数)", 0f ,100f);
        public static ConfigItem <Double> steppedMobDamageFactor = new ConfigItem("steppedMobDamageFactor", 1d, "渐进伤害每次增幅点数基础值(小数)", 0d ,100d);
        public static ConfigItem <Double> steppedMobDamageProgressMax = new ConfigItem("steppedMobDamageProgressMax", 4d, "怪物对玩家渐进伤害增幅最大值(小数)", 0d,100d);
        public static ConfigItem <Integer> steppedMobDamageProgressIncreaseDay = new ConfigItem("steppedMobDamageProgressIncreaseDay_v0.1.0", 50, "渐进伤害每次增幅每增加一点所需天数(整数)", 0 ,500);
        public static ConfigItem <Double> steppedPlayerDamageProgress = new ConfigItem("steppedPlayerDamageProgress", 2d, "玩家对怪物渐进伤害(不计附魔)最大值", 0d ,100d);
        public static ConfigItem <Double> steppedPlayerDamageIncreasePerLvl = new ConfigItem("steppedPlayerDamageIncreasePerLvl", 0.1d, "玩家对怪物渐进伤害每级加成", 0d ,10d);
        public static ConfigItem <Integer> fishingXp = new ConfigItem("fishingXp", 10, "钓鱼经验(整数)", 0 ,10000);
        public static ConfigItem <Integer> inRainDebuffTime = new ConfigItem("inRainDebuffTime", 3600, "雨中获得负面效果所需时长(整数)", 0 ,1000000);
        public static ConfigItem <Boolean> firstDayLongerDayTime = new ConfigItem("firstDayLongerDayTime", true, "加长第一天时间(开关)");
        public static ConfigItem <Integer> playerDefenseCooldown = new ConfigItem("playerDefenseCooldown", 160, "玩家格挡冷却(tick)", 0 ,1000000);
        public static ConfigItem <Integer> playerDefenceMaxTime = new ConfigItem("playerDefenceMaxTime", 40, "玩家格挡最大时间(tick)", 0 ,1000000);
        public static ConfigItem <Boolean> playerDisarmPlayer = new ConfigItem("playerDisarmPlayer", true, "玩家缴械玩家");
        public static ConfigItem <Double> bloodMoonMaxHostileFraction = new ConfigItem("bloodMoonMaxHostileFraction", 128d, "血月最大刷怪数量系数(小数)", 0d ,500d);
        public static ConfigItem <Boolean> annihilationSkeletonSpawnInLight = new ConfigItem("annihilationSkeletonSpawnInLight", false, "主世界湮灭骷髅在光照出生成");
        public static ConfigItem <Integer> ancientBoneLordSpawnLimitDay = new ConfigItem("ancientBoneLordSpawnLimitDay", 192, "主世界生成远古骷髅公爵所需天数(整数)", 0 ,500);
        public static ConfigItem <Integer> wanderingWitchSpawnChanceOverworld = new ConfigItem("wanderingWitchSpawnChanceOverworld", 33, "主世界流浪女巫生成概率百分比(整数)", 0 ,100);
        public static ConfigItem <Integer> wanderingWitchSpawnLimitDayOverworld = new ConfigItem("wanderingWitchSpawnLimitDayOverworld", 64, "主世界流浪女巫生成天数限制(整数)", 0 ,500);
        public static ConfigItem <Integer> wanderingWitchSpawnLimitDayOther = new ConfigItem("wanderingWitchSpawnLimitDayOther", 64, "地狱及地底世界流浪女巫生成天数限制(整数)", 0 ,500);
        public static ConfigItem <Integer> wanderingWitchSpawnChanceUnderworld = new ConfigItem("wanderingWitchSpawnChanceUnderworld", 50, "地底世界流浪女巫生成概率百分比(整数)", 0 ,100);
        public static ConfigItem <Integer> mobMaxSpawnCountIncreasePerDay = new ConfigItem("mobMaxSpawnCountIncreasePerDay", 1, "每天刷怪数量上限增量(整数)", 0 ,100);
        public static ConfigItem <Boolean> underworldRandomTeleport = new ConfigItem("underworldRandomTeleport", true, "地底世界随机传送(开关)");
        public static ConfigItem <Integer> underworldRandomTeleportTimeNew = new ConfigItem("underworldRandomTeleportTimeNew", 132000, "地底世界随机传送时间(tick)", 0 ,1000000);
        public static ConfigItem <Boolean> underworldDebuff = new ConfigItem("underworldDebuff", true, "地底世界负面效果(开关)");
        public static ConfigItem <Integer> underworldDebuffPeriod1 = new ConfigItem("underworldDebuffPeriod1", 72000, "地底世界负面效果第一阶段触发时间(tick)", 0 ,1000000);
        public static ConfigItem <Integer> underworldDebuffPeriod2 = new ConfigItem("underworldDebuffPeriod2", 108000, "地底世界负面效果第二阶段触发时间(tick)", 0 ,1000000);
        public static ConfigItem <Boolean> netherrackDamage = new ConfigItem("netherrackDamage", true, "地狱烫脚(开关)");
        public static ConfigItem <Integer> netherrackDamageLimitDay = new ConfigItem("netherrackDamageLimitDay", 96, "地狱烫脚所需天数(整数)", 0 ,500);
        public static ConfigItem <Boolean> netherDebuff = new ConfigItem("netherDebuff", true, "地狱负面效果(开关)");
        public static ConfigItem <Integer> netherDebuffTime = new ConfigItem("netherDebuffTime", 72000, "地狱负面效果触发时间(tick)", 0 ,1000000);
        public static ConfigItem <Integer> quartzMaxExpLevel = new ConfigItem("quartzMaxExpLevel", 55, "石英可提供经验最大等级", 0 ,1000);
        public static ConfigItem <Integer> fancyRedExp = new ConfigItem("fancyRedExp", 500, "红钻经验", 0 ,10000);
        public static ConfigItem <Integer> diamondExp = new ConfigItem("diamondExp", 400, "钻石经验", 0 ,10000);
        public static ConfigItem <Integer> emeraldExp = new ConfigItem("emeraldExp", 300, "绿宝石经验", 0 ,10000);
        public static ConfigItem <Integer> quartzExp = new ConfigItem("quartzExp", 35, "石英经验", 0 ,10000);
        public static ConfigItem <Integer> goldenAppleEatTime = new ConfigItem("goldenAppleEatTime", 10, "金苹果使用耗时（tick）", 0 ,1000000);
        public static ConfigItem <Integer> enchantedGoldenAppleEatTime = new ConfigItem("enchantedGoldenAppleEatTime", 10, "附魔金苹果使用耗时（tick）", 0 ,1000000);
        public static ConfigItem <Integer> critEnchantmentChanceBoostPerLvl = new ConfigItem("critEnchantmentChanceBoostPerLvl", 2, "暴击附魔每级暴击几率", 0 ,100);
        public static ConfigItem <Float> critEnchantmentDamageBoostPerLvl = new ConfigItem("critEnchantmentDamageBoostPerLvl", 0.4F, "暴击附魔每级额外伤害", 0f ,100f);
        public static ConfigItem <Integer> emergencyCooldown = new ConfigItem("emergencyCooldown", 120000, "紧急守备冷却时间（tick）", 0 ,1000000);
        public static ConfigItem <Double> emergencyCooldownReduceEveryArmor = new ConfigItem("emergencyCooldownReduceEveryArmor", 0.2d, "紧急守备每件装备冷却减免百分比", 0d,1d);
        public static ConfigItem <Integer> emergencyHealthRecoverAmount = new ConfigItem("emergencyHealthRecoverAmount", 10, "紧急守备触发后恢复至的血量", 1,100);
        public static ConfigItem <Double> conquerorDamageBoostPerLvl = new ConfigItem("conquerorDamageBoostPerLvl", 1d, "征服者每级额外伤害", 0d ,100d);
        public static ConfigItem <Float> beheadingChanceBoostPerLvl = new ConfigItem("beheadingChanceBoostPerLvl", 0.05f, "斩首每级头颅额外掉落几率", 0f ,1f);
        public static ConfigItem <Double> allProtectionVDefenceFraction = new ConfigItem("allProtectionVDefenceFraction", 0.1d, "全套保护5伤害减免", 0d ,1d);
        public static ConfigItem <Boolean> mobSpawnerSpeedUpWithPlayers = new ConfigItem("mobSpawnerSpeedUpWithPlayers", true, "刷怪笼在旁边有玩家时加速生怪");
        public static ConfigItem <Boolean> isSpawnExchanger = new ConfigItem("isSpawnExchanger", true, "是否生成转移骷髅");
        public static ConfigItem <Boolean> boneLordTweak = new ConfigItem("boneLordTweak", true, "骷髅领主增强");
        public static ConfigItem <Boolean> skeletonTripleShot = new ConfigItem("skeletonTripleShot", true, "三发骷髅");
        public static ConfigItem <Boolean> compressedSkeleton = new ConfigItem("compressedSkeleton", true, "分裂骷髅");
        public static ConfigItem <Integer> compressedSkeletonCrackStandTime = new ConfigItem("compressedSkeletonCrackStandTime", 20, "分裂骷髅分裂后停滞时间(tick)", 0 ,1000000);
        public static ConfigItem <Integer> compressedSkeletonExpandCount = new ConfigItem("compressedSkeletonExpandCount", 4, "分裂骷髅分裂数量",0, 10);
        public static ConfigItem <Boolean> skeletonForceMeleeAttack = new ConfigItem("skeletonForceMeleeAttack", true, "强制骷髅近战");
        public static ConfigItem <Float> boneLordAndLongdeadChangeWeaponChance = new ConfigItem("boneLordAndLongdeadChangeWeaponChance", 0.25f, "古尸和骷髅领主会切换武器概率",0f, 1f);
        public static ConfigItem <Boolean> batPoisonAttack = new ConfigItem("batPoisonAttack", true, "吸血蝙蝠攻击使玩家中毒");
        public static ConfigItem <Boolean> infernalCreeperBoost = new ConfigItem("infernalCreeperBoost", true, "增强地狱爬行者");
        public static ConfigItem <Boolean> zombiePigmanUseBow = new ConfigItem("zombiePigmanUseBow", true, "猪人带弓");
        public static ConfigItem <Double> zombiePigmanBoostChance = new ConfigItem("zombiePigmanBoostChance", 0.025d, "精英猪人生成概率",0d, 1d);
        public static ConfigItem <Boolean> zombiePigmanAttackAnimails = new ConfigItem("zombiePigmanAttackAnimails", false, "僵尸猪人攻击动物");
        public static ConfigItem <Boolean> wolvesAttackPlayersWithMeat = new ConfigItem("wolvesAttackPlayersWithMeat", true, "狼攻击快捷栏有肉的玩家");
        public static ConfigItem <Integer> villagerWoolToEmeraldShardCount = new ConfigItem("villagerWoolToEmeraldShardCount", 4, "村民用羊毛换绿宝石碎片-羊毛所需数量", 1 ,8);
        public static ConfigItem <Integer> villagerWoolToEmeraldShardShardCount = new ConfigItem("villagerWoolToEmeraldShardShardCount", 1, "村民用羊毛换绿宝石碎片-绿宝石碎片获得数量", 1 ,64);
        public static ConfigItem <Boolean> canBoostIronGolem = new ConfigItem("canBoostIronGolem", true, "可以强化铁傀儡");
        public static ConfigItem <Boolean> mobDefense = new ConfigItem("mobDefense", true, "怪物格挡");
        public static ConfigItem <Boolean> mobDisarmWhenDefence = new ConfigItem("mobDisarmWhenDefence", true, "怪物格挡时若有缴械附魔有几率缴械");
        public static ConfigItem <Boolean> mobDisarmWhenAttack = new ConfigItem("mobDisarmWhenAttack", true, "怪物攻击时若有缴械附魔有几率缴械");
        public static ConfigItem <Boolean> mobAttackCauseFire = new ConfigItem("mobAttackCauseFire", true, "怪物有几率在手持装备攻击时使目标着火");
        public static ConfigItem <Integer> annihilationSkeletonDespawnTime = new ConfigItem("annihilationSkeletonDespawnTime", 3600, "湮灭骷髅被攻击后消失用时(tick)", 0 ,1000000);
        public static ConfigItem <Float> enderDragonAttackWitherChance = new ConfigItem("enderDragonAttackWitherChance", 0.2f, "末影龙受攻击后给予玩家凋零几率",0f, 1f);
        public static ConfigItem <Float> enderDragonImmuneToArrowHealthPercent = new ConfigItem("enderDragonImmuneToArrowHealthPercent", 0.3f, "末影龙免疫弓箭血量百分比",0f, 1f);
        public static ConfigItem <Boolean> enderDragonAttackSlowness = new ConfigItem("enderDragonAttackSlowness", true, "末影龙攻击玩家给予玩家缓慢效果");
        public static ConfigItem <Float> netherModAttackFireChance = new ConfigItem("netherModAttackFireChance", 0.5f, "地狱生物攻击火焰附加概率",0f, 1f);
        public static ConfigItem <Integer> slimeAttackDamageMultiplier = new ConfigItem("slimeAttackDamageMultiplier", 1, "绿色史莱姆伤害倍数(整数)", 0 ,100);
        public static ConfigItem <Float> slimeAttackDamageBonus = new ConfigItem("slimeAttackDamageBonus", 4f, "绿色史莱姆伤害加成", 0f ,100f);
        public static ConfigItem <Integer> giantZombieSpawnZombieCooldown = new ConfigItem("giantZombieSpawnZombieCooldown", 1200, "巨型僵尸生成僵尸间隔(tick)", 0 ,1000000);
        public static ConfigItem <Integer> animalIllToDeathTime = new ConfigItem("animalIllToDeathTime", 144000, "生物生病死亡时间(tick)", 0 ,1000000);
        public static ConfigItem <Integer> breedXpChicken = new ConfigItem("breedXpChicken", 8, "鸡繁殖获得经验", 0 ,10000);
        public static ConfigItem <Integer> breedXpSheep = new ConfigItem("breedXpSheep", 10, "羊繁殖获得经验", 0 ,10000);
        public static ConfigItem <Integer> breedXpPig = new ConfigItem("breedXpPig", 15, "猪繁殖获得经验", 0 ,10000);
        public static ConfigItem <Integer> breedXpCow = new ConfigItem("breedXpCow", 25, "牛繁殖获得经验", 0 ,10000);
        public static ConfigItem <Integer> emeraldFrequencyBigHills = new ConfigItem("emeraldFrequencyBigHills", 6, "山地绿宝石矿每区块最少生成个数", 0 ,100);
        public static ConfigItem <Boolean> overworldAdamantiteOre = new ConfigItem("overworldAdamantiteOre", false, "主世界生成艾德曼");
        public static ConfigItem <Integer> copperFrequencyOverworld = new ConfigItem("copperFrequencyOverworld_v0.0.5", 40, "主世界铜矿生成频率", 0 ,100);
        public static ConfigItem <Integer> silverFrequencyOverworld = new ConfigItem("silverFrequencyOverworld_v0.0.5", 20, "主世界银矿生成频率", 0 ,100);
        public static ConfigItem <Integer> ironFrequencyOverworld = new ConfigItem("ironFrequencyOverworld_v0.0.5", 30, "主世界铁矿生成频率", 0 ,100);
        public static ConfigItem <Integer> goldFrequencyOverworld = new ConfigItem("goldFrequencyOverworld_v0.0.5.3", 25, "主世界金矿生成频率", 0 ,100);
        public static ConfigItem <Integer> mithrilFrequencyOverworld = new ConfigItem("mithrilFrequencyOverworld_v0.0.5", 0, "主世界秘银矿生成频率", 0 ,100);
        public static ConfigItem <Integer> lapisFrequencyOverworld = new ConfigItem("lapisFrequencyOverworld", 4, "主世界青金石矿生成频率", 0 ,100);
        public static ConfigItem <Integer> diamondFrequencyOverworld = new ConfigItem("diamondFrequencyOverworld_v0.0.5", 0, "主世界钻石矿生成频率", 0 ,100);
        public static ConfigItem <Integer> adamantiumFrequencyOverworld = new ConfigItem("adamantiumFrequencyOverworld", 1, "主世界艾德曼矿生成频率", 0 ,100);
        public static ConfigItem <Integer> copperFrequencyUnderworld = new ConfigItem("copperFrequencyUnderworld_v0.0.5", 0, "地底世界铜矿生成频率", 0 ,100);
        public static ConfigItem <Integer> silverFrequencyUnderworld = new ConfigItem("silverFrequencyUnderworld_v0.0.5", 0, "地底世界银矿生成频率", 0 ,100);
        public static ConfigItem <Integer> ironFrequencyUnderworld = new ConfigItem("ironFrequencyUnderworld_v0.0.5", 0, "地底世界铁矿生成频率", 0 ,100);
        public static ConfigItem <Integer> goldFrequencyUnderworld = new ConfigItem("goldFrequencyUnderworld_v0.0.5", 0, "地底世界金矿生成频率", 0 ,100);
        public static ConfigItem <Integer> mithrilFrequencyUnderworld = new ConfigItem("mithrilFrequencyUnderworld_v0.0.5", 6, "地底世界秘银矿生成频率", 0 ,100);
        public static ConfigItem <Integer> lapisFrequencyUnderworld = new ConfigItem("lapisFrequencyUnderworld_v0.0.5", 0, "地底世界青金石矿生成频率", 0 ,100);
        public static ConfigItem <Integer> diamondFrequencyUnderworld = new ConfigItem("diamondFrequencyUnderworld_v0.0.5", 6, "地底世界钻石矿生成频率", 0 ,100);
        public static ConfigItem <Integer> fancyRedFrequencyUnderworld = new ConfigItem("fancyRedFrequencyUnderworld", 3, "地底世界红钻矿生成频率", 0 ,100);
        public static ConfigItem <Integer> adamantiumFrequencyUnderworld = new ConfigItem("adamantiumFrequencyUnderworld_v0.0.9", 1, "地底世界艾德曼矿生成频率", 0 ,100);
        public static ConfigItem <Integer> underworldMantleBlockOffset = new ConfigItem("underworldMantleBlockOffset_v0.0.2", 75, "地底世界地幔位置向上偏移", 0 ,140);
        public static ConfigItem <Integer> netherAdamantiumMaxCountPerChunk = new ConfigItem("netherAdamantiumMaxCountPerChunk", 2, "地狱艾德曼每个区块最大生成数量", 0 ,100);
        public static ConfigItem <Integer> netherAdamantiumMaxCountPerVein = new ConfigItem("netherAdamantiumMaxCountPerVein", 1, "地狱艾德曼每个矿脉最大矿物生成数量", 0 ,100);
        public static ConfigItem <Integer> healthBarXOffset = new ConfigItem("healthBarXOffset", 0, "营养条GUI横向偏移");
        public static ConfigItem <Integer> healthBarYOffset = new ConfigItem("healthBarYOffset", 0, "营养条GUI纵向偏移");
        public static ConfigItem <Boolean> logPlayersInteractWithPortal = new ConfigItem("logPlayersInteractWithPortal", true, "玩家通过传送门时发送警告(开关)");
        public static ConfigItem <Integer> underworldGateOpenDay = new ConfigItem("underworldGateOpenDay", 1, "地下世界传送门多少天可以打开(主世界天数)", 1 ,500);
        public static ConfigItem <Integer> netherGateOpenDay = new ConfigItem("netherGateOpenDay", 1, "地狱传送门多少天可以打开(主世界天数)", 1 ,500);
        public static ConfigItem <Boolean> isAfterDeathKeep = new ConfigItem("isAfterDeathKeep", false, "是否死亡不掉落");
        public static ConfigItem <Boolean> isCloseShop = new ConfigItem("isCloseShop", false, "关闭商店系统");
        public static ConfigItem <Float> plantGrowthRate = new ConfigItem("plantGrowthRate_v0.0.9", 0.5F, "作物生长速度倍率",0f,1f);
        public static ConfigItem <Integer> whichDayGenVillage = new ConfigItem("whichDayGenVillage", 60, "多少天生成村庄", 1 ,500);
        public static ConfigItem <Integer> maxLevelLimit = new ConfigItem("maxLevelLimit", 300, "玩家最大限制等级", 0 ,1000);
        public static ConfigItem <Integer> enhancePerLvlCostExp = new ConfigItem("enhancePerLvlCostExp", 500, "附魔书附魔每增加一级消耗多少经验", 0 ,10000);

        public static ConfigItem <Double> zombieBossMaxHealth = new ConfigItem("zombieBossMaxHealth", 1000d, "僵尸BOSS最大血量", 1d ,10000d);
        public static ConfigItem <Double> zombieBossBaseDamage = new ConfigItem("zombieBossBaseDamage", 12d, "僵尸BOSS基础攻击伤害", 1d ,100d);
        public static ConfigItem <Float> zombieBossSpawnPercent = new ConfigItem("zombieBossSpawnPercent", 0.5f, "挖掘刷怪笼多大几率刷出僵尸BOSS", 0f,1f);

        public static ConfigItem <Integer> creeperFuseTime = new ConfigItem("creeperFuseTime", 30, "苦力怕蓄力时间(tick)", 10,1000);

//        public static ConfigItem <String> md5String = new ConfigItem("md5String", new lh("wensc").a("busy"), "MD5");

    }

    public static void loadConfigs(){

        wenscMap.put("BlnGravel", wenscConfig.BlnGravel);
        wenscMap.put("isOpenStrongBoxBreakRecord", wenscConfig.isOpenStrongBoxBreakRecord);
        wenscMap.put("isRecipeRingKiller", wenscConfig.isRecipeRingKiller);
        wenscMap.put("isRecipeGATorch", wenscConfig.isRecipeGATorch);
        wenscMap.put("inWallDamageForPlayer", wenscConfig.inWallDamageForPlayer);
        wenscMap.put("steppedMobDamageFactor", wenscConfig.steppedMobDamageFactor);
        wenscMap.put("steppedMobDamageProgressMax", wenscConfig.steppedMobDamageProgressMax);
        wenscMap.put("steppedMobDamageProgressIncreaseDay_v0.1.0", wenscConfig.steppedMobDamageProgressIncreaseDay);
        wenscMap.put("steppedPlayerDamageProgress", wenscConfig.steppedPlayerDamageProgress);
        wenscMap.put("steppedPlayerDamageIncreasePerLvl", wenscConfig.steppedPlayerDamageIncreasePerLvl);
        wenscMap.put("fishingXp", wenscConfig.fishingXp);
        wenscMap.put("inRainDebuffTime", wenscConfig.inRainDebuffTime);
        wenscMap.put("firstDayLongerDayTime", wenscConfig.firstDayLongerDayTime);
        wenscMap.put("playerDefenseCooldown", wenscConfig.playerDefenseCooldown);
        wenscMap.put("playerDefenceMaxTime", wenscConfig.playerDefenceMaxTime);
        wenscMap.put("playerDisarmPlayer", wenscConfig.playerDisarmPlayer);
        wenscMap.put("bloodMoonMaxHostileFraction", wenscConfig.bloodMoonMaxHostileFraction);
        wenscMap.put("annihilationSkeletonSpawnInLight", wenscConfig.annihilationSkeletonSpawnInLight);
        wenscMap.put("ancientBoneLordSpawnLimitDay", wenscConfig.ancientBoneLordSpawnLimitDay);
        wenscMap.put("wanderingWitchSpawnChanceOverworld", wenscConfig.wanderingWitchSpawnChanceOverworld);
        wenscMap.put("wanderingWitchSpawnLimitDayOverworld", wenscConfig.wanderingWitchSpawnLimitDayOverworld);
        wenscMap.put("wanderingWitchSpawnLimitDayOther", wenscConfig.wanderingWitchSpawnLimitDayOther);
        wenscMap.put("wanderingWitchSpawnChanceUnderworld", wenscConfig.wanderingWitchSpawnChanceUnderworld);
        wenscMap.put("mobMaxSpawnCountIncreasePerDay", wenscConfig.mobMaxSpawnCountIncreasePerDay);
        wenscMap.put("underworldRandomTeleport", wenscConfig.underworldRandomTeleport);
        wenscMap.put("underworldRandomTeleportTimeNew", wenscConfig.underworldRandomTeleportTimeNew);
        wenscMap.put("underworldDebuff", wenscConfig.underworldDebuff);
        wenscMap.put("underworldDebuffPeriod1", wenscConfig.underworldDebuffPeriod1);
        wenscMap.put("underworldDebuffPeriod2", wenscConfig.underworldDebuffPeriod2);
        wenscMap.put("netherrackDamage", wenscConfig.netherrackDamage);
        wenscMap.put("netherrackDamageLimitDay", wenscConfig.netherrackDamageLimitDay);
        wenscMap.put("netherDebuff", wenscConfig.netherDebuff);
        wenscMap.put("netherDebuffTime", wenscConfig.netherDebuffTime);
        wenscMap.put("quartzMaxExpLevel", wenscConfig.quartzMaxExpLevel);
        wenscMap.put("fancyRedExp", wenscConfig.fancyRedExp);
        wenscMap.put("diamondExp", wenscConfig.diamondExp);
        wenscMap.put("emeraldExp", wenscConfig.emeraldExp);
        wenscMap.put("quartzExp", wenscConfig.quartzExp);
        wenscMap.put("goldenAppleEatTime", wenscConfig.goldenAppleEatTime);
        wenscMap.put("enchantedGoldenAppleEatTime", wenscConfig.enchantedGoldenAppleEatTime);
        wenscMap.put("critEnchantmentChanceBoostPerLvl", wenscConfig.critEnchantmentChanceBoostPerLvl);
        wenscMap.put("critEnchantmentDamageBoostPerLvl", wenscConfig.critEnchantmentDamageBoostPerLvl);
        wenscMap.put("emergencyCooldown", wenscConfig.emergencyCooldown);
        wenscMap.put("emergencyCooldownReduceEveryArmor", wenscConfig.emergencyCooldownReduceEveryArmor);
        wenscMap.put("emergencyHealthRecoverAmount", wenscConfig.emergencyHealthRecoverAmount);
        wenscMap.put("conquerorDamageBoostPerLvl", wenscConfig.conquerorDamageBoostPerLvl);
        wenscMap.put("beheadingChanceBoostPerLvl", wenscConfig.beheadingChanceBoostPerLvl);
        wenscMap.put("allProtectionVDefenceFraction", wenscConfig.allProtectionVDefenceFraction);
        wenscMap.put("mobSpawnerSpeedUpWithPlayers", wenscConfig.mobSpawnerSpeedUpWithPlayers);
        wenscMap.put("isSpawnExchanger", wenscConfig.isSpawnExchanger);
        wenscMap.put("boneLordTweak", wenscConfig.boneLordTweak);
        wenscMap.put("skeletonTripleShot", wenscConfig.skeletonTripleShot);
        wenscMap.put("compressedSkeleton", wenscConfig.compressedSkeleton);
        wenscMap.put("compressedSkeletonCrackStandTime", wenscConfig.compressedSkeletonCrackStandTime);
        wenscMap.put("compressedSkeletonExpandCount", wenscConfig.compressedSkeletonExpandCount);
        wenscMap.put("skeletonForceMeleeAttack", wenscConfig.skeletonForceMeleeAttack);
        wenscMap.put("boneLordAndLongdeadChangeWeaponChance", wenscConfig.boneLordAndLongdeadChangeWeaponChance);
        wenscMap.put("batPoisonAttack", wenscConfig.batPoisonAttack);
        wenscMap.put("infernalCreeperBoost", wenscConfig.infernalCreeperBoost);
        wenscMap.put("zombiePigmanUseBow", wenscConfig.zombiePigmanUseBow);
        wenscMap.put("zombiePigmanBoostChance", wenscConfig.zombiePigmanBoostChance);
        wenscMap.put("zombiePigmanAttackAnimails", wenscConfig.zombiePigmanAttackAnimails);
        wenscMap.put("wolvesAttackPlayersWithMeat", wenscConfig.wolvesAttackPlayersWithMeat);
        wenscMap.put("villagerWoolToEmeraldShardCount", wenscConfig.villagerWoolToEmeraldShardCount);
        wenscMap.put("villagerWoolToEmeraldShardShardCount", wenscConfig.villagerWoolToEmeraldShardShardCount);
        wenscMap.put("canBoostIronGolem", wenscConfig.canBoostIronGolem);
        wenscMap.put("mobDefense", wenscConfig.mobDefense);
        wenscMap.put("mobDisarmWhenDefence", wenscConfig.mobDisarmWhenDefence);
        wenscMap.put("mobDisarmWhenAttack", wenscConfig.mobDisarmWhenAttack);
        wenscMap.put("mobAttackCauseFire", wenscConfig.mobAttackCauseFire);
        wenscMap.put("annihilationSkeletonDespawnTime", wenscConfig.annihilationSkeletonDespawnTime);
        wenscMap.put("enderDragonAttackWitherChance", wenscConfig.enderDragonAttackWitherChance);
        wenscMap.put("enderDragonImmuneToArrowHealthPercent", wenscConfig.enderDragonImmuneToArrowHealthPercent);
        wenscMap.put("enderDragonAttackSlowness", wenscConfig.enderDragonAttackSlowness);
        wenscMap.put("netherModAttackFireChance", wenscConfig.netherModAttackFireChance);
        wenscMap.put("slimeAttackDamageMultiplier", wenscConfig.slimeAttackDamageMultiplier);
        wenscMap.put("slimeAttackDamageBonus", wenscConfig.slimeAttackDamageBonus);
        wenscMap.put("giantZombieSpawnZombieCooldown", wenscConfig.giantZombieSpawnZombieCooldown);
        wenscMap.put("animalIllToDeathTime", wenscConfig.animalIllToDeathTime);
        wenscMap.put("breedXpChicken", wenscConfig.breedXpChicken);
        wenscMap.put("breedXpSheep", wenscConfig.breedXpSheep);
        wenscMap.put("breedXpPig", wenscConfig.breedXpPig);
        wenscMap.put("breedXpCow", wenscConfig.breedXpCow);
        wenscMap.put("emeraldFrequencyBigHills", wenscConfig.emeraldFrequencyBigHills);

        wenscMap.put("copperFrequencyOverworld_v0.0.5", wenscConfig.copperFrequencyOverworld);
        wenscMap.put("silverFrequencyOverworld_v0.0.5", wenscConfig.silverFrequencyOverworld);
        wenscMap.put("ironFrequencyOverworld_v0.0.5", wenscConfig.ironFrequencyOverworld);
        wenscMap.put("goldFrequencyOverworld_v0.0.5.3", wenscConfig.goldFrequencyOverworld);
        wenscMap.put("lapisFrequencyOverworld", wenscConfig.lapisFrequencyOverworld);

        wenscMap.put("adamantiumFrequencyOverworld", wenscConfig.adamantiumFrequencyOverworld);
        wenscMap.put("overworldAdamantiteOre", wenscConfig.overworldAdamantiteOre);
        wenscMap.put("mithrilFrequencyOverworld_v0.0.5", wenscConfig.mithrilFrequencyOverworld);
        wenscMap.put("diamondFrequencyOverworld_v0.0.5", wenscConfig.diamondFrequencyOverworld);
        wenscMap.put("copperFrequencyUnderworld_v0.0.5", wenscConfig.copperFrequencyUnderworld);
        wenscMap.put("silverFrequencyUnderworld_v0.0.5", wenscConfig.silverFrequencyUnderworld);
        wenscMap.put("ironFrequencyUnderworld_v0.0.5", wenscConfig.ironFrequencyUnderworld);
        wenscMap.put("goldFrequencyUnderworld_v0.0.5", wenscConfig.goldFrequencyUnderworld);
        wenscMap.put("lapisFrequencyUnderworld_v0.0.5", wenscConfig.lapisFrequencyUnderworld);

        wenscMap.put("mithrilFrequencyUnderworld_v0.0.5", wenscConfig.mithrilFrequencyUnderworld);
        wenscMap.put("diamondFrequencyUnderworld_v0.0.5", wenscConfig.diamondFrequencyUnderworld);
        wenscMap.put("fancyRedFrequencyUnderworld", wenscConfig.fancyRedFrequencyUnderworld);
        wenscMap.put("adamantiumFrequencyUnderworld_v0.0.9", wenscConfig.adamantiumFrequencyUnderworld);
        wenscMap.put("underworldMantleBlockOffset_v0.0.2", wenscConfig.underworldMantleBlockOffset);
        wenscMap.put("netherAdamantiumMaxCountPerChunk", wenscConfig.netherAdamantiumMaxCountPerChunk);
        wenscMap.put("netherAdamantiumMaxCountPerVein", wenscConfig.netherAdamantiumMaxCountPerVein);
        wenscMap.put("healthBarXOffset", wenscConfig.healthBarXOffset);
        wenscMap.put("healthBarYOffset", wenscConfig.healthBarYOffset);
        wenscMap.put("logPlayersInteractWithPortal", wenscConfig.logPlayersInteractWithPortal);
        wenscMap.put("underworldGateOpenDay", wenscConfig.underworldGateOpenDay);
        wenscMap.put("netherGateOpenDay", wenscConfig.netherGateOpenDay);
        wenscMap.put("isAfterDeathKeep", wenscConfig.isAfterDeathKeep);
        wenscMap.put("isCloseShop", wenscConfig.isCloseShop);
        wenscMap.put("plantGrowthRate_v0.0.9", wenscConfig.plantGrowthRate);
        wenscMap.put("whichDayGenVillage", wenscConfig.whichDayGenVillage);
        wenscMap.put("maxLevelLimit", wenscConfig.maxLevelLimit);
        wenscMap.put("enhancePerLvlCostExp", wenscConfig.enhancePerLvlCostExp);
        wenscMap.put("zombieBossMaxHealth", wenscConfig.zombieBossMaxHealth);
        wenscMap.put("zombieBossBaseDamage", wenscConfig.zombieBossBaseDamage);
        wenscMap.put("zombieBossSpawnPercent", wenscConfig.zombieBossSpawnPercent);
        wenscMap.put("creeperFuseTime", wenscConfig.creeperFuseTime);

//        wenscMap.put("md5", wenscConfig.md5String);

        String filePth = "mite-extreme.cfg";
        File file_mite = new File(filePth);
        if (file_mite.exists()) {
            Properties properties = new Properties();
            FileReader fr = null;
            try {
                fr = new FileReader(file_mite.getName());
                properties.load(fr);
                fr.close();
                readConfigFromFile(file_mite, properties);
                packConfigFile(file_mite, properties);
            } catch (FileNotFoundException var6) {
                var6.printStackTrace();
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        } else {
            try {
                if (file_mite.createNewFile()){
                    file_mite.setExecutable(true);//设置可执行权限
                    file_mite.setReadable(true);//设置可读权限
                    file_mite.setWritable(true);//设置可写权限
                    generateConfigFile(file_mite);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JFrame jFrame = new JFrame();
                jFrame.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(jFrame, "mite-extreme.cfg配置文件失败，请前往www.wensc.cn自行下载", "错误", 0);
                System.exit(0);
            }
        }
    }
    public static void beginToLoadShopConfig() {
        String shopConfigFilePath = "mite-extreme-shop.cfg";
        File shopConfigFile = new File(shopConfigFilePath);
        if (shopConfigFile.exists()) {
            Properties properties = new Properties();
            FileReader fr = null;
            try {
                fr = new FileReader(shopConfigFile.getName());
                properties.load(fr);
                fr.close();
                readShopConfigFromFile(shopConfigFile, properties);
            } catch (FileNotFoundException var6) {
                var6.printStackTrace();
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        } else {
            try {
                if (shopConfigFile.createNewFile()){
                    shopConfigFile.setExecutable(true); // 设置可执行权限
                    shopConfigFile.setReadable(true); // 设置可读权限
                    shopConfigFile.setWritable(true); // 设置可写权限
                    generateShopConfigFile(shopConfigFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JFrame jFrame = new JFrame();
                jFrame.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(jFrame, "mite-extreme-shop.cfg配置文件失败，请前往www.wensc.cn自行下载", "错误", 0);
                System.exit(0);
            }
        }
    }

    public static void  readShopConfigFromFile(File file_mite, Properties properties) {
        try{
            FileWriter fileWritter = new FileWriter(file_mite.getName(), true);

            for (Item item : Item.itemsList) {
                if(item != null) {
                    if(item instanceof ItemBlock) {
                        if(!((ItemBlock) item).getBlock().canBeCarried()) {
                            continue;
                        }
                    }
                    try {
                        String itemPrice =  (String) properties.get(item.getUnlocalizedName());
                        if(itemPrice != null) {
                            String [] soldPriceAndBuyPrice = itemPrice.split(",");
                            if(soldPriceAndBuyPrice.length == 2) {
                                double soldPrice = Double.parseDouble(soldPriceAndBuyPrice[0]);
                                double price = Double.parseDouble(soldPriceAndBuyPrice[1]);
                                item.setSoldPrice(soldPrice).setPrice(price);
                            } else if(soldPriceAndBuyPrice.length == 1) {
                                double soldPrice = Double.parseDouble(soldPriceAndBuyPrice[0]);
                                item.setSoldPrice(soldPrice);
                            }
                        } else {
                            if(item instanceof ItemBlock) {
                                fileWritter.write("// " + ((ItemBlock) item).getBlock().getLocalizedName() + " ID: " + ((ItemBlock) item).getBlockID() + "\n");
                                fileWritter.write(item.getUnlocalizedName() + "=" + item.getSoldPrice() +","+ item.getPrice()+ "\n\n");
                            } else {
                                fileWritter.write("// " + item.getItemDisplayName() + " ID: " + item.itemID + "\n");
                                fileWritter.write(item.getUnlocalizedName() + "=" + item.getSoldPrice() +","+ item.getPrice()+ "\n\n");
                            }
                        }
                    } catch (Exception e) {
                        if(item instanceof ItemBlock) {
                            Minecraft.setErrorMessage("配置项：" + ((ItemBlock) item).getBlock().getLocalizedName() + " ID: " + ((ItemBlock) item).getBlockID() + " 错误！！！");
                        } else {
                            Minecraft.setErrorMessage("配置项：" + item.getItemDisplayName() + " ID: " + item.itemID + " 错误！！！");
                        }
                    }
                }
            }
            fileWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  readConfigFromFile(File file_mite, Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            ConfigItem configItem = wenscMap.get(key);
            if(configItem != null) {
                if(configItem.ConfigValue instanceof Boolean) {
                    configItem.setConfigValue(Boolean.parseBoolean(properties.getProperty(key)));
                } else if(configItem.ConfigValue instanceof Float) {
                    float value = Float.parseFloat(properties.getProperty(key));
                    if(configItem.isNeedCompare) {
                        value = value > (float)configItem.max ? (float) configItem.max : Math.max(value, (float) configItem.min);
                    }
                    configItem.setConfigValue(value);
                } else if(configItem.ConfigValue instanceof Double) {
                    double value = Double.parseDouble(properties.getProperty(key));
                    if(configItem.isNeedCompare) {
                        value = value > (double)configItem.max ? (double) configItem.max : Math.max(value, (double) configItem.min);
                    }
                    configItem.setConfigValue(value);
                } else if(configItem.ConfigValue instanceof Integer) {
                    int value = Integer.parseInt(properties.getProperty(key));
                    if(configItem.isNeedCompare) {
                        value = value > (int)configItem.max ? (int) configItem.max : Math.max(value, (int) configItem.min);
                    }
                    configItem.setConfigValue(value);
                } else {
                    configItem.setConfigValue(properties.getProperty(key));
                }
            }
        }
    }

    public static void packConfigFile(File file,Properties properties) {
        try{
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            for (Map.Entry<String, ConfigItem> entry: wenscMap.entrySet()) {
                ConfigItem value = entry.getValue();
                String localValue = properties.getProperty(value.ConfigKey);
                if(localValue == null) {
                    fileWritter.write("// " + value.ConfigComment + "\n");
                    fileWritter.write(value.ConfigKey + "=" + value.ConfigValue + "\n\n");
                }
            }
            fileWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateConfigFile(File file) {
        try{
            FileWriter fileWritter = new FileWriter(file.getName());
            fileWritter.write("// MITE-Extreme配置文件，说明：【布尔类型：true为开启，false关闭】，在【名称=值】之间/之后不要存在空格或者其他无关字符，【tick：20tick=1秒】\n");
            for (Map.Entry<String, ConfigItem> entry: wenscMap.entrySet()) {
                ConfigItem value = entry.getValue();
                fileWritter.write("// " + value.ConfigComment + "\n");
                fileWritter.write(value.ConfigKey + "=" + value.ConfigValue + "\n\n");
            }
            fileWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateShopConfigFile(File file) {
        try{
            FileWriter fileWritter = new FileWriter(file.getName());
            fileWritter.write("// MITE-Extreme商店配置文件，说明：参数之间使用英文逗号分隔，请严格遵循格式（商品英文名=售出价格,购买价格），价格小于等于0代表不可出售或者不可购买，价格可以为小数，乱改造成无法启动概不负责\n");
            for (Item item : Item.itemsList) {
                if(item != null) {
                    if(item instanceof ItemBlock) {
                        if(!((ItemBlock) item).getBlock().canBeCarried()) {
                            continue;
                        }
                        fileWritter.write("// " + ((ItemBlock) item).getBlock().getLocalizedName() + " ID: " + ((ItemBlock) item).getBlockID() + "\n");
                        fileWritter.write(item.getUnlocalizedName() + "=" + item.getSoldPrice() +","+ item.getPrice()+ "\n\n");
                    } else {
                        fileWritter.write("// " + item.getItemDisplayName() + " ID: " + item.itemID + "\n");
                        fileWritter.write(item.getUnlocalizedName() + "=" + item.getSoldPrice() +","+ item.getPrice()+ "\n\n");
                    }
                }
            }
            fileWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
