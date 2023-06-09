package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityGemSetting;

public class BlockGemSetting extends Block implements IContainer {

    public IIcon furnaceIconTop;
    public IIcon furnaceIconFront;


    public BlockGemSetting(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
        this.setStepSound(soundStoneFootstep);
        this.setCreativeTab(CreativeModeTab.tabDecorations);
        this.setMaxStackSize(1);
        this.setLightOpacity(0);
    }

    @Override
    public boolean canBeReplacedBy(int metadata, Block other_block, int other_block_metadata) {
        return false;
    }

    @Override
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void a(mt par1IconRegister)
    {
        this.cW = par1IconRegister.a("gem_setting/tool_armor_setting/side");
        this.furnaceIconFront = par1IconRegister.a("gem_setting/tool_armor_setting/front");
        this.furnaceIconTop = par1IconRegister.a("gem_setting/tool_armor_setting/top");
    }

    @Override
    public boolean isPortable(World world, EntityLiving entity_living_base, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isPortal() {
        return true;
    }

    public float getCraftingDifficultyAsComponent(int metadata)
    {
        return -1.0F;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon a(int side, int meta)
    {
        return side == 1
                ? this.furnaceIconTop
                : (side == 0 ? this.furnaceIconTop
                : (side != meta ? this.cW
                : this.furnaceIconFront));
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityGemSetting();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z)
    {
        if (player.onServer() && world.isAirOrPassableBlock(x, y + 1, z, false))
        {
            TileEntityGemSetting tile_entity = (TileEntityGemSetting) world.getBlockTileEntity(x, y, z);
            if(tile_entity != null && !tile_entity.isUsing) {
                tile_entity.isUsing = true;
                player.displayGUIGemSetting(tile_entity);
            }
        }
        return true;
    }
}
