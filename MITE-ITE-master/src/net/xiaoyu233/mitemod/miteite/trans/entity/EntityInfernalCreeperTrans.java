package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.DamageSource;
import net.minecraft.EntityInfernalCreeper;
import net.minecraft.Item;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityInfernalCreeper.class)
public class EntityInfernalCreeperTrans extends EntityCreeperTrans {
   public EntityInfernalCreeperTrans(World world) {
      super(world);
      this.setSize(this.width* getScale(), this.height* getScale());
   }

   @Overwrite
   public float getNaturalDefense(DamageSource damage_source) {
      if (Configs.wenscConfig.infernalCreeperBoost.ConfigValue) {
         if (damage_source.bypassesMundaneArmor()) {
            if (this.getWorld() != null) {
               return super.getNaturalDefense(damage_source) + (float) this.getWorld().getDayOfOverworld() * 0.075F;
            }
            return super.getNaturalDefense(damage_source);
         }
         if (this.getWorld() != null) {
            return super.getNaturalDefense(damage_source) + 2.0F + (float) this.getWorld().getDayOfOverworld() * 0.075F;
         }
         return super.getNaturalDefense(damage_source) + 2.0F;
      }
      return super.getNaturalDefense(damage_source) + (damage_source.bypassesMundaneArmor() ? 0F : 2.0F);
   }

   @Overwrite
   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      if (recently_hit_by_player){
         this.dropItem(Items.voucherDoor);
      }
      int num_drops = this.rand.nextInt(4);
      if (num_drops == 0) {
         num_drops = this.rand.nextInt(3);
      }

      int fortune = damage_source.getLootingModifier();
      if (fortune > 0) {
         num_drops += this.rand.nextInt(fortune + 1);
      }

      if (num_drops > 0 && !recently_hit_by_player) {
         num_drops -= this.rand.nextInt(num_drops + 1);
      }

      for(int i = 0; i < num_drops; ++i) {
         this.dropItem(this.getDropItemId(), 1);
      }
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectCtorModifyExplosion(CallbackInfo callbackInfo){
      if (Configs.wenscConfig.infernalCreeperBoost.ConfigValue) {
         this.explosionRadius *= 2.0F;
      }
   }
}
