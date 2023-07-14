package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockLeaves1;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class ItemLeaves1 extends ItemBlock {
    public ItemLeaves1(Block block) {
        super(block);
    }

    public int getMetadata(int par1) {
        return par1 | 4;
    }

    public IIcon getIconFromSubtype(int par1) {
        return Blocks.leaves1.a(0, par1);
    }

    public int a(ItemStack par1ItemStack, int par2) {
        int var3 = par1ItemStack.getItemSubtype();
        return (var3 & 1) == 1 ? abs.a()
                : ((var3 & 2) == 2 ? abs.b()
                : Constant.getFoliageColorMaple());
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        if (par1ItemStack == null) {
            return super.getUnlocalizedName();
        } else {
            int var2 = par1ItemStack.getItemSubtype();
            if (var2 < 0 || var2 >= BlockLeaves1.LEAF_TYPES.length) {
                var2 = 0;
            }

            return super.getUnlocalizedName() + "." + BlockLeaves1.LEAF_TYPES[var2];
        }
    }
}
