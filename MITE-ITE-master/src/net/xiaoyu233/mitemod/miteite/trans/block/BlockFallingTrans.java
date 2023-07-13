package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockFalling;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockFalling.class)
public class BlockFallingTrans {
    @Shadow
    protected final boolean tryToFall(World par1World, int par2, int par3, int par4){ return true;};
    public final boolean tryToFallPublic(World par1World, int par2, int par3, int par4){
        return this.tryToFall(par1World, par2, par3, par4);
    }
}
