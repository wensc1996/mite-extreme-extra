package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public class BlockColorful extends Blocks implements IBlockWithSubtypes {
    private BlockSubtypes subtypes = new BlockSubtypes(new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "light_gray", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"});

    public BlockColorful(int par1, Material par2Material) {
        super(par1, par2Material, new BlockConstants());
        this.setBlockPrice(0.5D);
        this.setBlockSoldPrice(0.5D);
        this.setMaxStackSize(64);
        this.setBlockHardness(2.0F);
        this.setCushioning(1000.0F);
        this.setResistance(10F);
        this.setMinHarvestLevel(0);
        this.setCreativeTab(CreativeModeTab.tabBlock);
        this.setStepSound(Block.soundStoneFootstep);
        this.setLightValue(0.7F);
    }

    public boolean isAlwaysReplaceable() {
        return false;
    }

    public boolean canBeReplacedBy(int metadata, Block other_block, int other_block_metadata) {
        return false;
    }

    public int n() {
        return 0;
    }

    public boolean isValidMetadata(int metadata) {
        return metadata >= 0 && metadata < 16;
    }

//    public int getBlockSubtypeUnchecked(int metadata) {
//        return metadata;
//    }
    public int getBlockSubtypeUnchecked(int metadata) {
        return metadata & 15;
    }

    public void a(mt par1IconRegister) {
        this.subtypes.setIcons(this.registerIcons(par1IconRegister, this.getTextures(), "colorful/colorful_"));
    }

    public IIcon a(int side, int metadata) {
        return this.subtypes.getIcon(this.getBlockSubtype(metadata));
    }

    public String[] getTextures() {
        return this.subtypes.getTextures();
    }

    public String[] getNames() {
        return this.subtypes.getNames();
    }
}
