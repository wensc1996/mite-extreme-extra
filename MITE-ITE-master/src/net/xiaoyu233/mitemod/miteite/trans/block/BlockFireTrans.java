package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockFire.class)
public class BlockFireTrans {

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
