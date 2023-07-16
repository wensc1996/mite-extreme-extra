package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.EntityPlayer;
import net.minecraft.SlotResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SlotResult.class)
public class SlotResultMixin {
    public int getNumCraftingResults_(EntityPlayer player){
        return this.getNumCraftingResults(player);
    }
    @Shadow
    protected int getNumCraftingResults(EntityPlayer player) {
        return 1;
    }
}
