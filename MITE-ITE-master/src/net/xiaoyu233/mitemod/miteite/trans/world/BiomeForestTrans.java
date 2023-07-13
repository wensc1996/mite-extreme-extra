package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.world.WorldGenTreesWithTreeId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(BiomeForest.class)
public class BiomeForestTrans extends BiomeBase {
    protected BiomeForestTrans(int par1) {
        super(par1);
    }

    @Overwrite
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
        return (WorldGenerator)(par1Random.nextInt(5) == 0 ? this.worldGeneratorForest : (par1Random.nextInt(10) == 0
                ? this.worldGeneratorBigTree
                : par1Random.nextInt(2) == 0 ? this.worldGeneratorTrees : new WorldGenTreesWithTreeId(false, 4, Blocks.wood1, 0, Blocks.leaves1, 0, false)));
    }
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        super.decorate(par1World, par2Random, par3, par4);
        int var5 = Configs.wenscConfig.emeraldFrequencyBigHills.ConfigValue + par2Random.nextInt(3);

        for(int var6 = 0; var6 < var5; ++var6) {
            int var7 = par3 + par2Random.nextInt(16);
            int var8 = par2Random.nextInt(28) + 4;
            int var9 = par4 + par2Random.nextInt(16);
            int var10 = par1World.getBlockId(var7, var8, var9);
            if (var10 == Block.stone.blockID) {
                par1World.setBlock(var7, var8, var9, Block.oreEmerald.blockID, 0, 2);
            }
        }

    }
}
