package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEntityHopper.class)
public abstract class TileEntityHopperTrans extends TileEntity {
    @Shadow public abstract ItemStack getStackInSlot(int par1);

    @Inject(method = "insertStackFromEntity",at = @At("HEAD"),cancellable = true)
    private static void injectCheckCanPick(IInventory par0IInventory, EntityItem par1EntityItem,CallbackInfoReturnable<Boolean> callback){
        if (!par1EntityItem.canBePickUpByPlayer()){
            callback.setReturnValue(false);
            callback.cancel();
        }
    }

    @Inject(method = "insertItemToInventory",at = @At("RETURN"),cancellable = true)
    private void insertItemToInventoryInject(CallbackInfoReturnable<Boolean> callback) {
        Block blockAbove = this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        if( blockAbove != null && blockAbove.blockID ==  Blocks.lavaStill.blockID) {
            ItemStack item = this.getStackInSlot(0);
            if(item == null || item.itemID != Items.lavaInPipes.itemID) {
                this.setInventorySlotContents(0, new ItemStack(Items.lavaInPipes.itemID,1));
            }
        }
        callback.setReturnValue(true);
        callback.cancel();
    }

    @Shadow
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
    }
}
