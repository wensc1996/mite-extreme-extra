package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.relaunch.server.Main;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.BlockSpawn;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieBoss;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.*;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import net.xiaoyu233.mitemod.miteite.network.SPacketCraftingBoost;
import net.xiaoyu233.mitemod.miteite.network.SPacketOverlayMessage;
import net.xiaoyu233.mitemod.miteite.util.BlockPos;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerTrans extends EntityLiving implements ICommandListener {
   @Shadow public abstract void mountEntity(Entity par1Entity);

   @Shadow public abstract World getEntityWorld();

   @Shadow public abstract void addSkill(Skill skill);

   @Shadow public abstract EntityDamageResult attackEntityFrom(Damage damage);

   @Shadow public abstract void addChatMessage(String par1Str);

   @Shadow public int experience;

   @Shadow public abstract ItemStack getCurrentArmor(int par1);

   @Shadow public abstract boolean tryPlaceHeldItemAsBlock(RaycastCollision rc, Block block);

   @Shadow public abstract void convertOneOfHeldItem(ItemStack created_item_stack);

   private final Map<Entity, Integer> attackCountMap = new HashMap<>();
   @Shadow
   public ChunkCoordinates bed_location;
   @Shadow
   public long block_placement_tick;
   @Shadow
   public boolean collided_with_gelatinous_cube;
   @Shadow
   public EnumConsciousState conscious_state;
   @Shadow
   public int countdown_to_mark_all_nearby_chunks_for_render_update;
   @Shadow
   public PlayerInventory inventory = new PlayerInventory((EntityPlayer) ReflectHelper.dyCast(this));
   @Shadow
   public HashMap stats;
   @Shadow
   public boolean suppress_next_arm_swing;
   @Shadow
   public float vision_dimming;
   @Shadow
   List tentative_bounding_boxes;
   private float craftingBoostFactor;
   private int craftingBoostTimer;
   private BlockPos currentEffectedBeaconPos;
   private int inRainCounter;
   private int netherDebuffTime;
   private int underworldDebuffTime;
   private int underworldRandomTeleportTime;
   private volatile boolean waitForItemSync;
   private boolean cooldownEmergencyNextTick;
   public boolean isFirstLogin = true;
   private boolean isOp;
   private  boolean preventInWallDamage = false;

   private int surroundHurtCollDown = 20;

   public int spawnStoneX;
   public int spawnStoneY;
   public int spawnStoneZ;
   public int spawnStoneWorldId = -999;

   public int storeTorchTick = 0;

   public boolean hasDynamicCore = false;

   public double money = 0D;

   public int isAttackByBossCounter = 0;

   public int bossResetDamageBoostCounter = 0;

   public int bossTargetCounter = 0;

   ItemStack itemRingKiller;

   public void setOp(boolean op) {
      this.isOp = op;
   }

   public boolean isOp() {
      return this.isOp;
   }

   public boolean isOpenFireworkShow = false;

   @Shadow
   public PlayerAbilities capabilities;

   private int protein;

   private int essential_fats;

   private int phytonutrients;

   protected int respawn_experience = 0;

   public long StoneCount = 0L;

   public int resetAttackMapTimer = 0;

   private int defenseCooldown;

   public long getStoneCount() {
      return this.StoneCount;
   }

   private Calendar calendar;

   public EntityPlayerTrans(World par1World, String par2Str) {
      super(par1World);
   }

   public void setProtein(int protein) {
      this.protein = MathHelper.clamp_int(protein, 0, 10240000);
   }


   private void activeEmergency(List<ItemStack> emergencyItemList) {

      this.addPotionEffect(new MobEffect(11, 60, 1));
      this.setHealth(Configs.wenscConfig.emergencyHealthRecoverAmount.ConfigValue, true, this.getHealFX());
      this.entityFX(EnumEntityFX.smoke_and_steam);
      this.makeSound("fireworks.largeBlast", 2.0F, 0.75F);
      this.makeSound("random.anvil_land", 0.4F, 0.4F);
      double reduce = (1 - (emergencyItemList.size() - 1) * Configs.wenscConfig.emergencyCooldownReduceEveryArmor.ConfigValue);
      for (ItemStack itemStack : emergencyItemList) {
         itemStack.setEmergencyCooldown((int) (Configs.wenscConfig.emergencyCooldown.ConfigValue * reduce));
      }
   }

   public boolean onEntityRightClicked(EntityPlayer player, ItemStack item_stack) {
      if (super.onEntityRightClicked(player, item_stack)) {
         return true;
      } else if (this.riddenByEntity == null && player.riddenByEntity == null && player.ridingEntity == null && this.ridingEntity == null && item_stack == null) {
         if (player.onServer()) {
            player.mountEntity(this);
         }

         return true;
      } else {
         return false;
      }
   }

   @Overwrite
   public static int getHealthLimit(int level) {
      return Math.max(Math.min(6 + level / 5 * 2, Configs.wenscConfig.maxLevelLimit.ConfigValue / 5), 6);
   }


   @Overwrite
   public static final int getHighestPossibleLevel() {
      return Configs.wenscConfig.maxLevelLimit.ConfigValue;
   }

//   @Overwrite
//   public static final int getHighestPossibleLevel() {
//      return 200;
//   }

   public boolean canDefense(){
      return this.defenseCooldown <= 0;
   }
   public void setDefenseCooldown(int time){
      this.defenseCooldown = time;
   }

   protected float getDisarmingChance(ItemStack itemStack){
      return EnchantmentManager.getEnchantmentLevelFraction(Enchantment.disarming, itemStack) * 0.4f;
   }
   protected void tryDisarmTarget(Entity target){
      if (this.onServer() && target instanceof EntityLiving) {
         EntityLiving entity_living_base = (EntityLiving)target;
         ItemStack item_stack_to_drop = entity_living_base.getHeldItemStack();
         if (item_stack_to_drop != null && this.rand.nextFloat() < this.getDisarmingChance(this.getHeldItemStack())) {
            if (entity_living_base instanceof EntityInsentient){
               EntityInsentient entity_living = (EntityInsentient)entity_living_base;
               if (entity_living.canBeDisarmed()) {
                  EntityItem entityItem = entity_living.dropItemStack(item_stack_to_drop, entity_living.height / 2.0F);
                  //Only for natural generated weapons
                  if (!entity_living.picked_up_a_held_item_array[0]) {
                     entityItem.setCanBePickUpByPlayer(false);
                     //Only exist for half a minute
                     entityItem.age = 5400;
                  }
                  entity_living.clearMatchingEquipmentSlot(item_stack_to_drop);
                  entity_living.ticks_disarmed = 40;
               }
            }else if (entity_living_base instanceof EntityPlayer){
               EntityPlayer player = (EntityPlayer) entity_living_base;
               if (!player.isBlocking() && Configs.wenscConfig.playerDisarmPlayer.ConfigValue){
                  EntityItem entityItem = player.dropItemStack(item_stack_to_drop, player.height / 2.0F);
                  Vec3D lookVec = player.getLookVec();
                  entityItem.delayBeforeCanPickup = 20;
                  //Rotate 90 degrees,to the right side of the player
                  entityItem.addVelocity(-lookVec.zCoord * 0.4,0,lookVec.xCoord  * 0.4);
                  player.setHeldItemStack(null);
               }
            }
         }
      }
   }

   @Overwrite
   public void attackTargetEntityWithCurrentItem(Entity target) {

      if (!this.isImmuneByGrace() && target.canAttackWithItem()) {
         boolean critical = this.willDeliverCriticalStrike();
         float critBouns = 0.0F;
         ItemStack heldItemStack = this.getHeldItemStack();

         //Check for crit enchantment
         if (EnchantmentManager.hasEnchantment(heldItemStack, Enchantments.CRIT) && !(target instanceof EntityZombieBoss)) {
            int critLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.CRIT, heldItemStack);
            critical = this.rand.nextInt(10) < Configs.wenscConfig.critEnchantmentChanceBoostPerLvl.ConfigValue * critLevel;
            if (critical) {
               critBouns = (float)critLevel * Configs.wenscConfig.critEnchantmentDamageBoostPerLvl.ConfigValue;
            }
         }

         //Check for indomitable modifier
         float indomitableAmp = 1;
         float healthPercent = this.getHealth() / this.getMaxHealth();
         if (healthPercent <= 0.5f){
            ItemStack chestplate = this.getCurrentArmor(1);
            if (chestplate != null){
               float value = ArmorModifierTypes.INDOMITABLE.getModifierValue(chestplate.getTagCompound());
               if (value != 0){
                  indomitableAmp = this.getIndomitableAmp(healthPercent);
               }
            }
         }

         float demonHunterAmp = 1;
         if (!target.getWorld().isOverworld() && heldItemStack != null){
            demonHunterAmp += ToolModifierTypes.DEMON_POWER.getModifierValue(heldItemStack.getTagCompound());
         }

         float damage = (critBouns + this.calcRawMeleeDamageVs(target, critical, this.isSuspendedInLiquid())) * indomitableAmp * demonHunterAmp;
         if (damage <= 0.0F) {
            return;
         }

         int knockback = 0;
         if (target instanceof EntityLiving) {
            knockback += EnchantmentManager.getKnockbackModifier(this, (EntityLiving)target);
         }

         if (this.isSprinting()) {
            ++knockback;
         }

         boolean was_set_on_fire = false;
         int fire_aspect = EnchantmentManager.getFireAspectModifier(this);
         if (target instanceof EntityLiving && fire_aspect > 0 && !target.isBurning()) {
            was_set_on_fire = true;
            target.setFire(1);
         }

         this.tryDisarmTarget(target);

         EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.causeMobDamage(this).setFireAspectP(fire_aspect > 0), damage));
         boolean target_was_harmed = result != null && result.entityWasNegativelyAffected();
         target.onMeleeAttacked(this, result);
         if (target_was_harmed) {
            if (target instanceof EntityLiving) {
               int stunning = EnchantmentManager.getStunModifier(this, (EntityLiving)target);
               if ((double)stunning > Math.random() * 10.0D) {
                  ((EntityLiving)target).addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, stunning * 50, stunning * 5));
               }

               if(!(target instanceof EntityZombieBoss)) {
                  this.heal((float)EnchantmentManager.getVampiricTransfer(this, (EntityLiving)target, damage), EnumEntityFX.vampiric_gain);
               }
            }

            if (knockback > 0) {
               target.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * (float)knockback * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * (float)knockback * 0.5F);
               this.motionX *= 0.6D;
               this.motionZ *= 0.6D;
               this.setSprinting(false);
            }

            if (critical) {
               this.onCriticalHit(target);
            }

            if (target instanceof EntityLiving && EnchantmentWeaponDamage.getDamageModifiers(heldItemStack, (EntityLiving)target) > 0.0F) {
               this.onEnchantmentCritical(target);
            }

            if (damage >= 18.0F) {
               this.triggerAchievement(AchievementList.overkill);
            }

            this.setLastAttackTarget(target);
            if (target instanceof EntityLiving) {
               if (this.worldObj.isRemote) {
                  System.out.println("EntityPlayer.attackTargetEntityWithCurrentItem() is calling EnchantmentThorns.func_92096_a() on client");
                  Minecraft.temp_debug = "player";
               }

               EnchantmentThorns.func_92096_a(this, (EntityLiving)target, this.rand);
            }
         }

         ItemStack held_item_stack = heldItemStack;
         Object var10 = target;
         if (target instanceof EntityComplexPart) {
            IComplex var11 = ((EntityComplexPart)target).entityDragonObj;
            if (var11 != null && var11 instanceof EntityLiving) {
               var10 = var11;
            }
         }

         if (target_was_harmed && held_item_stack != null && var10 instanceof EntityLiving) {
            held_item_stack.hitEntity((EntityLiving)var10, ReflectHelper.dyCast(this));
         }

         if (target instanceof EntityLiving) {
            this.addStat(StatisticList.damageDealtStat, Math.round(damage * 10.0F));
            if (fire_aspect > 0 && target_was_harmed) {
               target.setFire(fire_aspect * 4);
            } else if (was_set_on_fire) {
               target.extinguish();
            }
         }

         if (this.onServer()) {
            this.addHungerServerSide(0.3F * EnchantmentManager.getEnduranceModifier(this));
         }
      }

   }

   private float getIndomitableAmp(float healthPercent){
      if (healthPercent <= 0.1f){
         return 2.0f;
      }else if (healthPercent <= 0.2f) {
         return 1.6f;
      }else if (healthPercent <= 0.35f){
         return 1.35f;
      }else if (healthPercent <= 0.5f){
         return 1.25f;
      }
      return 1.0f;
   }

   @Override
   public void addPotionEffect(MobEffect par1PotionEffect) {
         ItemStack helmet = this.getHelmet();
         if (helmet != null &&
                 //Bad only
                 MobEffectList.get(par1PotionEffect.getPotionID()).f()
         ){
            par1PotionEffect.setDuration((int) (par1PotionEffect.getDuration() * (1 - ArmorModifierTypes.IMMUNITY.getModifierValue(helmet.getTagCompound()))));
         }
         super.addPotionEffect(par1PotionEffect);
   }

