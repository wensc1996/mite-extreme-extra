package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.world.WorldGenTreesWithTreeId;

import java.util.Random;

public class BlockSapling1  extends BlockPlant {
    public static final String[] WOOD_TYPES = new String[]{"maple", "cherry", "maple", "maple"};
    private IIcon[] unlocalizedName;
    public static final int OAK_TREE = 0;
    public static final int SPRUCE_TREE = 1;
    public static final int BIRCH_TREE = 2;
    public static final int JUNGLE_TREE = 3;

    public BlockSapling1(int par1) {
        super(par1);
        float var2 = 0.4F;
        this.setBlockBoundsForAllThreads((double)(0.5F - var2), 0.0, (double)(0.5F - var2), (double)(0.5F + var2), (double)(var2 * 2.0F), (double)(0.5F + var2));
        this.setMaxStackSize(16);
        this.setCreativeTab(CreativeModeTab.tabDecorations);
        this.setCushioning(0.2F);
        this.setHardness(0.02F);
        this.setStepSound(soundGrassFootstep);
        this.setUnlocalizedName("sapling1");
        this.setTextureName("sapling1");
    }

    public boolean updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (super.updateTick(par1World, par2, par3, par4, par5Random)) {
            return true;
        } else {
            return par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(28) == 0 ? this.markOrGrowMarked(par1World, par2, par3, par4, par5Random) : false;
        }
    }

    public IIcon a(int par1, int par2) {
        return this.unlocalizedName[this.getBlockSubtype(par2)];
    }

    public boolean markOrGrowMarked(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!canGrowInBiome(this.getItemSubtype(par1World.getBlockMetadata(par2, par3, par4)), par1World.getBiomeGenForCoords(par2, par4))) {
            return false;
        } else {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);
            if ((var6 & 8) == 0) {
                par1World.setBlockMetadata(par2, par3, par4, var6 | 8, 4);
                return true;
            } else {
                this.growTree(par1World, par2, par3, par4, par5Random);
                return par1World.getBlock(par2, par3, par4) != this || par1World.getBlockMetadata(par2, par3, par4) != var6;
            }
        }
    }

    private void growTree(World par1World, int par2, int par3, int par4, Random par5Random) {
        int var6 = par1World.getBlockMetadata(par2, par3, par4) & 3;
        Object var7 = new WorldGenTreesWithTreeId(false, 4, Blocks.wood1, var6, Blocks.leaves1, var6, false);
        int var8 = 0;
        int var9 = 0;
        boolean var10 = false;

        if (var10) {
            par1World.setBlock(par2 + var8, par3, par4 + var9, 0, 0, 4);
            par1World.setBlock(par2 + var8 + 1, par3, par4 + var9, 0, 0, 4);
            par1World.setBlock(par2 + var8, par3, par4 + var9 + 1, 0, 0, 4);
            par1World.setBlock(par2 + var8 + 1, par3, par4 + var9 + 1, 0, 0, 4);
        } else {
            par1World.setBlock(par2, par3, par4, 0, 0, 4);
        }

        if (!((WorldGenerator)var7).generate(par1World, par5Random, par2 + var8, par3, par4 + var9)) {
            if (var10) {
                par1World.setBlock(par2 + var8, par3, par4 + var9, this.blockID, var6, 4);
                par1World.setBlock(par2 + var8 + 1, par3, par4 + var9, this.blockID, var6, 4);
                par1World.setBlock(par2 + var8, par3, par4 + var9 + 1, this.blockID, var6, 4);
                par1World.setBlock(par2 + var8 + 1, par3, par4 + var9 + 1, this.blockID, var6, 4);
            } else {
                par1World.setBlock(par2, par3, par4, this.blockID, var6, 4);
            }
        }

    }

    public boolean isSameSapling(World par1World, int par2, int par3, int par4, int par5) {
        return par1World.getBlockId(par2, par3, par4) == this.blockID && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
    }

    public String getMetadataNotes() {
        return "Bits 1 and 2 used for subtype, bit 8 used for (one) intermediate growth stage";
    }

    public boolean isValidMetadata(int metadata) {
        return metadata >= 0 && metadata < 4 || metadata >= 8 && metadata < 12;
    }

    public int getBlockSubtypeUnchecked(int metadata) {
        return metadata & 3;
    }

    public void a(mt par1IconRegister) {
        this.unlocalizedName = new IIcon[WOOD_TYPES.length];

        for(int var2 = 0; var2 < this.unlocalizedName.length; ++var2) {
            this.unlocalizedName[var2] = par1IconRegister.a(this.E() + "_" + WOOD_TYPES[var2]);
        }

    }

    public static boolean canGrowInBiome(int subtype, BiomeBase biome) {
        if (!biome.hasRainfall()) {
            return false;
        } else if (subtype == 0 || subtype == 1) {
            return biome.temperature >= 0.4F;
        }else {
            return true;
        }
    }
}
