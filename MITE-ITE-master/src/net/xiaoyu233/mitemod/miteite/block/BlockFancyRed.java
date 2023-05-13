package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.BlockOre;
import net.minecraft.Material;

public class BlockFancyRed extends BlockOre {
    public BlockFancyRed(int par1, Material vein_material, int min_harvest_level) {
        super(par1, vein_material, min_harvest_level);
        this.setHardness(3.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("oreFancyRed");
        this.setTextureName("fancy_red");
    }

}
