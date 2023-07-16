package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.util.Configs;

import java.util.*;

public class EntityZombieBoss extends EntityZombie {
    private Enchantment[] enhanceSpecialBookList = new Enchantment[]{Enchantment.protection, Enchantment.sharpness, Enchantment.fortune, Enchantment.harvesting, Enchantments.EXTEND, Enchantment.efficiency, Enchantment.vampiric, Enchantment.butchering, Enchantment.featherFalling, Enchantment.smite, Enchantment.unbreaking, Enchantment.arrow_recovery, Enchantment.looting, Enchantment.knockback, Enchantment.aquaAffinity, Enchantment.flame, Enchantment.power, Enchantment.silkTouch, Enchantment.endurance, Enchantment.baneOfArthropods, Enchantment.regeneration, Enchantment.thorns, Enchantment.true_flight, Enchantment.speed, Enchantment.stun, Enchantment.fishing_fortune, Enchantment.fireProtection, Enchantment.blastProtection, Enchantment.projectileProtection, Enchantment.free_action, Enchantment.quickness, Enchantment.punch};
    private Enchantment[] nonLevelsBookList = new Enchantment[]{Enchantments.enchantmentFixed, Enchantments.enchantmentChain, Enchantments.EMERGENCY, Enchantments.enchantmentForge};
    private int thunderTick = 0;
    private int attackedCounter = 200;
    public Map<String, Float> attackDamageMap = new HashMap<>();

    public EntityZombieBoss(World par1World) {
        super(par1World);
    }

    protected void addRandomEquipment() {
        this.setCurrentItemOrArmor(0, new ItemStack(Items.VIBRANIUM_WAR_HAMMER, 1).randomizeForMob(this, true));
        this.setCurrentItemOrArmor(1, new ItemStack(Items.VIBRANIUM_HELMET, 1).randomizeForMob(this, true));
        this.setCurrentItemOrArmor(2, new ItemStack(Items.VIBRANIUM_CHESTPLATE, 1).randomizeForMob(this, true));
        this.setCurrentItemOrArmor(3, new ItemStack(Items.VIBRANIUM_LEGGINGS, 1).randomizeForMob(this, true));
        this.setCurrentItemOrArmor(4, new ItemStack(Items.VIBRANIUM_BOOTS, 1).randomizeForMob(this, true));
        this.addPotionEffect(new MobEffect(1, 2147483647, 0));
        this.addPotionEffect(new MobEffect(5, 2147483647, 0));
    }

    public void addPotionEffect(MobEffect par1PotionEffect) {
        if(par1PotionEffect.getPotionID() == 1 || par1PotionEffect.getPotionID() == 5) {
            super.addPotionEffect(par1PotionEffect);
        }
    }

