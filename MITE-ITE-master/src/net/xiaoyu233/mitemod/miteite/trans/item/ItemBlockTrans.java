package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockSapling1;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.List;

@Mixin(ItemBlock.class)
public abstract class ItemBlockTrans extends Item{
    @Shadow private int blockID;
    @Shadow public abstract Block getBlock();

    public String getItemDisplayName(ItemStack item_stack) {
        return this.getBlock().getItemDisplayName(item_stack);
    }

    @Overwrite
    public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List) {
        if(Block.blocksList[this.blockID] != null) {
            Block.blocksList[this.blockID].getItemStacks(par1, par2CreativeTabs, par3List);
        } else {

        }
    }

    @ModifyConstant(method = {
            "<init>",
    }, constant = @Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }

    @Overwrite

    public int getBurnTime(ItemStack item_stack) {
        if (!this.canBurnAsFuelSource()) {
            return 0;
        } else {
            Block block = this.getBlock();
            if (block == Block.wood || block == Blocks.wood1) {
                return 1600;
            } else if (block != Block.planks && block != Block.woodDoubleSlab && block != Block.woodenButton) {
                if (block != Block.woodSingleSlab && block != Block.sapling && block != Blocks.sapling1 && block != Block.deadBush) {
                    if (block != Block.torchWood && !(block instanceof BlockRedstoneTorch)) {
                        if (block.blockMaterial == Material.wood) {
                            return 400;
                        } else {
                            return block == Block.coalBlock ? 16000 : 0;
                        }
                    } else {
                        return 800;
                    }
                } else {
                    return 200;
                }
            } else {
                return 400;
            }
        }
    }

    @Overwrite
    public boolean canBurnAsFuelSource() {
        Block block = this.getBlock();
        if (!(block instanceof BlockTorch) && !(block instanceof BlockSapling || block instanceof BlockSapling1)) {
            return block != Block.woodenButton && block != Block.deadBush ? block.blockMaterial.canBurnAsFuelSource() : true;
        } else {
            return true;
        }
    }

    @Overwrite
    public float getCompostingValue() {
        Block block = this.getBlock();
        if (block == Block.hay) {
            return Item.wheat.getCompostingValue() * 9.0F;
        } else if (block != Block.leaves && block != Block.vine && block != Blocks.leaves1) {
            if (block != Block.melon && !(block instanceof BlockPumpkin)) {
                if (!(block instanceof BlockMushroom) && block != Block.tallGrass && block != Block.waterlily) {
                    if (block instanceof BlockFlower) {
                        return 0.25F;
                    } else {
                        return block == Block.cake ? 2.4F : super.getCompostingValue();
                    }
                } else {
                    return 0.5F;
                }
            } else {
                return 2.0F;
            }
        } else {
            return 1.0F;
        }
    }
}