//   @Overwrite
//   public static int getHealthLimit(int level) {
//      return Math.max(Math.min(6 + level / 5 * 2, 40), 6);
//   }


   @Shadow
   public void addHungerServerSide(float hunger) {
   }

   @Shadow public abstract void addStat(Statistic par1StatBase, int par2);

   protected void checkForAfterDamage(Damage damage, EntityDamageResult result) {
      if (result.entityWasDestroyed()) {
         ItemStack[] var3 = this.getWornItems();
         List<ItemStack> readyEmergencyItemList = new ArrayList<>();
         for (ItemStack wornItem : var3) {
            if (wornItem != null && wornItem.hasEnchantment(Enchantments.EMERGENCY, false)) {
               if (wornItem.getEmergencyCooldown() <= 0){
                  readyEmergencyItemList.add(wornItem);
               } else {
                  this.addChatMessage("紧急守备冷却中：" + wornItem.getEmergencyCooldown() / 20 + "S");
               }
            }
         }
         if (readyEmergencyItemList.size() > 0){
            result.setEntity_was_destroyed(false);
            this.activeEmergency(readyEmergencyItemList);
         } else if(this.spawnStoneWorldId != -999) {
            if(damage.getSource().getImmediateEntity() != null) {
               this.addChatMessage("复活石效果生效：" + " 攻击来源1:" + damage.getSource().getImmediateEntity().getEntityName() + " 造成实际伤害：" + result.getAmountOfHealthLost() + " 玩家之前生命：" + this.prevHealth);
            } else {
               this.addChatMessage("复活石效果生效：" + " 攻击来源1:" + damage.getSource().damageType + " 造成实际伤害："+ result.getAmountOfHealthLost() + " 玩家之前生命：" + this.prevHealth);
            }
            result.setEntity_was_destroyed(false);
            this.setHealth(20, true, this.getHealFX());
            this.addPotionEffect(new MobEffect(MobEffectList.regeneration.id, 1200, 1));
            this.addPotionEffect(new MobEffect(MobEffectList.resistance.id, 600, 1));
            this.addPotionEffect(new MobEffect(MobEffectList.fireResistance.id, 1200, 0));
            if (!(this.isPotionActive(MobEffectList.field_76444_x) && this.getActivePotionEffect(MobEffectList.field_76444_x).getAmplifier() > 0)) {
               this.addPotionEffect(new MobEffect(MobEffectList.heal.id, 2, 0));
               this.removePotionEffect(MobEffectList.field_76444_x.id);
               this.addPotionEffect(new MobEffect(MobEffectList.field_76444_x.id, 20 * 20, 2));
            }
            if (this.getWorld().getDimensionId() != this.spawnStoneWorldId) {
               this.travelToDimension(this.spawnStoneWorldId);
            }
            this.setPositionAndUpdate(this.spawnStoneX, this.spawnStoneY, this.spawnStoneZ);

            this.getWorld().setBlockToAir(this.spawnStoneX, this.spawnStoneY - 1, this.spawnStoneZ);
            this.experience = this.experience / 2;
            this.spawnStoneWorldId = -999;
         }
      }
   }

   @Shadow
   protected abstract float calcRawMeleeDamageVs(Entity var1, boolean var2, boolean var3);

   @Inject(method = "clonePlayer",at = @At("RETURN"))
   public void clonePlayerInject(EntityPlayer par1EntityPlayer, boolean par2, CallbackInfo callbackInfo) {
      this.spawnStoneWorldId = par1EntityPlayer.spawnStoneWorldId;
      this.spawnStoneX = par1EntityPlayer.spawnStoneX;
      this.spawnStoneY = par1EntityPlayer.spawnStoneY;
      this.spawnStoneZ = par1EntityPlayer.spawnStoneZ;
      this.isFirstLogin = par1EntityPlayer.isFirstLogin;
      this.money = par1EntityPlayer.money;
   }

   public double plusMoney(double singleMoney){
      return this.money += singleMoney;
   }

   public double subMoney(double singleMoney){
      return this.money -= singleMoney;
   }

   @Inject(method = "readEntityFromNBT",at = @At("RETURN"))
   public void injectReadNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo ci) {
      NBTTagCompound nb = par1NBTTagCompound.getCompoundTag("spawn");

      this.StoneCount = par1NBTTagCompound.getLong("StoneCount");
      this.isFirstLogin = par1NBTTagCompound.getBoolean("isFirstLogin");
      NBTTagList var2 = par1NBTTagCompound.getTagList("Inventory");
      this.inventory.readFromNBT(var2);
      this.inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
      this.underworldRandomTeleportTime = par1NBTTagCompound.getInteger("UnderWorldTeleportTime");
      this.underworldDebuffTime = par1NBTTagCompound.getInteger("UnderWorldDebuffTime");
      this.netherDebuffTime = par1NBTTagCompound.getInteger("NetherDebuffTime");
      this.inRainCounter = par1NBTTagCompound.getInteger("InRainCounter");
      this.vision_dimming = par1NBTTagCompound.getFloat("vision_dimming");
      this.money = par1NBTTagCompound.getDouble("money");
      this.resetAttackMapTimer = par1NBTTagCompound.getInteger("resetAttackMapTimer");
      this.isAttackByBossCounter = par1NBTTagCompound.getInteger("isAttackByBossCounter");


      this.spawnStoneX = par1NBTTagCompound.getInteger("spawnStoneX");
      this.spawnStoneY = par1NBTTagCompound.getInteger("spawnStoneY");
      this.spawnStoneZ = par1NBTTagCompound.getInteger("spawnStoneZ");
      this.spawnStoneWorldId = par1NBTTagCompound.getInteger("spawnStoneWorldId");
      if (par1NBTTagCompound.hasKey("bossResetDamageBoostCounter")) {
         this.bossResetDamageBoostCounter = par1NBTTagCompound.getInteger("bossResetDamageBoostCounter");
      }

      if (par1NBTTagCompound.hasKey("AttackCountMap")) {
         NBTTagList attackCountMap = par1NBTTagCompound.getTagList("AttackCountMap");
         int count = attackCountMap.tagCount();

         for(int i = 0; i < count; ++i) {
            NBTTagCompound a = (NBTTagCompound)attackCountMap.tagAt(i);
            Entity attacker = this.getWorldServer().getEntityByID(a.getInteger("Attacker"));
            if (attacker != null) {
               this.attackCountMap.put(attacker, a.getInteger("Count"));
            }
         }
      }
      if (par1NBTTagCompound.hasKey("DefenseCooldown")){
         this.setDefenseCooldown(par1NBTTagCompound.getInteger("DefenseCooldown"));
      }

   }

   @Overwrite
   private void checkForArmorAchievements() {
      boolean wearing_leather = false;
      boolean wearing_full_suit_plate = true;
      boolean wearing_full_suit_adamantium_plate = true;
      boolean wearing_full_suit_vibranium_plate = true;

      for(int i = 0; i < 4; ++i) {
         if (this.inventory.armorInventory[i] != null && this.inventory.armorInventory[i].getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)this.inventory.armorInventory[i].getItem();
            Material material = armor.getArmorMaterial();
            if (material == Material.leather) {
               wearing_leather = true;
            }

            if (material != Material.copper && material != Material.silver && material != Material.gold && material != Material.iron && material != Material.mithril && material != Material.adamantium && material != Material.ancient_metal) {
               wearing_full_suit_plate = false;
            }

            if (material != Material.adamantium) {
               wearing_full_suit_adamantium_plate = false;
            }

            if (material != Materials.vibranium) {
               wearing_full_suit_vibranium_plate = false;
            }
         } else {
            wearing_full_suit_plate = false;
            wearing_full_suit_adamantium_plate = false;
            wearing_full_suit_vibranium_plate = false;
         }
      }

      if (wearing_leather) {
         this.triggerAchievement(AchievementList.wearLeather);
      }

      if (wearing_full_suit_plate) {
         this.triggerAchievement(AchievementList.wearAllPlateArmor);
      }

      if (wearing_full_suit_adamantium_plate) {
         this.triggerAchievement(AchievementList.wearAllAdamantiumPlateArmor);
      }

      if (wearing_full_suit_vibranium_plate) {
         this.triggerAchievement(Achievements.wearAllVibraniumPlateArmor);
      }

   }

   @Shadow
   public abstract void displayGUIAnvil(int x, int y, int z);

   public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
   }

   public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots) {
   }

   @Shadow
   protected abstract void fall(float par1);
   
   @Shadow public abstract float getAIMoveSpeed();

