package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.World;
import net.minecraft.WorldGenBase;
import net.minecraft.WorldGenCaves;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(WorldGenCaves.class)
public class WorldGenCavesTrans extends WorldGenBase {
    @Shadow
    protected void generateCaveNode(long par1, int par3, int par4, byte[] par5ArrayOfByte, double par6, double par8, double par10, float par12, float par13, float par14, int par15, int par16, double par17) {

    }

//    @Overwrite
//    protected void generateLargeCaveNode(long par1, int par3, int par4, byte[] par5ArrayOfByte, double par6, double par8, double par10) {
//        this.generateCaveNode(par1, par3, par4, par5ArrayOfByte, par6, par8, par10, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5);
//    }

    @Redirect(method = "recursiveGenerate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 8))
    public int forceGenerateLargeCave(Random rand,int par1, World par1World, int par2, int par3, int par4, int par5, byte[] par6ArrayOfByte) {
        return 0;
    }
}
