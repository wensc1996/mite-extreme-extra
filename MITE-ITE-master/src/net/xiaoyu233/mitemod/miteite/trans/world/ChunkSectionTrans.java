package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkSection.class)
public class ChunkSectionTrans {
    @Redirect(method = {
            "setExtBlockID",
    }, at = @At(value = "INVOKE", target = "Lnet/minecraft/Debug;setErrorMessage(Ljava/lang/String;)V"))
    public void deletePrintBlockIdMessage(String message) {

    }
}
