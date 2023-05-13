package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(BiomeDecorator.class)
public class BiomeDecoratorTrans {
   private static final int ADAMANTIUM_FREQUENCY_OVERWORLD = Configs.wenscConfig.adamantiumFrequencyOverworld.ConfigValue;
   private static final int ADAMANTIUM_FREQUENCY_UNDERWORLD = Configs.wenscConfig.adamantiumFrequencyUnderworld.ConfigValue;
   private static final int COPPER_FREQUENCY_OVERWORLD = Configs.wenscConfig.copperFrequencyOverworld.ConfigValue;
   private static final int COPPER_FREQUENCY_UNDERWORLD = Configs.wenscConfig.copperFrequencyUnderworld.ConfigValue;
   private static final int DIAMOND_FREQUENCY_OVERWORLD = Configs.wenscConfig.diamondFrequencyOverworld.ConfigValue;
   private static final int DIAMOND_FREQUENCY_UNDERWORLD = Configs.wenscConfig.diamondFrequencyUnderworld.ConfigValue;

   private static final int FANCY_RED_FREQUENCY_UNDERWORLD = Configs.wenscConfig.fancyRedFrequencyUnderworld.ConfigValue;
   private static final int GOLD_FREQUENCY_OVERWORLD = Configs.wenscConfig.goldFrequencyOverworld.ConfigValue;
   private static final int GOLD_FREQUENCY_UNDERWORLD = Configs.wenscConfig.goldFrequencyUnderworld.ConfigValue;
   private static final int IRON_FREQUENCY_OVERWORLD = Configs.wenscConfig.ironFrequencyOverworld.ConfigValue;
   private static final int IRON_FREQUENCY_UNDERWORLD = Configs.wenscConfig.ironFrequencyUnderworld.ConfigValue;
   private static final int LAPIS_FREQUENCY_OVERWORLD = Configs.wenscConfig.lapisFrequencyOverworld.ConfigValue;
   private static final int LAPIS_FREQUENCY_UNDERWORLD = Configs.wenscConfig.lapisFrequencyUnderworld.ConfigValue;
   private static final int MITHRIL_FREQUENCY_OVERWORLD = Configs.wenscConfig.mithrilFrequencyOverworld.ConfigValue;
   private static final int MITHRIL_FREQUENCY_UNDERWORLD = Configs.wenscConfig.mithrilFrequencyUnderworld.ConfigValue;
   private static final int SILVER_FREQUENCY_OVERWORLD = Configs.wenscConfig.silverFrequencyOverworld.ConfigValue;
   private static final int SILVER_FREQUENCY_UNDERWORLD = Configs.wenscConfig.silverFrequencyUnderworld.ConfigValue;
   @Shadow
   public boolean generateLakes;
   @Shadow
   protected WorldGenMinable adamantiteGen;
   @Shadow
   protected WorldGenerator bigMushroomGen;
   @Shadow
   protected BiomeBase biome;
   @Shadow
   protected WorldGenPlants bush_gen;
   @Shadow
   protected WorldGenerator cactusGen;
   @Shadow
   protected int chunk_X;
   @Shadow
   protected int chunk_Z;
   @Shadow
   protected WorldGenerator clayGen = new WorldGenClay(4);
   @Shadow
   protected int clayPerChunk;
   @Shadow
   protected WorldGenMinable coalGen;
   @Shadow
   protected WorldGenMinable copperGen;
   @Shadow
   protected World currentWorld;
   @Shadow
   protected WorldGenMinable diamondGen;

   protected WorldGenMinable fancyRedGen = new WorldGenMinable(Blocks.fancyRed.blockID, 3);
   @Shadow
   protected WorldGenMinable dirtGen;
   @Shadow
   protected int flowersPerChunk;
   @Shadow
   protected WorldGenMinable goldGen;
   @Shadow
   protected int grassPerChunk;
   @Shadow
   protected WorldGenerator gravelAsSandGen;
   @Shadow
   protected WorldGenMinable gravelGen;
   @Shadow
   protected WorldGenMinable ironGen;
   @Shadow
   protected WorldGenMinable lapisGen;
   @Shadow
   protected WorldGenMinable mithrilGen;
   @Shadow
   protected WorldGenerator mushroomBrownGen;
   @Shadow
   protected WorldGenerator mushroomRedGen;
   @Shadow
   protected WorldGenFlowers plantRedGen;
   @Shadow
   protected WorldGenerator plantYellowGen;
   @Shadow
   protected Random randomGenerator;
   @Shadow
   protected WorldGenMinable redstoneGen;
   @Shadow
   protected WorldGenerator reedGen;
   @Shadow
   protected WorldGenerator sandGen;
   @Shadow
   protected int sandPerChunk;
   @Shadow
   protected int sandPerChunk2;
   @Shadow
   protected WorldGenMinable silverGen;
   @Shadow
   protected WorldGenMinable silverfishGen;
   @Shadow
   protected int treesPerChunk;
   @Shadow
   protected WorldGenerator waterlilyGen;
   @Shadow
   protected int waterlilyPerChunk;

