package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mixin(ItemEnchantedBook.class)
public class ItemEnchantedBookTrans extends Item {

    @Inject(method = "<init>", at=@At("RETURN"))
    public void changeMaterial(CallbackInfo callbackInfo) {
        this.setMaterial(Materials.copper);
    }

    @Shadow
    public void addEnchantment(ItemStack par1ItemStack, EnchantmentInstance par2EnchantmentData) {}

    @Override
    public boolean isHarmedByAcid() {
        return false;
    }

    @Overwrite
    public StructurePieceTreasure func_92112_a(Random par1Random, int par2, int par3, int par4) {
        Object[] hasLevlesEnchantment = Arrays.stream(Enchantment.enchantmentsBookList).filter(enchantment -> enchantment.hasLevels()).toArray();
        Enchantment var5 = (Enchantment) hasLevlesEnchantment[par1Random.nextInt(hasLevlesEnchantment.length)];
        ItemStack var6 = new ItemStack(this.itemID, 1, 0);
        int var7 = MathHelper.getRandomIntegerInRange(par1Random, 1, var5.getNumLevels());
        this.addEnchantment(var6, new EnchantmentInstance(var5, var7));
        return new StructurePieceTreasure(var6, par2, par3, par4);
    }
}
