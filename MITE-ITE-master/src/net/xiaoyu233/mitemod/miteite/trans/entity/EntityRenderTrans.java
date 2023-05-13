package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.objectweb.asm.Opcodes;

@Mixin(value = EntityRenderer.class, priority = 1200)
public class EntityRenderTrans {
    @Shadow
    private Minecraft q;

    public EntityRenderTrans(Minecraft par1Minecraft) {
    }

    @Overwrite
    private float a(EntityPlayer par1EntityPlayer, float par2) {
        MobEffect var3 = par1EntityPlayer.getActivePotionEffect(MobEffectList.nightVision);
        if(var3 != null) {
            int var4 = var3.getDuration();
            return var4 > 200 ? 1.0F :0.7F + MathHelper.sin(((float)var4 - par2) * 3.1415927F * 0.2F) * 0.3F;
        } else {
            return par1EntityPlayer.hasDynamicCore ? 0.2F : 0;
        }
    }

    @Redirect(method = "h",
            at=@At(value = "INVOKE",
                    target = "Lnet/minecraft/ClientPlayer;isPotionActive(Lnet/minecraft/MobEffectList;)Z"))
    public boolean isPotionActiveOrDynamicCore(ClientPlayer caller, MobEffectList par1Potion) {
        return (caller.isPotionActive(par1Potion) || caller.hasDynamicCore);
    }
}
