package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockBreakInfo;
import net.minecraft.BlockLongGrass;
import net.minecraft.BlockPlant;
import net.minecraft.EntityPlayer;
import net.minecraft.EnumEntityFX;
import net.minecraft.Item;
import net.minecraft.Material;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLongGrass.class)
public class BlockLongGrassTrans extends BlockPlant {
    protected BlockLongGrassTrans(int id) {
        super(id, Material.grass);
        float size = 0.4F;
        this.setBlockBoundsForAllThreads((double)(0.5F - size), 0.0D, (double)(0.5F - size), (double)(0.5F + size), 0.800000011920929D, (double)(0.5F + size));
    }
}
