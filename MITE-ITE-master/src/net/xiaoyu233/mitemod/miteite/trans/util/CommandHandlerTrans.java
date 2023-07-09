package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandHandler.class)
public class CommandHandlerTrans {
    @Redirect(method = {"isUserPrivileged"} , at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z"))
    public boolean injectDevJudge(EntityPlayer player) {
        return Minecraft.inDevMode() || (player != null && player.isOp());
    }

    @Redirect(method = {"executeCommand"} , at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z"))
    public boolean injectDevJudge2(ICommandListener par1ICommandSender, String par2Str, boolean permission_override) {
        WorldServer world = (WorldServer)par1ICommandSender.getEntityWorld();
        ServerPlayer player = (ServerPlayer)world.getPlayerEntityByName(par1ICommandSender.getCommandSenderName());
        return Minecraft.inDevMode() || (player != null && player.isOp());
    }
}
