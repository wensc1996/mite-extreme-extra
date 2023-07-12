package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.bdo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(bdo.class)
public class EntityBreakingFXTrans {
    @ModifyConstant(method = {
            "<init>(Lnet/minecraft/World;DDDLnet/minecraft/Item;I)V"
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
