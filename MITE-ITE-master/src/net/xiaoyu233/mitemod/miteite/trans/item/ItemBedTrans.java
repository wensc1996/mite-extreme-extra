package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.*;
import java.util.List;

@Mixin(ItemBed.class)
public class ItemBedTrans extends Item {
    private IIcon[] iconArray = new IIcon[16];


    @Inject(method = "<init>", at = @At("RETURN"))
    public void  injectStart(int par1, CallbackInfo callbackInfo) {
        this.setTextureName("bed/bed");
    }

    private static int getDyeFromItem(int par0)
    {
        return par0;
    }


    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        if(par1ItemStack != null) {
            int var2 = par1ItemStack.getItemSubtype();

            if (var2 < 0 || var2 >= iconArray.length)
            {
                var2 = 0;
            }

            return super.getUnlocalizedName() + "." + ItemDye.dyeItemNames[getDyeFromItem(var2)];
        } else {
            return "";
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 16; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    public IIcon getIconFromSubtype(int par1)
    {
        if (par1 < 0 || par1 >= iconArray.length)
        {
            par1 = 0;
        }
        return this.iconArray[par1];
    }

    @Override
    public void a(mt par1IconRegister) {
        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.a(this.A() + "_" + ItemDye.dyeItemNames[getDyeFromItem(var2)]);
        }
    }

    @Overwrite
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        RaycastCollision rc = player.getSelectedObject(partial_tick, true);
        ItemStack itemStack = player.getHeldItemStack();
        int subtype = itemStack.getItemSubtype();
        return rc != null && rc.isBlock() ? player.tryPlaceHeldItemAsBlock(rc, Constant.bedBlockTypes[subtype]) : false;
    }
}
