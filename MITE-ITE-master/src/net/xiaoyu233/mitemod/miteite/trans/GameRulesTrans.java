package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.GameRules;
import net.minecraft.Minecraft;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GameRules.class)
public class  GameRulesTrans {

    public GameRulesTrans() {
    }
    @Overwrite
    private boolean getGameRuleOverrideBooleanValue(String rule_name) {
        if (rule_name.equals("doFireTick")) {
            return true;
        } else if (rule_name.equals("mobGriefing")) {
            return true;
        } else if (rule_name.equals("keepInventory")) {
            return Configs.wenscConfig.isAfterDeathKeep.ConfigValue;
        } else if (rule_name.equals("doMobSpawning")) {
            return true;
        } else if (rule_name.equals("doMobLoot")) {
            return true;
        } else if (rule_name.equals("doTileDrops")) {
            return true;
        } else if (rule_name.equals("commandBlockOutput")) {
            return true;
        } else if (rule_name.equals("naturalRegeneration")) {
            return true;
        } else if (rule_name.equals("doDaylightCycle")) {
            return true;
        } else {
            Minecraft.setErrorMessage("getGameRuleOverride: unhandled rule " + rule_name);
            return true;
        }
    }
}
