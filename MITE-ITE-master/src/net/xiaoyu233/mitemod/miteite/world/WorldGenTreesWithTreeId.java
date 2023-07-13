package net.xiaoyu233.mitemod.miteite.world;

import net.minecraft.Block;
import net.minecraft.Direction;
import net.minecraft.World;
import net.minecraft.WorldGenerator;

import java.util.Random;

public class WorldGenTreesWithTreeId extends WorldGenerator {
    private final int minTreeHeight;
    private final boolean vinesGrow;

    private final Block wood;
    private final int metaWood;
    private final Block leaves;
    private final int metaLeaves;

    public WorldGenTreesWithTreeId(boolean par1) {
        this(par1, 4, Block.wood, 0, Block.leaves, 0, false);
    }

    public WorldGenTreesWithTreeId(boolean par1, int minTreeHeight,Block wood, int metaWood, Block leaves, int metaLeaves, boolean vinesGrow) {
        super(par1);
        this.minTreeHeight = minTreeHeight;
        this.wood = wood;
        this.metaWood = metaWood;
        this.leaves = leaves;
        this.metaLeaves = metaLeaves;
        this.vinesGrow = vinesGrow;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var6 = par2Random.nextInt(3) + this.minTreeHeight;
        boolean var7 = true;
        if (par4 >= 1 && par4 + var6 + 1 <= 256) {
            int var8;
            byte var9;
            int var11;
            int var12;
            int radius;
            for(var8 = par4; var8 <= par4 + 1 + var6; ++var8) {
                var9 = 1;
                if (var8 == par4) {
                    var9 = 0;
                }

                if (var8 >= par4 + 1 + var6 - 2) {
                    var9 = 2;
                }

                for(radius = par3 - var9; radius <= par3 + var9 && var7; ++radius) {
                    for(var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11) {
                        if (var8 >= 0 && var8 < 256) {
                            var12 = par1World.getBlockId(radius, var8, var11);
                            if (var12 != 0 && var12 != leaves.blockID && var12 != Block.grass.blockID && var12 != Block.dirt.blockID && var12 != wood.blockID) {
                                var7 = false;
                            }
                        } else {
                            var7 = false;
                        }
                    }
                }
            }

            if (!var7) {
                return false;
            } else {
                radius = 0;
                var8 = par1World.getBlockId(par3, par4 - 1, par5);
                if ((var8 == Block.grass.blockID || var8 == Block.dirt.blockID) && par4 < 256 - var6 - 1) {
                    this.setBlock(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
                    var9 = 3;
                    byte var19 = 0;

                    int var13;
                    int var14;
                    int var15;
                    for(var11 = par4 - var9 + var6; var11 <= par4 + var6; ++var11) {
                        var12 = var11 - (par4 + var6);
                        var13 = var19 + 1 - var12 / 2;
                        if (var13 > radius) {
                            radius = var13;
                        }

                        for(var14 = par3 - var13; var14 <= par3 + var13; ++var14) {
                            var15 = var14 - par3;

                            for(int var16 = par5 - var13; var16 <= par5 + var13; ++var16) {
                                int var17 = var16 - par5;
                                if (var15 != var13 || -var15 != var13 || var17 != var13 || -var17 != var13 || par2Random.nextInt(2) != 0 && var12 != 0) {
                                    int var18 = par1World.getBlockId(var14, var11, var16);
                                    if (var18 == 0 || var18 == leaves.blockID) {
                                        this.setBlockAndMetadata(par1World, var14, var11, var16, leaves.blockID, this.metaLeaves);
                                    }
                                }
                            }
                        }
                    }

                    for(var11 = 0; var11 < var6; ++var11) {
                        var12 = par1World.getBlockId(par3, par4 + var11, par5);
                        if (var12 == 0 || var12 == leaves.blockID) {
                            this.setBlockAndMetadata(par1World, par3, par4 + var11, par5, wood.blockID, this.metaWood);
                            if (this.vinesGrow && var11 > 0) {
                                if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var11, par5)) {
                                    this.setBlockAndMetadata(par1World, par3 - 1, par4 + var11, par5, Block.vine.blockID, 8);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var11, par5)) {
                                    this.setBlockAndMetadata(par1World, par3 + 1, par4 + var11, par5, Block.vine.blockID, 2);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 - 1)) {
                                    this.setBlockAndMetadata(par1World, par3, par4 + var11, par5 - 1, Block.vine.blockID, 1);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 + 1)) {
                                    this.setBlockAndMetadata(par1World, par3, par4 + var11, par5 + 1, Block.vine.blockID, 4);
                                }
                            }
                        }
                    }

                    if (this.vinesGrow) {
                        for(var11 = par4 - 3 + var6; var11 <= par4 + var6; ++var11) {
                            var12 = var11 - (par4 + var6);
                            var13 = 2 - var12 / 2;

                            for(var14 = par3 - var13; var14 <= par3 + var13; ++var14) {
                                for(var15 = par5 - var13; var15 <= par5 + var13; ++var15) {
                                    if (par1World.getBlockId(var14, var11, var15) == leaves.blockID) {
                                        if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14 - 1, var11, var15) == 0) {
                                            this.growVines(par1World, var14 - 1, var11, var15, 8);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14 + 1, var11, var15) == 0) {
                                            this.growVines(par1World, var14 + 1, var11, var15, 2);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14, var11, var15 - 1) == 0) {
                                            this.growVines(par1World, var14, var11, var15 - 1, 1);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14, var11, var15 + 1) == 0) {
                                            this.growVines(par1World, var14, var11, var15 + 1, 4);
                                        }
                                    }
                                }
                            }
                        }

                        if (par2Random.nextInt(5) == 0 && var6 > 5) {
                            for(var11 = 0; var11 < 2; ++var11) {
                                for(var12 = 0; var12 < 4; ++var12) {
                                    if (par2Random.nextInt(4 - var11) == 0) {
                                        var13 = par2Random.nextInt(3);
                                        this.setBlockAndMetadata(par1World, par3 + Direction.offsetX[Direction.rotateOpposite[var12]], par4 + var6 - 5 + var11, par5 + Direction.offsetZ[Direction.rotateOpposite[var12]], Block.cocoaPlant.blockID, var13 << 2 | var12);
                                    }
                                }
                            }
                        }
                    }

                    if (par1World.decorating) {
                        par1World.placeNaturallyOccurringSnow(par3 - radius, par5 - radius, par3 + radius, par5 + radius);
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private void growVines(World par1World, int par2, int par3, int par4, int par5) {
        this.setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
        int var6 = 4;

        while(true) {
            --par3;
            if (par1World.getBlockId(par2, par3, par4) != 0 || var6 <= 0) {
                return;
            }

            this.setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
            --var6;
        }
    }
}
