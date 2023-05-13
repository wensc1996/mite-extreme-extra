package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.Minecraft;
import net.minecraft.PlayerInteractManager;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({PlayerInteractManager.class})
public class PlayerInteractManagerTrans {
    @Shadow
    public ServerPlayer thisPlayerMP;

    public PlayerInteractManagerTrans() {
    }

    @Redirect(
            method = {"setGameType", "getGameType", "isCreative", "initializeGameType"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z"
            )
    )
    private boolean redirectInDevMode() {
        return this.thisPlayerMP.isOp() || Minecraft.inDevMode();
    }
}