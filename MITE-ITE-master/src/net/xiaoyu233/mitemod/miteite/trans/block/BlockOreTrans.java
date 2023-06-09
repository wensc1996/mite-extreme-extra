package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(BlockOre.class)
public class BlockOreTrans extends Block {

    protected BlockOreTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Overwrite
    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        int metadata_dropped = -1;
        int quantity_dropped = 1;
        int id_dropped;
        if (info.wasExploded()) {
            if (this == oreEmerald) {
                id_dropped = Item.shardEmerald.itemID;
            } else if (this == oreDiamond) {
                id_dropped = Item.shardDiamond.itemID;
            } else if (this == oreLapis) {
                id_dropped = Item.dyePowder.itemID;
                metadata_dropped = 4;
                quantity_dropped = 3 + info.world.rand.nextInt(3);
            } else if (this == oreNetherQuartz) {
                id_dropped = Item.shardNetherQuartz.itemID;
            } else if (this == oreCoal) {
                id_dropped = -1;
            } else {
                id_dropped = this.blockID;
            }
        } else {
            if (info.wasHarvestedByPlayer() && info.getResponsiblePlayer().worldObj.areSkillsEnabled() && !info.getResponsiblePlayer().hasSkill(Skill.MINING)) {
                return super.dropBlockAsEntityItem(info);
            }

            if (this == oreCoal) {
                id_dropped = Item.coal.itemID;
            } else if (this == oreDiamond) {
                id_dropped = Item.diamond.itemID;
            } else if (this == oreLapis) {
                id_dropped = Item.dyePowder.itemID;
                metadata_dropped = 4;
                quantity_dropped = 3 + info.world.rand.nextInt(3);
            } else if (this == oreEmerald) {
                id_dropped = Item.emerald.itemID;
            } else if (this == oreNetherQuartz) {
                id_dropped = Item.netherQuartz.itemID;
            } else if(this == Blocks.fancyRed){
                id_dropped = Items.fancyRed.itemID;
            } else {
                id_dropped = this.blockID;
            }
        }

        if (metadata_dropped == -1) {
            metadata_dropped = id_dropped == this.blockID ? this.getItemSubtype(info.getMetadata()) : 0;
        }

        boolean suppress_fortune = id_dropped == this.blockID && BitHelper.isBitSet(info.getMetadata(), 1);
        if (id_dropped != -1 && info.getMetadata() == 0) {
            DedicatedServer.incrementTournamentScoringCounter(info.getResponsiblePlayer(), Item.getItem(id_dropped));
        }

        float chance = suppress_fortune ? 1.0F : 1.0F + (float)info.getHarvesterFortune() * 0.1F;
        int total = super.dropBlockAsEntityItem(info, id_dropped, metadata_dropped, quantity_dropped, chance);
        if(info.wasHarvestedByPlayer()) {
            if(EnchantmentManager.hasEnchantment(info.getResponsiblePlayer().getHeldItemStack(), Enchantments.enchantmentChain)) {
                for(int dx = -1; dx <= 1; ++dx) {
                    for(int dy = -1; dy <= 1; ++dy) {
                        for(int dz = -1; dz <= 1; ++dz) {
                            Block searchedBlock = info.world.getBlock(info.x + dx, info.y + dy, info.z + dz);
                            if(searchedBlock != null) {
                                BlockBreakInfo entity_item = (new BlockBreakInfo(info.world, info.x + dx, info.y + dy, info.z + dz)).setHarvestedBy(info.getResponsiblePlayer());
                                if(searchedBlock.blockID == this.blockID) {
                                    if(searchedBlock == oreLapis || searchedBlock.getItemSubtype(entity_item.getMetadata()) == metadata_dropped) {
                                        if (info.world.setBlockToAir(info.x + dx, info.y + dy, info.z + dz))
                                        {
                                            total += super.dropBlockAsEntityItem(entity_item, id_dropped, metadata_dropped, quantity_dropped, chance);
                                            if(info.getResponsiblePlayer().getHeldItem() instanceof ItemTool) {
                                                if (this.canDropExperienceOrbs()) {
                                                    int xp_reward_per_unit = Item.getItem(info.block.blockID).getExperienceReward(info.getMetadata());
                                                    if (xp_reward_per_unit > 0) {
                                                        this.dropXpOnBlockBreak(info.world, info.x, info.y, info.z, xp_reward_per_unit);
                                                    }
                                                }
                                                info.getResponsiblePlayer().getHeldItem().addExpForTool(info.getHarvesterItemStack(), info.getResponsiblePlayer(), info.block.blockMaterial.getMinHarvestLevel());
                                                info.getResponsiblePlayer().tryDamageHeldItem(DamageSource.generic, ((ItemTool)info.getResponsiblePlayer().getHeldItem()).getToolDecayFromBreakingBlock(info));
                                                if(info.getResponsiblePlayer().getHeldItemStack().getMaxDamage() - info.getResponsiblePlayer().getHeldItemStack().getItemDamage() - ((ItemTool)info.getResponsiblePlayer().getHeldItem()).getToolDecayFromBreakingBlock(info) <= 0) {
                                                    return total;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return total;
    }
}
