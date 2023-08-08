package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
public class BlockSpawn extends Block {
    BlockSpawn(int par1, Material material) {
        super(par1, material, (new BlockConstants()).setNeverHidesAdjacentFaces().setNotAlwaysLegal());
        this.setMaxStackSize(1);
        this.setHardness(2F);
        this.setLightOpacity(0);
        this.setMinHarvestLevel(2);
        this.setResistance(60000F);
        this.setCreativeTab(CreativeModeTab.tabBlock);
        this.setStepSound(Block.soundStoneFootstep);
    }

    public int dropBlockAsItself(BlockBreakInfo info) {
        if (info.block != this) {
            Minecraft.setErrorMessage("dropBlockAsItself: info.block!=this");
        }

        if (!info.block.canBeCarried()) {
            Minecraft.setErrorMessage("dropBlockAsItself: " + this + " cannot be carried");
        }

        EntityPlayer entityPlayer = info.getResponsiblePlayer();
        if (entityPlayer != null && entityPlayer.spawnStoneWorldId != -999) {
            if (info.x == entityPlayer.spawnStoneX && info.y == (entityPlayer.spawnStoneY - 1) && info.z == entityPlayer.spawnStoneZ) {
                entityPlayer.spawnStoneWorldId = -999;
                entityPlayer.addChatMessage("已经取消复活传送阵");
                return this.dropBlockAsEntityItem(info, this.createStackedBlock(info.getMetadata()));
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        EntityPlayer entityPlayer = info.getResponsiblePlayer();
        if (entityPlayer != null && entityPlayer.spawnStoneWorldId != -999) {
            if (info.x == entityPlayer.spawnStoneX && info.y == (entityPlayer.spawnStoneY - 1) && info.z == entityPlayer.spawnStoneZ) {
                entityPlayer.spawnStoneWorldId = -999;
                entityPlayer.addChatMessage("已经取消复活传送阵");
                return super.dropBlockAsEntityItem(info);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

//    public void onBlockAboutToBeBroken(BlockBreakInfo info) {
//        EntityPlayer entityPlayer = info.getResponsiblePlayer();
//
//    }

//    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer entityPlayer) {
//        if(!par1World.isRemote) {
//            if (entityPlayer != null) {
//                if (entityPlayer.spawnStoneWorldId != -999) {
//                    if (par2 == entityPlayer.spawnStoneX && par3 == (entityPlayer.spawnStoneY - 1) && par4 == entityPlayer.spawnStoneZ) {
//                        this.setBlockHardness(2F);
//                        this.setMinHarvestLevel(2);
//                    } else {
//                        if(entityPlayer.getHeldItem() instanceof ItemArmor || entityPlayer.getHeldItem() instanceof ItemWarHammer) {
//                            ItemStack item_stack_to_drop = entityPlayer.getHeldItemStack();
//                            entityPlayer.dropPlayerItem(item_stack_to_drop);
//                        }
//                    }
//                } else {
//                    if(entityPlayer.getHeldItem() instanceof ItemArmor || entityPlayer.getHeldItem() instanceof ItemWarHammer) {
//                        ItemStack item_stack_to_drop = entityPlayer.getHeldItemStack();
//                        entityPlayer.dropPlayerItem(item_stack_to_drop);
//                    }
//                }
//            }
//        }
//    }

//    @Override
//    public boolean onBlockPlacedMITE(World world, int x, int y, int z, int metadata, Entity placer, boolean test_only) {
//        if(world.isWorldServer()) {
//            EntityPlayer entityPlayer = (EntityPlayer) placer;
//            if(entityPlayer != null) {
//                if(entityPlayer.spawnStoneWorldId == -999) {
//                    entityPlayer.spawnStoneX = x;
//                    entityPlayer.spawnStoneY = y + 1;
//                    entityPlayer.spawnStoneZ = z;
//                    entityPlayer.setSpawnStoneWorldId(world.provider.dimensionId);
//                } else {
//                    entityPlayer.addChatMessage("已经放置复活传送阵，请勿重复放置");
//                    return false;
//                }
//            }
//        }
//        return super.onBlockPlacedMITE(world, x, y, z, metadata, placer, test_only);
//    }


    public boolean tryPlaceBlock(World world, int x, int y, int z, EnumFace face, int metadata, Entity placer, boolean perform_placement_check, boolean drop_existing_block, boolean test_only) {
        boolean isSuccess = super.tryPlaceBlock(world, x, y, z, face, metadata, placer, perform_placement_check, drop_existing_block, test_only);
        if(!world.isRemote) {
            EntityPlayer entityPlayer = (EntityPlayer) placer;
            if(entityPlayer != null && isSuccess && world.getBlock(x, y, z).blockID == Blocks.blockSpawn.blockID) {
                if(entityPlayer.spawnStoneWorldId == -999) {
                    entityPlayer.spawnStoneX = x;
                    entityPlayer.spawnStoneY = y + 1;
                    entityPlayer.spawnStoneZ = z;
                    entityPlayer.setSpawnStoneWorldId(world.provider.dimensionId);
                    entityPlayer.addChatMessage("复活传送阵已生效");
                } else {
                    entityPlayer.addChatMessage("已经放置复活传送阵，请勿重复放置");
                    world.setBlockToAir(x, y ,z);
                    return false;
                }
            }
        }
        return isSuccess;
    }
}
