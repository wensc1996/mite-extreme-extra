package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.EnumGamemode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({EnumGamemode.class})
public class EnumGamemodeTrans {
    public EnumGamemodeTrans() {
    }

    @Redirect(
            method = {"getByID", "a"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z"
            )
    )
    private static boolean redirectInDevMode() {
        return true;
    }

    @Redirect(
            method = {"e", "isCreative"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z"
            )
    )
    private boolean redirectInDevModeNS() {
        return true;
    }
}