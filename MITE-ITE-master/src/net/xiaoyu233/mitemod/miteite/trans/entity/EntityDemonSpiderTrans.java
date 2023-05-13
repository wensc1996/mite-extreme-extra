package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.*;

@Mixin(EntityDemonSpider.class)
public class EntityDemonSpiderTrans extends EntityArachnid {

    public EntityDemonSpiderTrans(World par1World) {
        super(par1World, 1.0F);
        this.tasks.addTask(4, new EntityAIGetOutOfLava(this, 1.0F));
    }
}
