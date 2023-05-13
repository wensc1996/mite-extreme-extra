package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnchantmentVampiric.class)
public class EnchantmentVampiricTrans {
    @Overwrite
    public boolean canEnchantItem(Item item) {
        if (item instanceof ItemTool) {
            Material material = ((ItemTool)item).getToolMaterial();
            if (material != Materials.vibranium) {
                return false;
            }
        }

        return item instanceof ItemSword || item instanceof ItemScythe || item instanceof ItemCudgel;
    }
}