   public BiomeDecoratorTrans(BiomeBase par1BiomeGenBase) {
      this.sandGen = new WorldGenSand(7, Block.sand.blockID);
      this.gravelAsSandGen = new WorldGenSand(6, Block.gravel.blockID);
      this.plantYellowGen = new WorldGenFlowers(Block.plantYellow.blockID);
      this.plantRedGen = new WorldGenFlowers(Block.plantRed.blockID);
      this.mushroomBrownGen = new WorldGenFlowers(Block.mushroomBrown.blockID);
      this.mushroomRedGen = new WorldGenFlowers(Block.mushroomRed.blockID);
      this.bigMushroomGen = new WorldGenHugeMushroom();
      this.reedGen = new WorldGenReed();
      this.cactusGen = new WorldGenCactus();
      this.waterlilyGen = new WorldGenWaterLily();
      this.flowersPerChunk = 2;
      this.grassPerChunk = 1;
      this.sandPerChunk = 1;
      this.sandPerChunk2 = 3;
      this.clayPerChunk = 1;
      this.generateLakes = true;
      this.biome = par1BiomeGenBase;
      this.dirtGen = new WorldGenMinable(Block.dirt.blockID, 32);
      this.gravelGen = new WorldGenMinable(Block.gravel.blockID, 32);
      this.coalGen = new WorldGenMinable(Block.oreCoal.blockID, 16);
      this.copperGen = new WorldGenMinable(Block.oreCopper.blockID, 6);
      this.silverGen = new WorldGenMinable(Block.oreSilver.blockID, 6);
      this.goldGen = new WorldGenMinable(Block.oreGold.blockID, 4);
      this.ironGen = new WorldGenMinable(Block.oreIron.blockID, 6);
      this.mithrilGen = new WorldGenMinable(Block.oreMithril.blockID, 3);
      this.adamantiteGen = new WorldGenMinable(Block.oreAdamantium.blockID, 2);
      this.redstoneGen = new WorldGenMinable(Block.oreRedstone.blockID, 5);
      this.diamondGen = new WorldGenMinable(Block.oreDiamond.blockID, 3);
      this.lapisGen = new WorldGenMinable(Block.oreLapis.blockID, 2);
      this.silverfishGen = new WorldGenMinable(Block.silverfish.blockID, 2);
      this.bush_gen = new WorldGenPlants(Block.bush);
   }

   @Shadow
   protected void genMinable(int frequency, WorldGenMinable world_gen_minable) {
   }

   @Overwrite
   protected void genMinable(int frequency, WorldGenMinable world_gen_minable, boolean vein_size_increases_with_depth) {
      int resource_multiplier = 1;
      frequency *= resource_multiplier;
//      if (this.currentWorld.underworld_y_offset != 0 && world_gen_minable != this.gravelGen) {
//         if (world_gen_minable == this.adamantiteGen) {
//            frequency *= 3;
//         } else {
//            frequency *= 4;
//         }
//      }
      while(frequency-- > 0) {
         if (this.randomGenerator.nextInt(10) == 0) {
            int x = this.chunk_X + this.randomGenerator.nextInt(16);
            int y = world_gen_minable.getRandomVeinHeight(this.currentWorld, this.randomGenerator);
            int z = this.chunk_Z + this.randomGenerator.nextInt(16);
            if (y >= 0) {
               world_gen_minable.generate(this.currentWorld, this.randomGenerator, x, y, z, vein_size_increases_with_depth);
            }
         }
      }
   }

   protected void generateOres() {
      if (this.currentWorld.isOverworld()) {
         this.genMinable(200, this.dirtGen);
         this.genMinable(200, this.gravelGen);
         this.genMinable(50, this.coalGen);
         this.genMinable(COPPER_FREQUENCY_OVERWORLD, this.copperGen, true);
         this.genMinable(SILVER_FREQUENCY_OVERWORLD, this.silverGen, true);
         this.genMinable(GOLD_FREQUENCY_OVERWORLD, this.goldGen, true);
         this.genMinable(IRON_FREQUENCY_OVERWORLD, this.ironGen, true);
         this.genMinable(MITHRIL_FREQUENCY_OVERWORLD, this.mithrilGen, true);
         this.genMinable(5, this.silverfishGen, true);
         this.genMinable(10, this.redstoneGen);
         this.genMinable(DIAMOND_FREQUENCY_OVERWORLD, this.diamondGen);
         this.genMinable(LAPIS_FREQUENCY_OVERWORLD, this.lapisGen);
         if (Configs.wenscConfig.overworldAdamantiteOre.ConfigValue) {
            this.genMinable(ADAMANTIUM_FREQUENCY_OVERWORLD, this.adamantiteGen, false);
         }
      } else if (this.currentWorld.isUnderworld()) {
         this.genMinable(300, this.gravelGen);
         this.genMinable(COPPER_FREQUENCY_UNDERWORLD, this.copperGen, false);
         this.genMinable(SILVER_FREQUENCY_UNDERWORLD, this.silverGen, false);
         this.genMinable(GOLD_FREQUENCY_UNDERWORLD, this.goldGen, false);
         this.genMinable(IRON_FREQUENCY_UNDERWORLD, this.ironGen, false);
         this.genMinable(MITHRIL_FREQUENCY_UNDERWORLD, this.mithrilGen, false);
         this.genMinable(ADAMANTIUM_FREQUENCY_UNDERWORLD, this.adamantiteGen, false);
         this.genMinable(5, this.redstoneGen);
         this.genMinable(DIAMOND_FREQUENCY_UNDERWORLD, this.diamondGen);
         this.genMinable(FANCY_RED_FREQUENCY_UNDERWORLD, this.fancyRedGen);
         this.genMinable(LAPIS_FREQUENCY_UNDERWORLD, this.lapisGen);
         if (this.currentWorld.underworld_y_offset != 0) {
            this.genMinable(50, this.silverfishGen);
         }
      } else if (!this.currentWorld.isTheEnd()) {
         Minecraft.setErrorMessage("generateOres: don't know how to handle world " + this.currentWorld);
      }

   }

}
