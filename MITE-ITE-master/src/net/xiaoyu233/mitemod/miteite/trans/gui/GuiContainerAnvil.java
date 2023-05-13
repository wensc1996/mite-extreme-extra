package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(awz.class)
public class GuiContainerAnvil extends awy implements ICrafting {
    public EntityPlayer player;
    @Shadow
    private ContainerAnvil u;
    @Shadow
    private TileEntityAnvil tile_entity_anvil;

    @Inject(method = "<init>",at = @At("RETURN"))
    public void GuiContainerAnvilTrans(EntityPlayer player, int par3, int par4, int par5, CallbackInfo callbackInfo)  {
        this.player = player;
    }

    public GuiContainerAnvil(Container par1Container) {
        super(par1Container);
    }
    @Shadow
    public void updateCraftingInventory(Container container, List list) {
    }
    @Shadow
    public void sendSlotContents(Container container, int i, ItemStack itemStack) {
    }
    @Shadow
    public void sendProgressBarUpdate(Container container, int i, int i1) {
    }
    @Shadow
    protected void a(float v, int i, int i1) {
    }

    protected void b(int par1, int par2) {
        GL11.glDisable(2896);
        this.o.b(this.tile_entity_anvil.getCustomInvNameOrTranslated(), 60, 6, 4210752);
        if(this.u.getSlot(0).getHasStack() && this.u.getSlot(1).getHasStack()) {
            ItemStack var1 = this.u.getSlot(0).getStack();
            ItemStack var2 = this.u.getSlot(1).getStack();
            if(var2.getItem() instanceof ItemEnchantedBook) {
                if(!this.player.isOp()) {
                    int expSums = (var1.getEnhanceTotalLevel() + var2.getEnhanceTotalLevel()) * Configs.wenscConfig.enhancePerLvlCostExp.ConfigValue;
                    if(this.player.experience < expSums) {
                        this.o.b(LocaleI18n.translateToLocal("gui.containerAnvil.needXp") + ":" + expSums, 93, 70, 16720896);
                    } else {
                        this.o.b(LocaleI18n.translateToLocal("gui.containerAnvil.subXp") + ":" + expSums, 93, 70, 7048739);
                    }
                }
            }
        }
        GL11.glEnable(2896);
    }
}
