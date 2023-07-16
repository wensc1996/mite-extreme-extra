package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityOcelot.class)
public class EntityOcelotMixin extends EntityTameableAnimal {
    public EntityOcelotMixin(World par1World) {
        super(par1World);
    }

    @Overwrite
    public boolean onEntityRightClicked(EntityPlayer player, ItemStack item_stack) {
        if (super.onEntityRightClicked(player, item_stack)) {
            return true;
        } else if (this.riddenByEntity == null) {
            if (player.onServer()) {
                player.mountEntity(this);
            }

            return true;
        } else {
            return false;
        }
    }
    @Shadow
    protected int getTamingOutcome(EntityPlayer entityPlayer) {
        return 0;
    }
    @Shadow
    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return null;
    }
}
