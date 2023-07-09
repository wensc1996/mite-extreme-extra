package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Arrays;

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
            return (float) par1EntityPlayer.dynamicCoreLevel * 0.2f;
        }
    }

    @Redirect(method = "h",
            at=@At(value = "INVOKE",
                    target = "Lnet/minecraft/ClientPlayer;isPotionActive(Lnet/minecraft/MobEffectList;)Z"))
    public boolean isPotionActiveOrDynamicCore(ClientPlayer caller, MobEffectList par1Potion) {
        return (caller.isPotionActive(par1Potion) || caller.dynamicCoreLevel > 0);
    }

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD, method = "g(F)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/bdd;getBlockId(III)I", shift = At.Shift.AFTER))
    public void judgeBed(float par1, CallbackInfo ci, EntityLiving var2, float var3, double var4, double var6, double var8, int var10) {
        if(Arrays.stream(Constant.bedBlockTypes).anyMatch(e -> e.blockID == var10 && e.blockID != Block.bed.blockID)) {
            int var11 = this.q.f.getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
            int var12 = var11 & 3;
            GL11.glRotatef((float)(var12 * 90), 0.0F, 1.0F, 0.0F);
        }
    }
}
