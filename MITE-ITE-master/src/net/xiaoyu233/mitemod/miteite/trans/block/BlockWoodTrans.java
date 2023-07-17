package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockSubtypes;
import net.minecraft.BlockWood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockWood.class)
public class BlockWoodTrans {
    @Shadow
    private BlockSubtypes subtypes ;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void injectInit(CallbackInfo callbackInfo) {
        this.subtypes = new BlockSubtypes(new String[] {"oak", "spruce", "birch", "jungle", "maple", "cherry", "maple", "maple"});
    }

    @Overwrite
    public boolean isValidMetadata(int metadata) {
        return metadata >= 0 && metadata < 8;
    }

    @Overwrite
    public int getBlockSubtypeUnchecked(int metadata) {
        return metadata & 7;
    }

}
