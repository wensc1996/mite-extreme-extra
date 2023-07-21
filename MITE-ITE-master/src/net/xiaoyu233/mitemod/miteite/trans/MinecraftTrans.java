package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.*;
import net.minecraft.client.main.Main;
import net.xiaoyu233.mitemod.miteite.gui.GuiShop;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

@Mixin(Minecraft.class)
public abstract class MinecraftTrans {
    @Shadow
    public aul u;
    @Shadow
    public ClientPlayer h;

    @Overwrite
    public static String getVersionDescriptor(boolean include_formatting) {
        String red = include_formatting ? EnumChatFormat.RED.toString() : "";
        return "1.6.4-MITE" + "-Extreme-" + Constant.MITE_ITE_VERSION + (Main.is_MITE_DS ? "-DS" : "") + " overwrite by wensc,洛小雨" + (Minecraft
                .inDevMode() ? red + " DEV" : "");
    }

    @ModifyConstant(method = {
            "W",
    }, constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }

    @Shadow
    public void a(awe par1GuiScreen) {}

    @Inject(method = "k", at = @At(value = "FIELD", target = "Lnet/minecraft/aul;n:I", ordinal = 1, shift = At.Shift.AFTER))
    public void injectOpenShopGUI(CallbackInfo ci) {
        while(this.u.keyBindShop.c()) {
            ByteArrayOutputStream var3 = new ByteArrayOutputStream();
            DataOutputStream var4 = new DataOutputStream(var3);
            try {
                var4.writeBoolean(true);
                this.h.netClientHandler.c(new Packet250CustomPayload("MC|OpenShopGUI", var3.toByteArray()));
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }
    }
}