//   @Overwrite
//   public EntityDamageResult attackEntityFrom(Damage damage) {
//      if (this.ac < 1000 && Damage.wasCausedByPlayer(damage) && this.isWithinTournamentSafeZone()) {
//         return null;
//      } else if (this.bG.disableDamage && !damage.canHarmInCreative()) {
//         return null;
//      } else {
//         if (this.inBed()) {
//            this.wakeUpPlayer(true, damage.getResponsibleEntityP());
//         }
//
//         if (damage.isExplosion()) {
//            damage.scaleAmount(1.5F);
//         }
//
//
//         //End of injection
//
//         return entityDamageResult;
//      }
//   }

   public float getCraftingBoostFactor() {
      return this.craftingBoostFactor;
   }

   @Shadow
   public ItemStack getHeldItemStack() {
      return null;
   }

   @Shadow
   public void setHeldItemStack(ItemStack itemStack) {
   }

   @Shadow
   private float getLevelModifier(EnumLevelBonus harvesting) {
      return 0.0F;
   }

   @Overwrite
   public final float getReach(Block block, int metadata) {
      if (this.hasExtendedReach()) {
         return 5.0F;
      } else {
         float block_reach = 2.75F;
         ItemStack item_stack = this.getHeldItemStack();
         int enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
         return item_stack == null ? block_reach : block_reach + item_stack.getItem().getReachBonus(block, metadata) + (float)enchantmentLevel * 0.25F;
      }
   }

   @Overwrite
   public float getReach(EnumEntityReachContext context, Entity entity) {
      if (this.hasExtendedReach()) {
         return 5.0F;
      } else {
         float elevation_difference = (float)(this.posY - (double)this.yOffset - (entity.posY - (double)entity.yOffset));
         float height_advantage;
         if (elevation_difference < -0.5F) {
            height_advantage = (elevation_difference + 0.5F) * 0.5F;
            if (height_advantage < -1.0F) {
               height_advantage = -1.0F;
            }
         } else if (elevation_difference > 0.5F) {
            height_advantage = (elevation_difference - 0.5F) * 0.5F;
            if (height_advantage > 1.0F) {
               height_advantage = 1.0F;
            }
         } else {
            height_advantage = 0.0F;
         }

         ItemStack item_stack = this.getHeldItemStack();
         int enchantmentLevel;
         if (context == EnumEntityReachContext.FOR_MELEE_ATTACK) {
            enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
            return entity.adjustPlayerReachForAttacking(ReflectHelper.dyCast(this), 1.5F + height_advantage + (item_stack == null ? 0.0F : item_stack.getItem().getReachBonus())) + (float)enchantmentLevel * 0.25F;
         } else if (context == EnumEntityReachContext.FOR_INTERACTION) {
            enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
            return entity.adjustPlayerReachForInteraction(ReflectHelper.dyCast(this), 2.5F + height_advantage + (item_stack == null ? 0.0F : item_stack.getItem().getReachBonus(entity)) + (float)enchantmentLevel * 0.25F);
         } else {
            Minecraft.setErrorMessage("getReach: invalid context");
            return 0.0F;
         }
      }
   }

   public int[] getRunegateDestinationCoords(WorldServer world, int x, int y, int z) {
      int seed = this.rand.nextInt();
      BlockRunestone block_runestone = Block.runestoneMithril;
      int chunk_z;
      int runegate_domain_radius;
      if (seed == 0) {
         x = 0;
         z = 0;
      } else {
         Random random = new Random(seed);

         for(chunk_z = 0; chunk_z < 4; ++chunk_z) {
            runegate_domain_radius = world.getRunegateDomainRadius(block_runestone == Block.runestoneAdamantium ? Material.adamantium : Material.mithril);
            x = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius;

            for(z = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius; block_runestone == Block.runestoneAdamantium && WorldServer.getDistanceFromDeltas(x, z) < (double)(runegate_domain_radius / 2); z = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius) {
               x = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius;
            }

            if (world.getBiomeGenForCoords(x, z) != BiomeBase.ocean) {
               break;
            }
         }
      }

      int chunk_x = x >> 4;
      chunk_z = z >> 4;

      for(runegate_domain_radius = -1; runegate_domain_radius <= 1; ++runegate_domain_radius) {
         for(int dz = -1; dz <= 1; ++dz) {
            world.getChunkProvider().provideChunk(chunk_x + runegate_domain_radius, chunk_z + dz);
         }
      }

      if (world.isTheNether()) {
         y = 0;

         while(true) {
            ++y;
            if (y >= 254) {
               break;
            }

            if (world.isAirOrPassableBlock(x, y, z, false)) {
               ++y;
               if (world.isAirOrPassableBlock(x, y, z, false) && !world.isAirOrPassableBlock(x, y - 2, z, false) && !world.isLavaBlock(x, y - 2, z) && !world.isLavaBlock(x, y - 1, z)) {
                  return new int[]{x, y - 1, z};
               }
            }
         }
      } else if (world.isUnderworld()) {
         y = 254;

         while(true) {
            --y;
            if (y <= 0) {
               break;
            }

            if (BlockPortal.isGoodSpotForPlayerToAppearAt(world, x, y, z)) {
               return new int[]{x, y, z};
            }
         }
      } else {
         y = 256;

         while(true) {
            --y;
            if (y <= 0) {
               break;
            }

            if (world.isAirOrPassableBlock(x, y, z, false)) {
               --y;
               if (world.isAirOrPassableBlock(x, y, z, false)) {
                  while(y > 0 && world.isAirOrPassableBlock(x, y - 1, z, false)) {
                     --y;
                  }

                  if (y == 0) {
                     y = 64;
                  }

                  return new int[]{x, y, z};
               }
            }
         }
      }

      if (!world.isUnderworld()){
         if (!world.isAirOrPassableBlock(x, 64, z, true)) {
            world.setBlockToAir(x, 64, z);
         }

         if (!world.isAirOrPassableBlock(x, 65, z, true)) {
            world.setBlockToAir(x, 65, z);
         }
         return new int[]{x, 64, z};
      }else {
         if (!world.isAirOrPassableBlock(x, 120, z, true)) {
            world.setBlockToAir(x, 120, z);
         }

         if (!world.isAirOrPassableBlock(x, 121, z, true)) {
            world.setBlockToAir(x, 121, z);
         }
         return new int[]{x, 120, z};
      }
   }

   @Shadow
   public ItemStack[] getWornItems() {
      return new ItemStack[0];
   }

   @Shadow
   protected abstract boolean hasExtendedReach();

   @Shadow
   private boolean hasFoodEnergy() {
      return false;
   }

   private void initiateRunegateTeleport(WorldServer world, int x, int y, int z, ServerPlayer player) {
      int[] runegate_destination_coords = this.getRunegateDestinationCoords(world, x, y, z);
      player.runegate_destination_coords = runegate_destination_coords;
      player.playerNetServerHandler.sendPacket(new Packet85SimpleSignal(EnumSignal.runegate_start));
      player.setPositionAndUpdate(runegate_destination_coords[0], runegate_destination_coords[1], runegate_destination_coords[2]);
   }

   @Redirect(method = "getCurrentPlayerStrVsBlock",at = @At(value = "INVOKE",target = "Lnet/minecraft/EnchantmentManager;getAquaAffinityModifier(Lnet/minecraft/EntityLiving;)Z"))
   private boolean redirectCheckAquaEnchantment(EntityLiving player){
      boolean aquaAffinityModifier = EnchantmentManager.getAquaAffinityModifier(this);
      boolean waterLike = this.getHeldItemStack() != null && ToolModifierTypes.AQUADYNAMIC_MODIFIER.getModifierLevel(this.getHeldItemStack().getTagCompound()) != 0;
      return aquaAffinityModifier || waterLike;
   }

   @Redirect(method = "attackEntityFrom",
           at = @At(value = "INVOKE",
                   target = "Lnet/minecraft/EntityLiving;attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;"))
   private EntityDamageResult redirectEntityAttack(EntityLiving caller,Damage damage){
      if (!this.worldObj.isRemote)
      {
         if(damage.getSource() == DamageSource.inWall) {
            return null;
         }
      }

      // 如果收到伤害，计时5S，打一次计时5S，持续打持续计时
      this.resetAttackMapTimer = 200;
      // 每天在渐进增强 progress = 1/50 2/50 50/50
      double progress = Math.min(Configs.wenscConfig.steppedMobDamageProgressMax.ConfigValue, (this.getWorld().getDayOfOverworld()) / (float)Configs.wenscConfig.steppedMobDamageProgressIncreaseDay.ConfigValue);
      if (progress != 0.0D) {
         Entity responsibleEntity = damage.getSource().getResponsibleEntity();
         if (responsibleEntity != null && !(responsibleEntity instanceof EntityEnderDragon || responsibleEntity instanceof EntityCubic)) {
            if (this.attackCountMap.containsKey(responsibleEntity)) {
               damage.setAmount(damage.getAmount() + (this.attackCountMap.get(responsibleEntity)) * (float)progress);
               this.attackCountMap.put(responsibleEntity, this.attackCountMap.get(responsibleEntity) + 1);
            } else {
               this.attackCountMap.put(responsibleEntity, 1);
            }

            // 去掉附魔金苹果的保护值
            float protection = this.getProtection(damage.getSource(), true, true, true, false);
            // 如果伤害低于当前总防御力，如果高于总防御按照正常计算
            if(damage.getAmount() < protection) {
               int trueHurtPercent = (int)(protection / 10); // 护甲值倍率数0,1,2,3,4,5 真伤概率60%,50%,40%,30%,20%,10%
               if(rand.nextInt(10) < Math.max(6 - trueHurtPercent, 0)) {
                  damage.setAmount(this.getTotalProtection(damage.getSource()) + damage.getAmount() / 10);
               }
            }
         }
      }


      if (damage.getResponsibleEntityP() != null && this.getHeldItem() != null && this.rand.nextInt(10) > 8) {
         this.tryDisarmTarget(damage.getResponsibleEntityP());
      }

      EntityDamageResult entityDamageResult = super.attackEntityFrom(damage);

      if (entityDamageResult != null && (double)this.getHealthFraction() <= 0.1D && !entityDamageResult.entityWasDestroyed()) {
         ItemStack[] var5 = this.getWornItems();

         List<ItemStack> readyEmergencyItems = new ArrayList<>();
         for (ItemStack wornItem : var5) {
            if (wornItem != null && wornItem.hasEnchantment(Enchantments.EMERGENCY, false)) {
               if (wornItem.getEmergencyCooldown() <= 0){
                  readyEmergencyItems.add(wornItem);
               } else {
                  this.addChatMessage("紧急守备冷却中：" + wornItem.getEmergencyCooldown());
               }
            }
         }
         if (readyEmergencyItems.size() > 0){
            this.activeEmergency(readyEmergencyItems);
         } else if(this.spawnStoneWorldId != -999) {
              if(damage.getSource().getImmediateEntity() != null) {
                 this.addChatMessage("复活石效果生效：" + " 攻击来源2:" + damage.getSource().getImmediateEntity().getEntityName() + " 造成实际伤害："+ entityDamageResult.getAmountOfHealthLost() + " 玩家之前生命：" + this.prevHealth);
              } else {
                 this.addChatMessage("复活石效果生效：" + " 攻击来源2:" + damage.getSource().damageType + " 造成实际伤害："+ entityDamageResult.getAmountOfHealthLost() + " 玩家之前生命：" + this.prevHealth);
              }
            entityDamageResult.setEntity_was_destroyed(false);
            this.setHealth(20, true, this.getHealFX());
            this.addPotionEffect(new MobEffect(MobEffectList.regeneration.id, 1200, 1));
            this.addPotionEffect(new MobEffect(MobEffectList.resistance.id, 600, 1));
            this.addPotionEffect(new MobEffect(MobEffectList.fireResistance.id, 1200, 0));
            if (!(this.isPotionActive(MobEffectList.field_76444_x) && this.getActivePotionEffect(MobEffectList.field_76444_x).getAmplifier() > 0)) {
               this.addPotionEffect(new MobEffect(MobEffectList.heal.id, 2, 0));
               this.removePotionEffect(MobEffectList.field_76444_x.id);
               this.addPotionEffect(new MobEffect(MobEffectList.field_76444_x.id, 20 * 20, 2));
            }
            if (this.getWorld().getDimensionId() != this.spawnStoneWorldId) {
               this.travelToDimension(this.spawnStoneWorldId);
            }
            this.setPositionAndUpdate(this.spawnStoneX, this.spawnStoneY, this.spawnStoneZ);
            this.getWorld().setBlockToAir(this.spawnStoneX, this.spawnStoneY - 1, this.spawnStoneZ);
            this.experience = this.experience / 2;
            this.spawnStoneWorldId = -999;
         }
      }
      return entityDamageResult;
   }

   public void setSpawnStoneWorldId(int worldId) {
      this.spawnStoneWorldId = worldId;
   }

   private ItemStack damageItem(ItemStack itemStack, int damage) {
      if (itemStack != null) {
         int rawDamage = itemStack.getItemDamage();
         int maxDamage = itemStack.getMaxDamage();
         int resultDamage = rawDamage + damage;
         return resultDamage > maxDamage ? null : itemStack.setItemDamage(resultDamage);
      } else {
         return null;
      }
   }
   public void attackMonsters(List <Entity>targets) {
      float damage = ((ItemRingKiller)itemRingKiller.getItem()).getRingKillerSkillDamage();
      for(int i = 0; i< targets.size(); i++) {
         EntityMonster entityMonster = targets.get(i) instanceof EntityMonster ? (EntityMonster)targets.get(i) : null;
         if(entityMonster != null && (!EntityEnderman.class.isInstance(entityMonster) && !EntitySilverfish.class.isInstance(entityMonster) && !EntityZombieBoss.class.isInstance(entityMonster))) {
            entityMonster.attackEntityFrom(new Damage(DamageSource.causePlayerDamage(this.getAsPlayer()), damage));
         }
      }
   }

   @Inject(method = "onLivingUpdate",
           at = @At(value = "INVOKE",
                   target = "Lnet/minecraft/EntityLiving;onLivingUpdate()V",
                   shift = At.Shift.AFTER))
   private void injectTick(CallbackInfo c){
      this.inventory.decrementAnimations();
      // 客户端
      if(this.storeTorchTick <= 0) {
         ItemStack currentItemStack = this.inventory.getDynamicCore();
         if(currentItemStack != null) {
            if(currentItemStack.getItemDamage() < currentItemStack.getMaxDamage() - 2) {
               this.hasDynamicCore = true;
               if (!this.worldObj.isRemote){
                  currentItemStack.tryDamageItem(DamageSource.causePlayerDamage(ReflectHelper.dyCast(this)), 2, ReflectHelper.dyCast(this));
               }
            } else {
               this.hasDynamicCore = false;
            }
         } else {
            this.hasDynamicCore = false;
            this.storeTorchTick = 0;
         }
         this.storeTorchTick = 10;
      } else {
         this.storeTorchTick --;
      }

      // 服务端
      if (!this.worldObj.isRemote) {

         if(this.isAttackByBossCounter > 0) {
            --this.isAttackByBossCounter;
         }

         if(bossResetDamageBoostCounter > 0) {
            --bossResetDamageBoostCounter;
            this.removePotionEffect(MobEffectList.damageBoost.id);
         }
         // 一旦收到攻击，开始计时5S，如果5S正常结束表示未持续收到伤害，则清空
         if(resetAttackMapTimer <= 0) {
            this.attackCountMap.clear();
         } else {
            resetAttackMapTimer --;
         }
         this.itemRingKiller = this.inventory.getRingKiller();
         if(this.itemRingKiller != null) {
            float range = ((ItemRingKiller)this.itemRingKiller.getItem()).getRingKillerSkillRange();
            int cooldownTime = ((ItemRingKiller)this.itemRingKiller.getItem()).getRingKillerSkillCoolDownTime();
            List <Entity>targets  = this.getNearbyEntities(range, range);
            if(targets.size() > 0) {
               if(this.surroundHurtCollDown == cooldownTime) {
                  this.attackMonsters(targets);
                  --this.surroundHurtCollDown;
               } else {
                  --this.surroundHurtCollDown;
                  if(this.surroundHurtCollDown <= 0) {
                     this.surroundHurtCollDown = cooldownTime;
                  }
               }
            } else {
               if(this.surroundHurtCollDown < cooldownTime) {
                  --this.surroundHurtCollDown;
                  if(this.surroundHurtCollDown <= 0) {
                     this.surroundHurtCollDown = cooldownTime;
                  }
               }
            }
         }

         if (this.craftingBoostTimer > 0) {
            --this.craftingBoostTimer;
         } else if (this.craftingBoostTimer == 0) {
            this.craftingBoostFactor = 0.0F;
            this.sendPacket(new SPacketCraftingBoost((float)this.craftingBoostTimer));
            this.craftingBoostTimer = -1;
         }
         if (this.isInRain()) {
            if (this.inRainCounter < Configs.wenscConfig.inRainDebuffTime.ConfigValue) {
               ++this.inRainCounter;
            } else {
               this.addPotionEffect(new MobEffect(18, 3600, 0));
            }
         } else if (this.inRainCounter > 0) {
            --this.inRainCounter;
         }

         int debuff_time;

         if (!this.worldObj.isUnderworld()) {
            if (this.worldObj.isTheNether()) {
               if (Configs.wenscConfig.netherDebuff.ConfigValue) {
                  debuff_time = Configs.wenscConfig.netherDebuffTime.ConfigValue;
                  if (this.netherDebuffTime > debuff_time) {
                     this.addPotionEffect(new MobEffect(4, 3600, 1));
                  } else if (this.netherDebuffTime == debuff_time) {
                     this.sendPacket(new SPacketOverlayMessage("§n---你在下界中感到疲惫---", EnumChatFormat.DARK_RED.rgb, 200));
                  }
               }
            } else if (this.netherDebuffTime > 0) {
               --this.netherDebuffTime;
            } else if (this.netherDebuffTime == 1) {
               this.sendPacket(new SPacketOverlayMessage("§n---你已从地狱的疲惫中恢复---", EnumChatFormat.DARK_GREEN.rgb, 200));
            }

            if (this.underworldRandomTeleportTime > 0) {
               --this.underworldRandomTeleportTime;
            }

            if (this.underworldDebuffTime > 0) {
               --this.underworldDebuffTime;
            } else if (this.underworldDebuffTime == 1) {
               this.sendPacket(new SPacketOverlayMessage("§n---你已从地底世界的疲惫中恢复---", EnumChatFormat.DARK_GREEN.rgb, 200));
            }
         }
         else {
            if (Configs.wenscConfig.underworldDebuff.ConfigValue) {
               ++this.underworldDebuffTime;
               debuff_time = Configs.wenscConfig.underworldDebuffPeriod2.ConfigValue;
               int period2 = Configs.wenscConfig.underworldDebuffPeriod2.ConfigValue;
               if (this.underworldDebuffTime > debuff_time && this.underworldDebuffTime < period2) {
                  if (this.underworldDebuffTime == debuff_time + 1) {
                     this.sendPacket(new SPacketOverlayMessage("§l---你在地底世界中感到有些疲惫---", EnumChatFormat.GRAY.rgb, 400));
                  }

                  this.addPotionEffect(new MobEffect(2, 1200, 0));
               } else if (this.underworldDebuffTime > period2) {
                  if (this.underworldDebuffTime == period2 + 1) {
                     this.sendPacket(new SPacketOverlayMessage("§l---你在地底世界中感到更加疲惫---", EnumChatFormat.YELLOW.rgb, 400));
                  }

                  this.addPotionEffect(new MobEffect(2, 2400, 1));
               }
            }

            if (Configs.wenscConfig.underworldRandomTeleport.ConfigValue) {
               double randomTeleportTime = Configs.wenscConfig.underworldRandomTeleportTimeNew.ConfigValue;
               ++this.underworldRandomTeleportTime;
               double timeToTeleport = randomTeleportTime - (double)this.underworldRandomTeleportTime;
               if (timeToTeleport == 1200.0D) {
                  this.sendPacket(new SPacketOverlayMessage("§l---你将于一分钟后被随机传送,请做好准备!!!---", EnumChatFormat.YELLOW.rgb, 400));
               }

               if (timeToTeleport == 6000.0D) {
                  this.sendPacket(new SPacketOverlayMessage("§l---你将于五分钟后被随机传送,请做好准备!!!---", EnumChatFormat.DARK_AQUA.rgb, 400));
               }

               if (timeToTeleport <= 200.0D && this.underworldRandomTeleportTime % 20 == 0) {
                  this.sendPacket(new SPacketOverlayMessage("§l§n!!!你将于" + (int)timeToTeleport / 20 + "秒后被随机传送!!!", EnumChatFormat.RED.rgb, 200));
               }

               if (this.underworldRandomTeleportTime > randomTeleportTime) {
                  if (ReflectHelper.dyCast(EntityPlayer.class, this) instanceof EntityPlayer) {
                     this.initiateRunegateTeleport(this.worldObj.getAsWorldServer(), this.getBlockPosX(), this.getBlockPosY(), this.getBlockPosZ(), ReflectHelper.dyCast(this));
                  }

                  this.underworldRandomTeleportTime = 0;
               }
            } else if (this.underworldRandomTeleportTime > 0) {
               --this.underworldRandomTeleportTime;
            }
         }




         //To avoid slot locking due to emergency cooldown
         if (ticksExisted % 20 == 0){
            // 紧急守备冷却
            for (ItemStack wornItem : this.getWornItems()) {
               if (wornItem != null){
                  int emergencyCooldown = wornItem.getEmergencyCooldown();
                  if (emergencyCooldown > 0){
                     wornItem.setEmergencyCooldown(Math.max(emergencyCooldown - 20, 0));
                  }
               }
            }

            // 烟花效果
            calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
//            this.addChatMessage("" + calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND));
            if(this.isOpenFireworkShow == true || ((calendar.get(Calendar.MONTH) + 1) == 1 && calendar.get(Calendar.DATE) == 1 && calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) >= 0 && calendar.get(Calendar.MINUTE) <= 5)) {
               World world = this.getWorld();
               ItemStack itemStack = new ItemStack(Item.fireworkCharge);
               ItemStack itemStack2 = new ItemStack(Item.firework);
               NBTTagList var25 = new NBTTagList("Explosions");
               NBTTagCompound var15;
               NBTTagCompound var18;
               var15 = new NBTTagCompound();
               var18 = new NBTTagCompound("Explosion");


               var18.setBoolean("Flicker", true);
               var18.setBoolean("Trail", true);
               byte var23 = (byte)(rand.nextInt(4) + 1);

               var18.setIntArray("Colors", ItemDye.dyeColors);
               var18.setIntArray("FadeColors", ItemDye.dyeColors);

               var18.setByte("Type", (byte)(rand.nextInt(4) + 1));
               var15.setTag("Explosion", var18);
               itemStack.setTagCompound(var15);

               var15 = new NBTTagCompound();
               var18 = new NBTTagCompound("Fireworks");
               var25.appendTag(itemStack.getTagCompound().getCompoundTag("Explosion"));
               var18.setTag("Explosions", var25);
               var18.setByte("Flight", (byte)(rand.nextInt(3) + 1));
               var15.setTag("Fireworks", var18);
               itemStack2.setTagCompound(var15);
               world.spawnEntityInWorld(new EntityFireworks(world, this.posX, this.posY + 5, this.posZ, itemStack2));
            }
         }
         if (this.defenseCooldown > 0){
            this.defenseCooldown--;
         }
      }
   }

   @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
   public void injectWriteNBT(NBTTagCompound par1NBTTagCompound,CallbackInfo callback) {
      par1NBTTagCompound.setLong("StoneCount", this.StoneCount);
      par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
      par1NBTTagCompound.setBoolean("isFirstLogin", this.isFirstLogin);

      par1NBTTagCompound.setInteger("UnderWorldTeleportTime", this.underworldRandomTeleportTime);
      par1NBTTagCompound.setInteger("UnderWorldDebuffTime", this.underworldDebuffTime);
      par1NBTTagCompound.setInteger("NetherDebuffTime", this.netherDebuffTime);
      par1NBTTagCompound.setInteger("InRainCounter", this.inRainCounter);
      par1NBTTagCompound.setFloat("vision_dimming", this.vision_dimming);
      par1NBTTagCompound.setDouble("money", this.money);

      par1NBTTagCompound.setInteger("spawnStoneX", this.spawnStoneX);
      par1NBTTagCompound.setInteger("spawnStoneY", this.spawnStoneY);
      par1NBTTagCompound.setInteger("spawnStoneZ", this.spawnStoneZ);
      par1NBTTagCompound.setInteger("spawnStoneWorldId", this.spawnStoneWorldId);
      par1NBTTagCompound.setInteger("bossResetDamageBoostCounter", this.bossResetDamageBoostCounter);
      par1NBTTagCompound.setInteger("resetAttackMapTimer", this.resetAttackMapTimer);
      par1NBTTagCompound.setInteger("isAttackByBossCounter", this.isAttackByBossCounter);

      NBTTagList nbtTagList = new NBTTagList();
      for (Entry<Entity, Integer> integerEntry : this.attackCountMap.entrySet()) {
         NBTTagCompound compound = new NBTTagCompound();
         compound.setInteger("Attacker", ( integerEntry).getKey().entityId);
         compound.setInteger("Count", (integerEntry).getValue());
         nbtTagList.appendTag(compound);
      }

      par1NBTTagCompound.setTag("AttackCountMap", nbtTagList);
      par1NBTTagCompound.setInteger("DefenseCooldown",this.defenseCooldown);
   }

   @Redirect(method = "getCurrentPlayerStrVsBlock",at = @At(value = "INVOKE",target = "Lnet/minecraft/Item;getStrVsBlock(Lnet/minecraft/Block;I)F"))
   private float redirectGetStrVsBlock(Item caller,Block block,int metadata){
      ItemStack heldItemStack = this.getHeldItemStack();
      return heldItemStack.getItem().getStrVsBlock(block, metadata,heldItemStack, net.xiaoyu233.fml.util.ReflectHelper.dyCast(this));
   }

   @Shadow
   protected abstract boolean isImmuneByGrace();

   @Shadow
   public boolean isWearing(ItemStack itemStack) {
      return false;
   }

   public void itemsSynced() {
      this.waitForItemSync = false;
   }

   @Override
   public boolean knockBack(Entity attacker, float amount) {
      ItemStack[] itemStacks = this.getWornItems();
      int i = 0;

      for(int itemStacksLength = itemStacks.length; i < itemStacksLength; ++i) {
         ItemStack stack = itemStacks[i];
         if (stack != null) {
            amount *= Math.max(1.0F - ArmorModifierTypes.STEADY_MODIFIER.getModifierValue(stack.stackTagCompound), 0.0F);
         }
      }

      return super.knockBack(attacker, amount);
   }

   @Shadow
   protected abstract void onCriticalHit(Entity target);

   @Shadow
   protected abstract void onEnchantmentCritical(Entity target);