    public void getEnchantBookDependsOnDamageRate(EntityPlayer entityPlayer, int rate) {
        ItemStack var11 = null;
        Enchantment dropEnchantment = Enchantment.enchantmentsList[rand.nextInt(Enchantment.enchantmentsList.length)];
        if(dropEnchantment != null) {
            if(dropEnchantment.getNumLevelsForVibranium() > 1) {
                var11 = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, Math.min(rate, dropEnchantment.getNumLevelsForVibranium())));
            } else {
                if(rate >= 7) {
                    var11 = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, 1));
                } else {
                    getEnchantBookDependsOnDamageRate(entityPlayer, rate);
                }
            }
        } else {
            getEnchantBookDependsOnDamageRate(entityPlayer, rate);
        }
        entityPlayer.inventory.addItemStackToInventoryOrDropIt(var11);
    }

    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player) {
            Enchantment dropEnchantment;
            this.broadcastDamage("僵尸BOSS挑战成功");
            MinecraftServer server = MinecraftServer.F();
            for (Object o : server.getConfigurationManager().playerEntityList) {
                int nums;
                EntityPlayer player = (EntityPlayer)o;
                if (!this.attackDamageMap.containsKey(player.getEntityName()) || (nums = Math.round(this.attackDamageMap.get(player.getEntityName()).floatValue()) / 10) <= 0) continue;
                this.dropItemStack(new ItemStack(Item.diamond, nums));
            }

            float percent = (float)this.nonLevelsBookList.length / ((float)this.enhanceSpecialBookList.length + (float)this.nonLevelsBookList.length);
            if (this.rand.nextFloat() < percent && this.rand.nextInt(5) == 0) {
                dropEnchantment = this.nonLevelsBookList[this.rand.nextInt(this.nonLevelsBookList.length)];
                ItemStack stack = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, dropEnchantment.getNumLevelsForVibranium()));
                if (this.rand.nextInt(2) == 0) {
                    this.dropItemStack(stack);
                }
                return;
            }
            dropEnchantment = this.enhanceSpecialBookList[this.rand.nextInt(this.enhanceSpecialBookList.length)];
            ItemStack stack = Item.enchantedBook.getEnchantedItemStack(new EnchantmentInstance(dropEnchantment, dropEnchantment.getNumLevelsForVibranium()));
            if (this.rand.nextInt(2) == 0) {
                this.dropItemStack(stack);
            }
        }
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int rate = Math.min(200, worldObj.getDayOfOverworld()) / 20;
        this.setEntityAttribute(GenericAttributes.attackDamage, 6 + rate * 2);
        this.setEntityAttribute(GenericAttributes.maxHealth, 20 + rate * 100);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.3D);
    }

    @Override
    protected void tryDamageArmor(DamageSource damage_source, float amount, EntityDamageResult result) {
    }

    public boolean isComfortableInLava()
    {
        return true;
    }

    public boolean getCanSpawnHere(boolean perform_light_check) {
        return true;
    }

    @Override
    public boolean handleLavaMovement() {
        return false;
    }

    @Override
    public boolean handleWaterMovement() {
        return false;
    }

    public boolean canBeDisarmed() {
        return false;
    }

    public boolean canCatchFire() {
        return false;
    }

    public boolean isPushedByWater() {
        return false;
    }


    @Override
    public boolean canNeverPickUpItem(Item item) {
        return true;
    }

    public boolean canBeKnockedBack() {
        return false;
    }

    @Override
    public EntityDamageResult attackEntityFrom(Damage damage) {
        if(damage.getSource().damageType.equals("player") || damage.getSource().damageType.equals("mob")) {
            if(damage.getSource().getResponsibleEntity() instanceof EntityPlayer) {
                EntityPlayer player = ((EntityPlayer) damage.getSource().getResponsibleEntity());
                player.removePotionEffect(MobEffectList.damageBoost.id);
                player.bossResetDamageBoostCounter = 200;
                this.attackedCounter = 200;
                damage.setAmount(damage.getAmount() / 5);
                EntityDamageResult originDamage = super.attackEntityFrom(damage);
                try {
                    if(attackDamageMap.containsKey(player.getEntityName())) {
                        attackDamageMap.put(player.getEntityName(), attackDamageMap.get(player.getEntityName()) + originDamage.getAmountOfHealthLost());
                    } else {
                        attackDamageMap.put(player.getEntityName(), originDamage.getAmountOfHealthLost());
                    }
                } catch (Exception e) {
                    Minecraft.setErrorMessage("BOSS伤害计算错误分析");
                    Minecraft.setErrorMessage(e.getMessage());
                }
                return originDamage;
            }
            return null;
        } else {
            return null;
        }
    }


    public void broadcastDamage(String stateMessage) {
        MinecraftServer server = MinecraftServer.F();
        Iterator var4 = server.getConfigurationManager().playerEntityList.iterator();

        List<Map.Entry<String,Float>> list = new ArrayList<>(attackDamageMap.entrySet());
        Collections.sort(list, (e1, e2) -> (int) Math.floor(e2.getValue() - e1.getValue()));
        while(var4.hasNext()) {
            Object o = var4.next();
            EntityPlayer player = (EntityPlayer)o;
            for(int i = 0; i < Math.min(list.size(), 5); i++) {
                player.sendChatToPlayer(ChatMessage.createFromText("--" + stateMessage + "-伤害排名--"));
                player.sendChatToPlayer(ChatMessage.createFromText("第")
                        .appendComponent(ChatMessage.createFromText("§6" + (i + 1)))
                        .appendComponent(ChatMessage.createFromText("§r名: "))
                        .appendComponent(ChatMessage.createFromText("§n" + list.get(i).getKey()))
                        .appendComponent(ChatMessage.createFromText("§r - "))
                        .appendComponent(ChatMessage.createFromText("§b" + String.format("%.2f", list.get(i).getValue())))
                        .appendComponent(ChatMessage.createFromText("§r点伤害")));
            }
        }
    }

    @Override
    public EntityDamageResult attackEntityAsMob(Entity target) {
        if(target instanceof EntityPlayer) {
            ((EntityPlayer) target).isAttackByBossCounter = 30;
        }
        return super.attackEntityAsMob(target);
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("attackedCounter", (short)this.attackedCounter);
        par1NBTTagCompound.setShort("thunderTick", (short)this.thunderTick);

        NBTTagList nbtTagList = new NBTTagList();
        for (Map.Entry<String, Float> integerEntry : this.attackDamageMap.entrySet()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("Attacker", (integerEntry).getKey());
            compound.setFloat("Damage", (integerEntry).getValue());
            nbtTagList.appendTag(compound);
        }
        par1NBTTagCompound.setTag("AttackDamageMap", nbtTagList);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.attackedCounter = par1NBTTagCompound.getShort("attackedCounter");
        this.thunderTick = par1NBTTagCompound.getShort("thunderTick");
        if (par1NBTTagCompound.hasKey("AttackDamageMap")) {
            NBTTagList attackCountMap = par1NBTTagCompound.getTagList("AttackDamageMap");
            int count = attackCountMap.tagCount();

            for(int i = 0; i < count; ++i) {
                NBTTagCompound a = (NBTTagCompound)attackCountMap.tagAt(i);
                this.attackDamageMap.put(a.getString("Attacker"), a.getFloat("Damage"));
            }
        }
    }

    public void addThunderAttack(EntityPlayer player, float damage) {
        if(player != null) {
            WorldServer var20 = (WorldServer)this.worldObj;
            var20.addWeatherEffect(new EntityLightning(var20, player.posX, player.posY, player.posZ));
            player.attackEntityFrom(new Damage(DamageSource.divine_lightning, damage));
        }
    }

    public boolean setSurroundingPlayersAsTarget() {
        List entities = Arrays.asList(this.getNearbyEntities(16, 16).stream().filter(entity -> entity instanceof EntityPlayer && !((EntityPlayer) entity).isPlayerInCreative()).toArray());
        if(entities.size() > 0) {
            Object targetPlayer = entities.get(rand.nextInt(entities.size()));
            if(targetPlayer instanceof EntityPlayer) {
                this.setTarget((EntityPlayer)targetPlayer);
                return true;
            }
        }
        return false;
    }

    public void healAndBroadcast() {
        if(this.getHealth() < this.getMaxHealth()) {
            this.heal(this.getMaxHealth());
            this.broadcastDamage("僵尸BOSS挑战失败");
            this.attackDamageMap.clear();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            thunderTick ++;
            if(attackedCounter <= 0) {
                this.healAndBroadcast();
            } else {
                attackedCounter --;
            }
            EntityLiving target = this.getTarget();
            if(!(target instanceof EntityPlayer)) {
                this.healAndBroadcast();
            }
            if(thunderTick % 20 == 0) {
                if(target != null && target instanceof EntityPlayer) {
                    if(((EntityPlayer) target).isAttackByBossCounter <= 0) {
                        addThunderAttack((EntityPlayer)target, 10f);
                    }
                }
                if(thunderTick == 60) {
                    this.setSurroundingPlayersAsTarget();
                    thunderTick = 0;
                }
            }
        }
    }
}
