package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityArachnid.class)
public class EntityArachnidTrans extends EntityMonster {
    public EntityArachnidTrans(World par1World) {
        super(par1World);
    }

    @Inject(method = "dropFewItems", at = @At("HEAD"))
    public void InjectThrowRedEnvelope(boolean recently_hit_by_player, DamageSource damage_source, CallbackInfo ci) {
        if(recently_hit_by_player) {
            if(rand.nextInt(2) == 0) {
                this.dropItemStack(new ItemStack(Items.redEnvelope, 1));
            }
        }
    }
}
