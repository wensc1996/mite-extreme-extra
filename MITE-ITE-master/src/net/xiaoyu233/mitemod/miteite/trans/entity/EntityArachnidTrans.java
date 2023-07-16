package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityArachnid.class)
public class EntityArachnidTrans extends EntityMonster {
    public EntityArachnidTrans(World par1World) {
        super(par1World);
    }


    @Shadow
    int num_webs;

    protected void dropFewItemsArachnid(boolean recently_hit_by_player, DamageSource damage_source){
    }

    @Overwrite
    protected final void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        this.dropFewItemsArachnid(recently_hit_by_player, damage_source);
        if (recently_hit_by_player) {
            while(this.num_webs-- > 0) {
                this.dropItemStack(new ItemStack(Item.silk.itemID));
            }
        }

        if (recently_hit_by_player && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + damage_source.getButcheringModifier()) > 0)) {
            this.dropItem(Item.spiderEye.itemID, 1);
        }
    }

    @Overwrite
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.maxHealth, 10.0D);
        this.setEntityAttribute(GenericAttributes.followRange, 28.0D);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 1.0D);
        this.setEntityAttribute(GenericAttributes.attackDamage, 6.0D);
    }
}
