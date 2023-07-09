package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.StatisticList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StatisticList.class)
public class StatisticListTrans {
    @ModifyConstant(method = {
            "initBreakableStats",
            "initStats",
            "initMinableStats",
            "initUsableStats"
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
