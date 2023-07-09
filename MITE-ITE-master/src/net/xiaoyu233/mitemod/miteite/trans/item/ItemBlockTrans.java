package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Block;
import net.minecraft.Item;
import net.minecraft.ItemBlock;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemBlock.class)
public abstract class ItemBlockTrans extends Item{
    @Shadow public abstract Block getBlock();

    public String getItemDisplayName(ItemStack item_stack) {
        return this.getBlock().getItemDisplayName(item_stack);
    }

    @ModifyConstant(method = {
            "<init>",
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }
}
