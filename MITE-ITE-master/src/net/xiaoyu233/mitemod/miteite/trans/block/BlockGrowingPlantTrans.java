package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.BlockGrowingPlant;
import net.minecraft.BlockPlant;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockGrowingPlant.class)
public abstract class BlockGrowingPlantTrans extends BlockPlant {
    public BlockGrowingPlantTrans(int block_id) {
        super(block_id);
        this.setMaxStackSize(8);
    }

    @Overwrite
    public float getGlobalGrowthRateModifierFromMITE() {
        return Configs.wenscConfig.plantGrowthRate.ConfigValue;
    }
}
