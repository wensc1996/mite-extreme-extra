package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public class BlockStairsColorful extends BlockStairs {
    private BlockSubtypes subtypes = new BlockSubtypes(new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "light_gray", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"});
    BlockStairsColorful(int par1, Block par2Block, int par3) {
        super(par1, par2Block, par3);
        this.setCushioning(1000.0F);
        this.setMaxStackSize(64);
        this.setResistance(1000F);
        this.setLightValue(0.7F);
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        return this.dropBlockAsEntityItem(info, this.createStackedBlock(info.getMetadata()));
    }
}