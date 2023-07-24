package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.Block;
import net.minecraft.BlockFlowerPot;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static net.minecraft.Block.deadBush;

@Mixin(BlockFlowerPot.class)
public class BlockFlowerPotTrans {
    @Overwrite
    public boolean isValidMetadata(int metadata) {
        return metadata >= 0 && metadata < 16;
    }

    @Overwrite
    public static ItemStack getPlantForMeta(int par0) {
        switch (par0) {
            case 1:
                return new ItemStack(Block.plantRed);
            case 2:
                return new ItemStack(Block.plantYellow);
            case 3:
                return new ItemStack(Block.sapling, 1, 0);
            case 4:
                return new ItemStack(Block.sapling, 1, 1);
            case 5:
                return new ItemStack(Block.sapling, 1, 2);
            case 6:
                return new ItemStack(Block.sapling, 1, 3);
            case 7:
                return new ItemStack(Block.mushroomRed);
            case 8:
                return new ItemStack(Block.mushroomBrown);
            case 9:
                return new ItemStack(Block.cactus);
            case 10:
                return new ItemStack(deadBush);
            case 11:
                return new ItemStack(Block.tallGrass, 1, 2);
            case 12:
                return new ItemStack(deadBush, 1, 1);
            case 13:
                return new ItemStack(Blocks.sapling1, 1, 0);
            case 14:
                return new ItemStack(Blocks.sapling1, 1, 1);
            case 15:
                return new ItemStack(Blocks.sapling1, 1, 2);
            default:
                return null;
        }
    }

    @Overwrite
    public static int getMetaForPlant(ItemStack par0ItemStack) {
        int var1 = par0ItemStack.getItem().itemID;
        if (var1 == Block.plantRed.blockID) {
            return par0ItemStack.getItemSubtype() == 0 ? 1 : 0;
        } else if (var1 == Block.plantYellow.blockID) {
            return 2;
        } else if (var1 == Block.cactus.blockID) {
            return 9;
        } else if (var1 == Block.mushroomBrown.blockID) {
            return 8;
        } else if (var1 == Block.mushroomRed.blockID) {
            return 7;
        } else if (var1 == deadBush.blockID) {
            return deadBush.isWitherwood(par0ItemStack.getItemSubtype()) ? 12 : 10;
        } else {
            if (var1 == Block.sapling.blockID) {
                switch (par0ItemStack.getItemSubtype()) {
                    case 0:
                        return 3;
                    case 1:
                        return 4;
                    case 2:
                        return 5;
                    case 3:
                        return 6;
                }
            }

            if (var1 == Blocks.sapling1.blockID) {
                switch (par0ItemStack.getItemSubtype()) {
                    case 0:
                        return 13;
                    case 1:
                        return 14;
                    case 2:
                        return 15;
                }
            }

            if (var1 == Block.tallGrass.blockID) {
                switch (par0ItemStack.getItemSubtype()) {
                    case 2:
                        return 11;
                }
            }

            return 0;
        }
    }
}
