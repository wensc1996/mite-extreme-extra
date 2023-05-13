package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityEnderman.class)
public class EntityEndermanTrans extends EntityMonster {
   public EntityEndermanTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(100.0D + day / 20D);
      this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.3D);
      this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(30.0D + day / 20D);
   }

   @Overwrite
   protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
      if (recently_hit_by_player){
         this.dropItem(Items.voucherWitch);
      }
      int item_id = this.getDropItemId();
      if (item_id > 0) {
         int num_drops = this.rand.nextInt(2 + damage_source.getLootingModifier());

         for(int i = 0; i < num_drops; ++i) {
            this.dropItem(item_id, 1);
         }
      }
   }

   public EntityDamageResult attackEntityAsMob(Entity target) {
      this.worldObj.setEntityState(this, EnumEntityState.golem_throw);
      EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this), (float)this.getEntityAttributeValue(GenericAttributes.attackDamage)));
      if (result != null && result.entityWasKnockedBack()) {
         target.motionY += 0.6D;
      }

      return result;
   }

}
