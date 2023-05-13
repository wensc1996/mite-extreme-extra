package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public class BlockLantern extends Blocks {
    BlockLantern(int par1, Material material) {
        super(par1, material, (new BlockConstants()).setNeverHidesAdjacentFaces().setNotAlwaysLegal());
        this.setMaxStackSize(64);
        this.setCushioning(1000.0F);
        this.setLightValue(1F);
        this.setBounds(true);
        this.setResistance(1000F);
        this.setCreativeTab(CreativeModeTab.tabBlock);
    }


    private void setBounds(boolean for_all_threads) {
        float var1 = 0.375F;
        float var2 = var1 / 2.0F;
        // 前 下 厚度 高度 宽度
        this.setBlockBounds((double)(0.5F - var2), 0.0D, (double)(0.5F - var2), (double)(0.5F + var2), (double)0.6875D, (double)(0.5F + var2), for_all_threads);
    }

//    public boolean isLegalOn(int metadata, Block block_below, int block_below_metadata) {
//        return block_below != null && block_below != leaves && block_below.isTopFlatAndSolid(block_below_metadata);
//    }

    public boolean canBeReplacedBy(int metadata, Block other_block, int other_block_metadata) {
        return false;
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        if (!info.wasExploded() && !info.wasCrushed()) {
            return super.dropBlockAsEntityItem(info, Blocks.blockLantern);
        } else {
            return 0;
        }
    }
    public boolean isStandardFormCube(boolean[] is_standard_form_cube, int metadata) {
        return false;
    }

    public void setBlockBoundsForItemRender(int item_damage) {
        this.setBounds(false);
    }
}
