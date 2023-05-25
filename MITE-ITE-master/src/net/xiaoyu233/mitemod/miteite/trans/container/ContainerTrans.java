package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Container.class)
public abstract class ContainerTrans {
    @Shadow
    public final EntityPlayer player;
    public ContainerTrans(EntityPlayer player) {
        this.player = player;
    }

    //    LocalCapture.PRINT 好东西,需要在控制台打印，日志不会记录，采用服务端来查看比较好

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "slotClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/Container;transferStackInSlot(Lnet/minecraft/EntityPlayer;I)Lnet/minecraft/ItemStack;", shift = At.Shift.AFTER))
    public void injectSlotClick(int par1, int par2, int par3, boolean holding_shift, EntityPlayer par4EntityPlayer, CallbackInfoReturnable<ItemStack> cir, ItemStack var5, PlayerInventory var6, Slot var16) {
        var16.onSlotClicked(player,1 , var16.getContainer());
    }


    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "mergeItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/Slot;putStack(Lnet/minecraft/ItemStack;)V", shift = At.Shift.AFTER))
    public void injectSlotClick2(ItemStack par1ItemStack, int par2, int par3, boolean par4, CallbackInfoReturnable<Boolean> cir, boolean var5, int var6, Slot var7, ItemStack var8) {
        var7.onSlotClicked(player,1 , var7.getContainer());
    }
}
