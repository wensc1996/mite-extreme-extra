package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemTrans {
   @Shadow public abstract boolean getHasSubtypes();

   @Shadow public abstract int getNumSubtypes();

   @Shadow private int sugar_content;

   @Shadow public abstract boolean hasQuality();

   @Shadow private int maxDamage;

   @Shadow public abstract boolean isDamageable();

   @Shadow
   private static Item[] itemsList;
   @Shadow
   @Final
   private int itemID;

   @Shadow
   protected List materials;

   public double soldPriceArray[];

   public double buyPriceArray[];

   @ModifyConstant(method = {
           "<init>(ILjava/lang/String;I)V",
   }, constant = @Constant(intValue = 256))
   private static int injected(int value) {
      return 1024;
   }

   private double soldPrice = 0;
   private double price = 0;

   @Inject(method = "<init>()V",at = @At("RETURN"))
   private void injectCtor(CallbackInfo callbackInfo){
      ReflectHelper.dyCast(Item.class,this).recipes = new aah[500];
      if(this.getNumSubtypes() > 0) {
         this.soldPriceArray = new double[this.getNumSubtypes()];
         this.buyPriceArray = new double[this.getNumSubtypes()];
      } else {
         this.soldPriceArray = new double[1];
         this.buyPriceArray = new double[1];
      }
   }

   @Inject(method = "<init>(ILjava/lang/String;I)V" ,at = @At("RETURN"))
   private void ItemInject(int par1, String texture, int num_subtypes, CallbackInfo callbackInfo) {
      ReflectHelper.dyCast(Item.class,this).recipes = new aah[500];
      if(this.getNumSubtypes() > 0) {
         this.soldPriceArray = new double[this.getNumSubtypes()];
         this.buyPriceArray = new double[this.getNumSubtypes()];
      } else {
         this.soldPriceArray = new double[1];
         this.buyPriceArray = new double[1];
      }
   }

   // 向源类进行注入
   public Item setBuyPrice(double price) {
      if(this.getHasSubtypes()) {
         for (int i = 0; i < this.getNumSubtypes(); i++) {
            this.buyPriceArray[i] = price;
         }
      } else {
         this.buyPriceArray[0] = price;
      }
      return (Item) ReflectHelper.dyCast(this);
   }


   // 向源类进行注入
   public Item setSoldPrice(double price) {
      if(this.getHasSubtypes()) {
         for (int i = 0; i < this.getNumSubtypes(); i++) {
            this.soldPriceArray[i] = price;
         }
      } else {
         this.soldPriceArray[0] = price;
      }
      return (Item) ReflectHelper.dyCast(this);
   }

   public boolean hasSoldPrice(int meta) {
      return soldPriceArray[meta] > 0;
   }

   public boolean hasBuyPrice(int meta) {
      return buyPriceArray[meta] > 0;
   }


   public void setSugarContent(int sugarContent){
      this.sugar_content = sugarContent;
   }

   public void addExpForTool(ItemStack stack, EntityPlayer player, int exp) {
      if (Configs.wenscConfig.isActiveSecondaryAttribute.ConfigValue) {
         stack.fixNBT();
         NBTTagCompound tagCompound = stack.stackTagCompound;
         if (tagCompound != null) {
            if (tagCompound.hasKey("tool_exp")) {
               tagCompound.setInteger("tool_exp", tagCompound.getInteger("tool_exp") + exp);
               if (tagCompound.hasKey("tool_level")) {
                  int currentLevel = tagCompound.getInteger("tool_level");
                  int nextLevelExpReq = this.getExpReqForLevel(currentLevel, this.isWeapon(stack.getItem()));
                  if (tagCompound.getInteger("tool_exp") >= nextLevelExpReq) {
                     tagCompound.setInteger("tool_level", currentLevel + 1);
                     tagCompound.setInteger("tool_exp", 0);
                     if (!player.worldObj.isRemote) {
                        player.sendChatToPlayer(ChatMessage.createFromTranslationKey("你的" + stack.getMITEStyleDisplayName() + "已升级,当前等级:" + (currentLevel + 1)).setColor(EnumChatFormat.DARK_AQUA));
                     }

                     if (!tagCompound.hasKey("modifiers")) {
                        tagCompound.setCompoundTag("modifiers", new NBTTagCompound());
                     }

                     this.onItemLevelUp(tagCompound, player, stack);
                  }
               }
            }
         } else {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("tool_exp", 0);
            compound.setInteger("tool_level", 0);
            stack.stackTagCompound = compound;
         }
      }
   }

   public List getMaterials() {
      return materials;
   }

   public int addModifierLevelFor(NBTTagCompound modifiers, ItemModifierTypes modifierType) {
      int effectLevel = modifiers.getInteger(modifierType.getNbtName()) + 1;
      modifiers.setInteger(modifierType.getNbtName(), effectLevel);
      return effectLevel;
   }

   @Overwrite
   public final int getMaxDamage(EnumQuality quality) {
      if (!this.isDamageable()) {
         Minecraft.setErrorMessage("getMaxDamage: item is not damageable, " + this);
      }
      return this.maxDamage;
   }

   @Shadow
   public ItemBlock getAsItemBlock() {
      return null;
   }

   public Multimap<String, AttributeModifier> getAttrModifiers(ItemStack stack) {
      return HashMultimap.create();
   }

   @Shadow
   private int getBurnTime(ItemStack item_stack) {
      return 0;
   }

   public int getCookTime() {
      return this.isBlock() ? 100 * (this.getAsItemBlock().getBlock().getMinHarvestLevel(-1) + 1) : 100;
   }

   public int getExpReqForLevel(int i, boolean weapon) {
      return 0;
   }

   @Overwrite
   public int getHeatLevel(ItemStack item_stack) {
      if (ReflectHelper.dyCast(this) == Items.BLAZE_COAL_POWDER) {
         return 5;
      } else if (ReflectHelper.dyCast(this) == Item.blazeRod) {
         return 4;
      } else {
         return this.getBurnTime(item_stack) > 0 ? 1 : 0;
      }
   }

   @Shadow
   private Material getMaterialForRepairs() {
      return null;
   }

   public float getMeleeDamageBonus(ItemStack stack) {
      return 0.0F;
   }

   @Overwrite
   public Item getRepairItem() {
      Material material_for_repairs = this.getMaterialForRepairs();
      if (material_for_repairs == Material.copper) {
         return Item.copperNugget;
      } else if (material_for_repairs == Material.silver) {
         return Item.silverNugget;
      } else if (material_for_repairs == Material.gold) {
         return Item.goldNugget;
      } else if (material_for_repairs == Material.iron || material_for_repairs == Material.rusted_iron) {
         return Item.ironNugget;
      } else if (material_for_repairs == Material.mithril) {
         return Item.mithrilNugget;
      } else if (material_for_repairs == Material.adamantium) {
         return Item.adamantiumNugget;
      } else if (material_for_repairs == Material.ancient_metal) {
         return Item.ancientMetalNugget;
      } else if (material_for_repairs == Materials.vibranium) {
         return Items.VIBRANIUM_NUGGET;
      } else if (material_for_repairs == Materials.redstone) {
         return Items.redstone;
      } else {
         return null;
      }
   }

   public String getResourceLocationPrefix() {
      return "";
   }

   public float getStrVsBlock(Block block, int metadata, ItemStack itemStack, EntityPlayer user) {
      return 0.0F;
   }

   public int getToolLevel(ItemStack itemStack) {
      return itemStack.stackTagCompound != null ? itemStack.getTagCompound().getInteger("tool_level") : 0;
   }

   public boolean hasExpAndLevel() {
      return false;
   }

   @Shadow
   public boolean isBlock() {
      return false;
   }

   public boolean isMaxToolLevel(ItemStack itemStack) {
      return false;
   }

   public boolean isWeapon(Item b) {
      return false;
   }

   public void onItemLevelUp(NBTTagCompound tagCompound, EntityPlayer player, ItemStack stack) {
   }

   @Shadow
   public Item setCreativeTab(CreativeModeTab table) {
      return null;
   }

   public void setCreativeTable(CreativeModeTab table) {
      this.setCreativeTab(table);
   }

   @Shadow
   public Item setMaxStackSize(int maxStackSize) {
      return null;
   }

   public void setResourceLocation(String location) {
      this.setTextureName(location);
   }

   @Shadow
   public Item setTextureName(String location) {
      return null;
   }
}
