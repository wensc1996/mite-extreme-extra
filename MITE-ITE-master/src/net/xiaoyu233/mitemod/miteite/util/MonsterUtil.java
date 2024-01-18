package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.EnchantmentInstance;
import net.minecraft.EntityInsentient;
import net.minecraft.ItemStack;

import java.util.List;
import java.util.Random;

import static net.minecraft.EnchantmentManager.getEnchantmentLevelsAlteredByItemEnchantability;

public class MonsterUtil {
    public static void addDefaultArmor(int day_count, EntityInsentient monster, boolean haveAll) {
       Random rand = monster.getRNG();
        day_count += monster.worldObj.isUnderworld() ? 8 : 0;
        for(int index = 4; index > 0; --index) {
            int weight = day_count / 8 + rand.nextInt(7) - 3;
            int armorIndex = Math.max(Math.min(weight, Constant.ARMORS[index - 1].length - 1),0);
            monster.setCurrentItemOrArmor(index, (new ItemStack(Constant.ARMORS[index - 1][armorIndex])).randomizeForMob(monster, day_count > 64));
        }
    }

    public static ItemStack addRandomEnchantment(Random par0Random, ItemStack itemStack, int enchantment_levels,int maxTotalLevel,int maxEnchantmentNum) {
        enchantment_levels = getEnchantmentLevelsAlteredByItemEnchantability(enchantment_levels, itemStack.getItem());
        if (enchantment_levels >= 1) {
            List<EnchantmentInstance> enchantmentList = EnchantmentUtil.buildEnchantmentList(par0Random, itemStack, enchantment_levels,maxTotalLevel, maxEnchantmentNum);
            if (enchantmentList != null) {
                for (EnchantmentInstance enchantment : enchantmentList) {
                    itemStack.addEnchantment(enchantment.enchantmentobj, enchantment.enchantmentLevel);
                }
            }

        }
        return itemStack;
    }

    public static int getRandomItemTier(Random rand,int day_count){
        return getRandomItemTier(rand, Math.min(10, day_count / 16), rand.nextInt(2 + Math.min(day_count / 64, 6)) + 1, day_count);
    }

    public static int getRandomItemTier(Random random, int maxTier, int minTier, int dayCount) {
       int now = minTier;
       while (now < maxTier && random.nextInt(Math.max(2 * now + 1 - dayCount / 12, 1)) == 0) {
          ++now;
       }
       return now;
    }
}
