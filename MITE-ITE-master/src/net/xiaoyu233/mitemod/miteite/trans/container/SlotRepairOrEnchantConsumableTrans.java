package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SlotRepairOrEnchantConsumable.class)
public class SlotRepairOrEnchantConsumableTrans extends Slot {
    public SlotRepairOrEnchantConsumableTrans(IInventory inventory, int slot_index, int display_x, int display_y, BlockAnvil anvil) {
        super(inventory, slot_index, display_x, display_y, true);
    }

    @Overwrite
    public boolean isItemValid(ItemStack item_stack) {
        if (!super.isItemValid(item_stack)) {
            return false;
        } else {
            Item item = item_stack.getItem();
            if (item != Item.enchantedBook && item != Item.bottleOfDisenchanting) {
                return item_stack.isItemStackDamageable() && !item_stack.isItemEnchanted() ? true : item_stack.isRepairItem();
            } else {
                return true;
            }
        }
    }
}
