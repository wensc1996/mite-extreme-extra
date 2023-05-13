package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.gui.GuiForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(bex.class)
public abstract class ClientPlayerTrans extends beu {

   @Shadow
   protected Minecraft d;

   public ClientPlayerTrans(World par1World, String par2Str) {
      super(par1World, par2Str);
   }

   @Shadow
   public void a(ChatMessage chatMessage) {
   }

   @Shadow
   public boolean a(int i, String s) {
      return false;
   }

   @Shadow
   public ChunkCoordinates b() {
      return null;
   }

   @Shadow public abstract void addChatMessage(String par1Str);

   public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
      this.d.a(new axj(ReflectHelper.dyCast(this), par1IInventory));
   }

   public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots) {
      this.d.a(new GuiForgingTable(ReflectHelper.dyCast(this), x, y, z, slots));
   }

   @Overwrite
   public void displayGUIChestForMinecart(IInventory par1IInventory) {
      this.d.a(new axj(this, par1IInventory));
   }

   @Overwrite
   public void displayGUIChest(int x, int y, int z, IInventory par1IInventory) {
      this.d.a(new axj(this, par1IInventory));
   }

//   public void displayGUIJewelry(int x, int y, int z, JewelrySlot slots) {
//      this.d.a(new GuiForgingTable(ReflectHelper.dyCast(this), x, y, z, slots));
//   }

   @Overwrite
   private float getBenchAndToolsModifier(Container container) {
      if (!(container instanceof ContainerWorkbench)) {
         return 0.0F;
      } else {
         ContainerWorkbench container_workbench = (ContainerWorkbench)container;
         SlotResult slot_crafting = (SlotResult)container_workbench.getSlot(0);
         ItemStack item_stack = slot_crafting.getStack();
         Item item = item_stack == null ? null : item_stack.getItem();
         aah recipe = container_workbench.getRecipe();
         Material material_to_check_tool_bench_hardness_against = recipe == null ? item.getHardestMetalMaterial() : recipe.getMaterialToCheckToolBenchHardnessAgainst();
         Material tool_material = BlockWorkbench.getToolMaterial(container_workbench.getBlockMetadata());
         if (material_to_check_tool_bench_hardness_against == null) {
            return tool_material == Materials.vibranium ? 3.4028235E38F : 0.2F;
         } else if (tool_material != Material.flint && tool_material != Material.obsidian) {
            if (tool_material != Material.copper && tool_material != Material.silver && tool_material != Material.gold) {
               if (tool_material == Material.iron) {
                  return 0.4F;
               } else if (tool_material == Material.ancient_metal) {
                  return 0.5F;
               } else if (tool_material == Material.mithril) {
                  return 0.6F;
               } else if (tool_material == Material.adamantium) {
                  return 0.7F;
               } else if (tool_material == Materials.vibranium) {
                  return 3.4028235E38F;
               } else {
                  Minecraft.setErrorMessage("getBenchAndToolsModifier: unrecognized tool material " + tool_material);
                  return 0.0F;
               }
            } else {
               return 0.3F;
            }
         } else {
            return 0.2F;
         }
      }
   }

//   @Overwrite
//   private float getBenchAndToolsModifier(Container container) {
//      if (!(container instanceof ContainerWorkbench)) {
//         return 0.0F;
//      } else {
//         ContainerWorkbench container_workbench = (ContainerWorkbench) container;
//         SlotResult slot_crafting = (SlotResult) container_workbench.getSlot(0);
//         ItemStack item_stack = slot_crafting.getStack();
//         Item item = item_stack == null ? null : item_stack.getItem();
//         aah recipe = container_workbench.getRecipe();
//         Material material_to_check_tool_bench_hardness_against;
//         if (recipe == null) {
//            material_to_check_tool_bench_hardness_against = item.getHardestMetalMaterial();
//         } else {
//            material_to_check_tool_bench_hardness_against = recipe.getMaterialToCheckToolBenchHardnessAgainst();
//         }
//         if (material_to_check_tool_bench_hardness_against == null){
//            return 0f;
//         }
//
//         Material benchMaterial = BlockWorkbench.getToolMaterial(container_workbench.getBlockMetadata());
//         if (benchMaterial.getMinHarvestLevel() < material_to_check_tool_bench_hardness_against.getMinHarvestLevel()) {
//            return 0F;
//         }
//         if (benchMaterial == Material.flint || benchMaterial == Material.obsidian) {
//            return 0F;
//         } else if (benchMaterial == Material.copper || benchMaterial == Material.silver || benchMaterial == Material.gold) {
//            return 0.1F;
//         } else if (benchMaterial == Material.iron) {
//            return 0.2F;
//         } else if (benchMaterial == Material.ancient_metal) {
//            return 0.3F;
//         } else if (benchMaterial == Material.mithril) {
//            return 0.3F;
//         } else if (benchMaterial == Material.adamantium) {
//            return 0.4F;
//         } else if (benchMaterial == Materials.vibranium) {
//            return 1F;
//         } else {
//            Minecraft.setErrorMessage("getBenchAndToolsModifier: unrecognized tool material " + benchMaterial);
//            return 0.0F;
//         }
//
//      }
//   }

//   @Overwrite
//   public int getCraftingPeriod(float quality_adjusted_crafting_difficulty) {
//      int period = bex.calcUnmodifiedCraftingPeriod(quality_adjusted_crafting_difficulty);
//      if (this.hasCurse(Curse.clumsiness)) {
//         period *= 2;
//      }
//
//      float bench_and_tools_modifier = this.getBenchAndToolsModifier(this.openContainer);
//      return Math.round(Math.max((float)period * (1.0F - bench_and_tools_modifier) * (1.0f - Math.min(this.getExperienceLevel(),100) * 0.006f), 1) / (this.getCraftingBoostFactor() + 1.0F));
//   }
}
