package net.xiaoyu233.mitemod.miteite.trans.block;


import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockFurnace.class)
public abstract class BlockFurnaceMixin extends BlockDirectionalWithTileEntity {

    protected BlockFurnaceMixin(int id, Material material, BlockConstants constants) {
        super(id, material, constants);
    }

    public int getDurability(){
        if (this.blockMaterial == Material.clay) {
            return 2048;
        } else if (this.blockMaterial == Material.hardened_clay) {
            return 3072;
        } else if(this.blockMaterial == Material.stone){
            return 8192;
        } else if (this.blockMaterial == Material.sand) {
            return 8192;
        } else if (this.blockMaterial == Material.obsidian) {
            return 16384;
        } else if (this.blockMaterial == Material.netherrack) {
            return 32768;
        }   else {
            return 1;
        }
    }


    @Overwrite
    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        Block furnace_block = Block.getBlock(this.getIdleBlockID());
        if (info.wasExploded()) {
            if (furnace_block == Block.furnaceClayIdle) {
                return 0;
            } else {
                Block model_block;
                if (furnace_block == Block.furnaceSandstoneIdle) {
                    model_block = Block.sandStone;
                } else if (furnace_block == Block.furnaceIdle) {
                    model_block = Block.cobblestone;
                } else if (furnace_block == Block.furnaceObsidianIdle) {
                    model_block = Block.obsidian;
                } else {
                    if (furnace_block != Block.furnaceNetherrackIdle) {
                        return 0;
                    }

                    model_block = Block.netherrack;
                }

                return model_block.dropBlockAsEntityItem(info.setBlock(model_block, 0));
            }
        } else {
            if (furnace_block.blockMaterial != Materials.vibranium){
                TileEntityFurnace tileEntity = (TileEntityFurnace)info.tile_entity;
                return super.dropBlockAsEntityItem(info.setDamage(tileEntity.damage));
            } else {
                return super.dropBlockAsEntityItem(info);
            }
        }
    }


    public boolean tryPlaceFromHeldItem(int x, int y, int z, EnumFace face, ItemStack item_stack, EntityPlayer player, float offset_x, float offset_y, float offset_z, boolean perform_placement_check, boolean drop_existing_block, boolean test_only) {
        if (super.tryPlaceFromHeldItem(x, y, z, face, item_stack, player, offset_x, offset_y, offset_z, perform_placement_check, drop_existing_block, test_only)) {
            if (!test_only && player.onServer()) {
                TileEntityFurnace tileEntity = (TileEntityFurnace)player.worldObj.getBlockTileEntity(x, y, z);
                tileEntity.addDamage(player.worldObj, x, y, z, item_stack.getItemDamage());
            }
            return true;
        } else {
            return false;
        }
    }

    @Shadow
    public abstract int getIdleBlockID();

    @Shadow
    public TileEntity createNewTileEntity(World world) {
        return null;
    }

    @Shadow
    public EnumDirection getDirectionFacing(int i) {
        return null;
    }

    @Shadow
    public int getMetadataForDirectionFacing(int i, EnumDirection enumDirection) {
        return 0;
    }
}
