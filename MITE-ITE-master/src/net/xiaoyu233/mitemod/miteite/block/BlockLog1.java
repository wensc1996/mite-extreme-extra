package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public final class BlockLog1 extends BlockRotatable implements IBlockWithSubtypes {
    private BlockSubtypes subtypes = new BlockSubtypes(new String[]{"maple", "cherry", "maple", "maple"});
    private IIcon[] end_icons;

    protected BlockLog1(int par1) {
        super(par1, Material.wood);
        this.modifyMinHarvestLevel(1);
        this.setHardness(BlockHardness.log);
        this.setCreativeTab(CreativeModeTab.tabBlock);
        this.setStepSound(soundWoodFootstep);
        this.setUnlocalizedName("log1");
        this.setTextureName("log1");
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        byte var7 = 4;
        int var8 = var7 + 1;
        if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8)) {
            for(int var9 = -var7; var9 <= var7; ++var9) {
                for(int var10 = -var7; var10 <= var7; ++var10) {
                    for(int var11 = -var7; var11 <= var7; ++var11) {
                        int var12 = par1World.getBlockId(par2 + var9, par3 + var10, par4 + var11);
                        if (var12 == Blocks.leaves1.blockID) {
                            int var13 = par1World.getBlockMetadata(par2 + var9, par3 + var10, par4 + var11);
                            if ((var13 & 8) == 0) {
                                par1World.setBlockMetadata(par2 + var9, par3 + var10, par4 + var11, var13 | 8, 4);
                            }
                        }
                    }
                }
            }
        }

    }

    protected IIcon c(int par1) {
        return this.subtypes.getIcon(par1);
    }

    protected IIcon d(int par1) {
        return this.end_icons[par1];
    }

    public static int limitToValidMetadata(int par0) {
        return par0 & 3;
    }

    public String getMetadataNotes() {
        return "Bits 1 and 2 used for subtype, bit 4 set if aligned WE, and bit 8 set if aligned NS";
    }

    public boolean isValidMetadata(int metadata) {
        return metadata >= 0 && metadata < 16 && !BitHelper.isBitSet(metadata, 12);
    }

    public int getBlockSubtypeUnchecked(int metadata) {
        return metadata & 3;
    }

    public void a(mt par1IconRegister) {
        this.subtypes.setIcons(this.registerIcons(par1IconRegister, this.getTextures(), this.E() + "_"));
        this.end_icons = this.registerIcons(par1IconRegister, this.getTextures(), this.E() + "_", "_top");
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        return info.wasExploded() ? this.dropBlockAsEntityItem(info, Item.stick.itemID, 0, 1, 1.5F)
                : super.dropBlockAsEntityItem(info);
    }

    public String getNameDisambiguationForReferenceFile(int metadata) {
        return "log1";
    }

    public String[] getTextures() {
        return this.subtypes.getTextures();
    }

    public String[] getNames() {
        return this.subtypes.getNames();
    }
}