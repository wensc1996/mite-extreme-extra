package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
//import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.Map;

@Mixin(ContainerAnvil.class)
public class ContainerAnvilTrans extends Container {
    @Shadow
    private IInventory inputSlots;
    @Shadow
    private int stackSizeToBeUsedInRepair;

    public ContainerAnvilTrans(EntityPlayer player) {
        super(player);
    }

    @Shadow
    public boolean canInteractWith(EntityPlayer var1) {
        return false;
    }

    @Inject(method = "isRepairing",
            at = @At(value = "FIELD",target = "Lnet/minecraft/ContainerAnvil;repair_fail_condition:I",shift = At.Shift.AFTER,opcode = Opcodes.PUTFIELD),
    slice = @Slice(from = @At(value = "INVOKE",target = "Lnet/minecraft/Item;hasQuality()Z")))
    private void removeRepairExpReq(boolean b, CallbackInfoReturnable<Boolean> callbackInfo){
        this.repair_fail_condition = 0;
    }
    @Redirect(method = "updateRepairOutput",at = @At(ordinal = 0, value = "INVOKE",target = "Lnet/minecraft/ItemStack;isEnchantable()Z"))
    public boolean isEnchantable(ItemStack item_stack_in_first_slot) {
        return true;
    }

    @Redirect(method = "updateRepairOutput",at = @At(ordinal = 0, value = "INVOKE",target = "Lnet/minecraft/ItemStack;isItemEnchanted()Z"))
    public boolean updateRepairOutputEnableEnhanted(ItemStack item_stack_in_first_slot) {
        ItemStack var1 = this.inputSlots.getStackInSlot(0);
        ItemStack var2 = this.inputSlots.getStackInSlot(1);
        if(var1 != null && var2 != null) {
            if(!this.player.isOp() && this.player.experience < (var1.getEnhanceTotalLevel() + var2.getEnhanceTotalLevel()) * Configs.wenscConfig.enhancePerLvlCostExp.ConfigValue) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Redirect(method = "updateRepairOutput",at = @At(value = "INVOKE",target = "Lnet/minecraft/Enchantment;getNumLevels()I"))
    public int getNumLevelsForVibraniumTrans(Enchantment enchantment) {
        ItemStack var1 = this.inputSlots.getStackInSlot(0);
        return var1.getItem().getHardestMetalMaterial() == Materials.vibranium ? enchantment.getNumLevelsForVibranium() : enchantment.getNumLevels();
    }

    public IInventory getRepairInputInventoryTrans() {
        return this.inputSlots;
    }

    public int getStackSizeUsedInRepairTrans() {
        return this.stackSizeToBeUsedInRepair;
    }

}
