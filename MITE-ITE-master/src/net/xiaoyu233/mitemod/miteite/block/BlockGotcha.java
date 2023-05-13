package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.Block;
import net.minecraft.BlockConstants;
import net.minecraft.BlockHalfTransparant;
import net.minecraft.BlockSubtypes;
import net.minecraft.CreativeModeTab;
import net.minecraft.IBlockWithSubtypes;
import net.minecraft.IIcon;
import net.minecraft.Material;
import net.minecraft.mt;

public class BlockGotcha extends BlockHalfTransparant implements IBlockWithSubtypes {
    private BlockSubtypes subtypes = new BlockSubtypes(new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "light_gray", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"});
    public Material gotcha_metal;
    private float crafting_difficulty_as_component = 0.0F;

    public BlockGotcha(int par1, Material par2Material, boolean par3) {
        super(par1, "glass", par2Material, par3, (new BlockConstants()).setNeverHidesAdjacentFaces());
        this.setBlockPrice(2D);
        this.setBlockSoldPrice(2D);
        this.setMaxStackSize(64);
        this.setBlockHardness(0.0F);
        this.setCraftingDifficultyAsComponent(1.0E-11F);
        this.setCushioning(1000.0F);
        this.setMinHarvestLevel(0);
        this.setCreativeTab(CreativeModeTab.tabBlock);
        this.setStepSound(Block.soundGlassFootstep);
        this.setLightValue(1.0F);
        this.setResistance(1000F);
    }

    public BlockGotcha setCraftingDifficultyAsComponent(float difficulty) {
        this.crafting_difficulty_as_component = difficulty;
        return this;
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

    public int getBlockSubtypeUnchecked(int metadata) {
        return metadata;
    }

    public void a(mt par1IconRegister) {
        this.subtypes.setIcons(this.registerIcons(par1IconRegister, this.getTextures(), "gotcha_"));
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
