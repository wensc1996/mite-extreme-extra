package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockSapling1;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Slot.class)
public class SlotTrans {
    @Overwrite
    public static boolean isLargeItem(Item item) {
        if (item instanceof ItemBlock) {
            Block block = ((ItemBlock)item).getBlock();
            return !(block instanceof BlockTorch)
                    && !(block instanceof BlockSapling || block instanceof BlockSapling1) && !(block instanceof BlockFlower)
                    && !(block instanceof BlockLongGrass) && !(block instanceof BlockMushroom)
                    && !(block instanceof BlockButtonAbstract)
                    && !(block instanceof BlockWaterLily) && !(block instanceof BlockVine);
        } else {
            return item instanceof ItemDoor || item instanceof ItemBoat || item instanceof ItemBed;
        }
    }
}
