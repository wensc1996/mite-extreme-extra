package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.Chunk;
import net.minecraft.ChunkSection;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Random;

@Mixin(Chunk.class)
public class ChunkTrans {
    @Shadow
    private final boolean is_empty;
    @Shadow
    public ChunkSection[] storageArrays;

    public ChunkTrans(boolean isEmpty) {
        is_empty = isEmpty;
    }

    @Redirect(method = "<init>(Lnet/minecraft/World;[BII)V",at = @At(value = "INVOKE",target = "Ljava/util/Random;nextInt(I)I"),
            slice = @Slice(
                    from = @At(value = "FIELD",target = "Lnet/minecraft/ChunkProviderUnderworld;bedrock_strata_4_bump_noise:[D"),
                    to = @At(value = "FIELD",target = "Lnet/minecraft/Block;mantleOrCore:Lnet/minecraft/BlockMantleOrCore;",ordinal = 1)))
    private int redirectRandomBedrockNum(Random caller,int bound){
        //In fact this is the height of the mantle blocks
        return Configs.wenscConfig.underworldMantleBlockOffset.ConfigValue;
    }

    @Overwrite
    public final int getBlockIDOptimized(int xz_index, int y) {
        int local_x = xz_index % 16;
        int local_z = xz_index / 16;
        return this.getBlockID(local_x, y, local_z);
    }

    @Overwrite
    public final int getBlockID(int par1, int par2, int par3) {
        if (this.is_empty) {
            return 0;
        } else {
            int par2_shifted = par2 >> 4;
            if (par2_shifted < this.storageArrays.length) {
                ChunkSection extended_block_storage = this.storageArrays[par2_shifted];
                if (extended_block_storage != null) {
                    int par2_and_15 = par2 & 15;
                    int var7 = extended_block_storage.getExtBlockID(par1 & 15, par2_and_15, par3 & 15);
                    return var7;
                }
            }
            return 0;
        }
    }
}
