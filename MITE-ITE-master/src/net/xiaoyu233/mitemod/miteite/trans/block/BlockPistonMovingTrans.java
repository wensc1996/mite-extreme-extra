package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockPistonMoving.class)
public class BlockPistonMovingTrans extends Block{
    protected BlockPistonMovingTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }


    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "getAxisAlignedBB", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/BlockPistonMoving;getDirectionFacing(I)Lnet/minecraft/EnumDirection;", shift = At.Shift.AFTER), cancellable = true)
    public void getAxisAlignedBB(World par1World, int par2, int par3, int par4, int par5, float par6, int par7, CallbackInfoReturnable<AxisAlignedBB> cir, AxisAlignedBB var8, EnumDirection direction) {
        if(direction == null) {
            cir.setReturnValue(null);
            cir.cancel();
        }
    }
}
