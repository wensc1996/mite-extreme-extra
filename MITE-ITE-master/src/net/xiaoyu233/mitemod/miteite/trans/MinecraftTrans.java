package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.EnumChatFormat;
import net.minecraft.Minecraft;
import net.minecraft.client.main.Main;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Minecraft.class)
public abstract class MinecraftTrans {

    @Overwrite
    public static String getVersionDescriptor(boolean include_formatting) {
        String red = include_formatting ? EnumChatFormat.RED.toString() : "";
        return "1.6.4-MITE" + "-INNN" + (Main.is_MITE_DS ? "-DS" : "") + (Minecraft
                .inDevMode() ? red + " DEV" : "");
    }

    @ModifyConstant(method = {
            "W",
    }, constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 256))
    private static int injected(int value) {
        return 1024;
    }

}
