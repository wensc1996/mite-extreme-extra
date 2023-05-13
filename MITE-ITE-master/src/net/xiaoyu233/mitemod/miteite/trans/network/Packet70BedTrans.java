package net.xiaoyu233.mitemod.miteite.trans.network;


import net.minecraft.Packet70Bed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Packet70Bed.class)
public class Packet70BedTrans {
    public Packet70BedTrans(int par1, int par2) {
    }

    @Redirect(
            method = {"<clinit>"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z"
            )
    )
    private static boolean redirectDevModeS() {
        return true;
    }

    @Redirect(
            method = {"<init>(II)V"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z"
            )
    )
    private boolean redirectDevMode() {
        return true;
    }
}
