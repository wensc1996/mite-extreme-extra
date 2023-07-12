package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.WorldMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WorldMap.class)
public class WorldMapTrans {
    @ModifyConstant(method = {
            "writeMapColorsToFile"
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
