package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/SlotAnvilResult")
class SlotAnvilResultTrans {
//    注意如果采用此方法注入，里面的静态变量会被注入到构造函数中，如果shadow字段定义为Null,那么后续读取该变量皆为Null
    @Shadow
    World field_135071_a;
    @Shadow
    ContainerAnvil repairContainer;

    @Inject(method = "<init>", at=@At("RETURN"))
    public void initContainer(ContainerAnvil par1ContainerRepair, IInventory par2IInventory, int par3, int par4, int par5, int par7, int par8, int par9, CallbackInfo callbackInfo) {
        this.repairContainer = par1ContainerRepair;
        this.field_135071_a =  par1ContainerRepair.world;
    }

    @Inject(method = "onPickupFromSlot", at = @At("HEAD"))
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, CallbackInfo callbackInfo) {
        ItemStack var1 = this.repairContainer.getRepairInputInventoryTrans().getStackInSlot(0);
        ItemStack var2 = this.repairContainer.getRepairInputInventoryTrans().getStackInSlot(1);
        if(var1 != null && var2 != null && var2.getItem() instanceof ItemEnchantedBook) {
            if(!par1EntityPlayer.isOp()) {
                par1EntityPlayer.experience -= (var1.getEnhanceTotalLevel() + var2.getEnhanceTotalLevel()) * Configs.wenscConfig.enhancePerLvlCostExp.ConfigValue;
            }
        }
    }
}
