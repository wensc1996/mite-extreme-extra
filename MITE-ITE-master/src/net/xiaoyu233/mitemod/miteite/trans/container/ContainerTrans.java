//package net.xiaoyu233.mitemod.miteite.trans.container;
//
//import net.minecraft.Container;
//import net.minecraft.EntityPlayer;
//import net.minecraft.ItemStack;
//import net.minecraft.Minecraft;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(Container.class)
//public abstract class ContainerTrans {
//    @Shadow @Final public EntityPlayer player;
//
//    @Inject(method = "slotClick", at=@At("HEAD"))
//    public final ItemStack slotClick(int par1, int par2, int par3, boolean holding_shift, EntityPlayer par4EntityPlayer, CallbackInfoReturnable <ItemStack> callbackInfoReturnable) {
//        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//        for(StackTraceElement stackTraceElement : stackTraceElements) {
//            Minecraft.setErrorMessage("调用链:"+stackTraceElement.toString());
//        }
//        this.player.addChatMessage("par1:"+par1+" par2:" +par2+" par3:" + par3);
//        return null;
//    };
//}