//   @Shadow
   //Seems like nothing need to be changed
//   public float getCurrentPlayerStrVsBlock(int x, int y, int z, boolean apply_held_item) {
//      Block block = Block.blocksList[super.worldObj.getBlockId(x, y, z)];
//      if (block == null) {
//         return 0.0F;
//      } else {
//         float block_hardness = super.worldObj.getBlockHardness(x, y, z);
//         if (block_hardness == 0.0F) {
//            return 1.0F;
//         } else {
//            float min_str_vs_block = -3.4028235E38F;
//            Item held_item = super.getHeldItem();
//            float str_vs_block;
//            if (block.isPortable(super.worldObj, this, x, y, z)) {
//               str_vs_block = min_str_vs_block = 4.0F * block_hardness;
//            } else {
//               int metadata;
//               if (apply_held_item && held_item != null) {
//                  metadata = super.worldObj.getBlockMetadata(x, y, z);
//                  str_vs_block = held_item.getStrVsBlock(block, metadata, this.getHeldItemStack(), (ServerPlayer)ReflectHelper.dyCast(this));
//                  if (str_vs_block < 1.0F) {
//                     return this.getCurrentPlayerStrVsBlock(x, y, z, false);
//                  }
//
//                  int var4 = EnchantmentManager.getEfficiencyModifier(this);
//                  if (var4 > 0) {
//                     float var6 = (float)(var4 * var4 + 1);
//                     str_vs_block += var6;
//                  }
//               } else {
//                  metadata = super.worldObj.getBlockMetadata(x, y, z);
//                  if (block.blockMaterial.requiresTool(block, metadata)) {
//                     str_vs_block = 0.0F;
//                  } else {
//                     str_vs_block = 1.0F;
//                  }
//               }
//            }
//
//            if (block == Block.web) {
//               boolean decrease_strength = true;
//               if (apply_held_item && held_item != null && held_item.isTool() && held_item.getAsTool().isEffectiveAgainstBlock(block, 0)) {
//                  decrease_strength = false;
//               }
//
//               if (decrease_strength) {
//                  str_vs_block *= 0.2F;
//               }
//            }
//
//            if (super.isPotionActive(MobEffectList.digSpeed)) {
//               str_vs_block *= 1.0F + (float)(super.getActivePotionEffect(MobEffectList.digSpeed).getAmplifier() + 1) * 0.2F;
//            }
//
//            if (super.isPotionActive(MobEffectList.digSlowdown)) {
//               str_vs_block *= 1.0F - (float)(super.getActivePotionEffect(MobEffectList.digSlowdown).getAmplifier() + 1) * 0.2F;
//            }
//
//            if (super.isInsideOfMaterial(Material.water) && !EnchantmentManager.getAquaAffinityModifier(ReflectHelper.dyCast(this))) {
//               str_vs_block /= 5.0F;
//            }
//
//            if (!super.onGround) {
//               str_vs_block /= 5.0F;
//            }
//
//            if (!this.hasFoodEnergy()) {
//               str_vs_block /= 5.0F;
//            }
//
//            str_vs_block *= 1.0F + this.getLevelModifier(EnumLevelBonus.HARVESTING);
//            return Math.max(str_vs_block, min_str_vs_block);
//         }
//      }
//   }


   @Shadow
   public void sendPacket(Packet packet) {
   }

   public void setCraftingBoostFactor(float craftingBoostFactor, @Nullable BlockPos currentEffectedBeaconPos) {
      if (!this.worldObj.isRemote) {
         float result = 0.0F;
         if (currentEffectedBeaconPos != null) {
            if (currentEffectedBeaconPos.equals(this.currentEffectedBeaconPos)) {
               result = craftingBoostFactor;
            } else {
               result = Math.max(craftingBoostFactor, this.craftingBoostFactor);
               if (result != this.craftingBoostFactor) {
                  this.currentEffectedBeaconPos = currentEffectedBeaconPos;
               }
            }
         }

         if (result != this.craftingBoostFactor) {
            this.craftingBoostFactor = result;
            this.sendPacket(new SPacketCraftingBoost(this.craftingBoostFactor));
         }
      } else {
         this.craftingBoostFactor = craftingBoostFactor;
      }

   }

   public void setCraftingBoostTimer(int craftingBoostTimer) {
      this.craftingBoostTimer = craftingBoostTimer;
   }

   @Shadow
   public boolean setWornItem(int i, ItemStack itemStack) {
      return false;
   }

   public void syncItemsAndWait() {
      this.sendPacket(new CPacketSyncItems());
      this.waitForItemSync = true;

      while(this.waitForItemSync) {
      }

   }

   @Shadow
   public abstract void triggerAchievement(Statistic par1StatBase);

   @Shadow
   public void wakeUpPlayer(boolean get_out_of_bed, Entity entity_to_look_at) {
   }

   @Shadow
   public boolean willDeliverCriticalStrike() {
      return false;
   }
}
