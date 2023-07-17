package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableRecipes;
import net.xiaoyu233.mitemod.miteite.trans.item.recipe.CraftingManagerInvoker;

import java.util.ArrayList;
import java.util.List;

import static net.xiaoyu233.mitemod.miteite.item.Items.VIBRANIUM_INGOT;
import static net.xiaoyu233.mitemod.miteite.item.Items.VIBRANIUM_NUGGET;

@SuppressWarnings("RedundantCast")
public class RegisterHelper {
    public static ArrayList<RecipesArgs> shapedRecipes = new ArrayList<>();
    public static ArrayList<RecipesArgs> shapelessRecipe = new ArrayList<>();
    public static void registerAllItems(){
        Items.registerItems();
        Blocks.registerBlocks();
        // 当所有物品和方块注册完之后开始执行价格初始化
        registerPrice();
        Achievements.registerAchievements();
    }

    public static void registerPrice() {
        for (Item item: Item.itemsList) {
            if(item != null) {
                if(item.getHasSubtypes()) {
                    List subs = item.getSubItems();
                    for (int i = 0; i < subs.size(); i++) {
                        ItemStack itemStack = (ItemStack) subs.get(i);
                        int sub = itemStack.getItemSubtype();
                        item.soldPriceArray.put(sub, 0D);
                        item.buyPriceArray.put(sub, 0D);
                    }
                } else {
                    item.soldPriceArray.put(0, 0D);
                    item.buyPriceArray.put(0, 0D);
                }
            }
        }
        Items.manure.setSoldPrice(0.1D);
        Items.seeds.setSoldPrice(0.1D);
        Items.sinew.setSoldPrice(0.1D);
        Items.silk.setSoldPrice(0.1D);
        Items.feather.setSoldPrice(0.1D);
        Items.flint.setSoldPrice(0.1D);
        Items.redstone.setSoldPrice(1D);
        Items.coal.setSoldPrice(1D);
        Items.bone.setSoldPrice(1D);
        Items.gunpowder.setSoldPrice(1D);
        Items.rottenFlesh.setSoldPrice(1D);
        Items.spiderEye.setSoldPrice(1D);
        Items.doorWood.setSoldPrice(1.5D);
        Items.doorCopper.setSoldPrice(30D);
        Items.doorSilver.setSoldPrice(30D);
        Items.doorGold.setSoldPrice(30D);
        Items.doorIron.setSoldPrice(60D);
        Items.doorMithril.setSoldPrice(120D);
        Items.doorAdamantium.setSoldPrice(180D);
        Items.ingotCopper.setSoldPrice(10D);
        Items.ingotSilver.setSoldPrice(10D);
        Items.ingotGold.setSoldPrice(10D);
        Items.ingotIron.setSoldPrice(20D);
        Items.ingotAncientMetal.setSoldPrice(30D);
        Items.ingotMithril.setSoldPrice(40D);
        Items.ingotAdamantium.setSoldPrice(60D);
        VIBRANIUM_INGOT.setSoldPrice(100D);
        Items.copperNugget.setSoldPrice(1D);
        Items.silverNugget.setSoldPrice(1D);
        Items.goldNugget.setSoldPrice(1D);
        Items.ironNugget.setSoldPrice(2D);
        Items.ancientMetalNugget.setSoldPrice(3D);
        Items.mithrilNugget.setSoldPrice(4D);
        Items.adamantiumNugget.setSoldPrice(6D);
        VIBRANIUM_NUGGET.setSoldPrice(10D);
        Items.bread.setBuyPrice(0.2D).setSoldPrice(0.2D);

        Items.getItem(Blocks.blockStairsColorful0.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful1.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful2.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful3.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful4.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful5.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful6.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful7.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful8.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful9.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful10.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful11.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful12.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful13.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful14.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockStairsColorful15.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);

        Items.getItem(Blocks.blockColorful.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockColorfulBrick.blockID).setBuyPrice(0.5D).setSoldPrice(0.5D);
        Items.getItem(Blocks.blockGotcha.blockID).setSoldPrice(2D).setBuyPrice(2D);

        Items.getItem(Blocks.plantYellow.blockID).setSoldPrice(0.1D);
        Items.getItem(Blocks.plantRed.blockID).setSoldPrice(0.1D);
        Items.getItem(Blocks.torchWood.blockID).setSoldPrice(0.2D);

        Items.getItem(Blocks.leaves.blockID).setSoldPrice(0.25D);
        Items.getItem(Blocks.leaves1.blockID).setSoldPrice(0.25D);
        Items.getItem(Blocks.planks.blockID).setSoldPrice(0.25D);
        Items.getItem(Blocks.pumpkin.blockID).setSoldPrice(0.25D);
        Items.getItem(Blocks.dirt.blockID).setSoldPrice(0.5D);
        Items.getItem(Blocks.sand.blockID).setSoldPrice(0.5D);
        Items.getItem(Blocks.cobblestone.blockID).setSoldPrice(1D);
        Items.getItem(Blocks.stone.blockID).setSoldPrice(2D);
        Items.getItem(Blocks.cobblestoneWall.blockID).setSoldPrice(1D);
        Items.getItem(Blocks.wood.blockID).setSoldPrice(1D);
        Items.getItem(Blocks.wood1.blockID).setSoldPrice(1D);
        // 开始注入商品价格配置文件
        Configs.beginToLoadShopConfig();
    }
    public static void registerAllRecipes(CraftingManager crafters){
        RecipeRegister recipeRegister = new RecipeRegister();
        Blocks.registerRecipes(recipeRegister);
        Items.registerRecipes(recipeRegister);
        ForgingTableRecipes.registerAll();


        RecipesArgs recipesArgs;
        for (RecipesArgs shapedRecipe : shapedRecipes) {
            recipesArgs = shapedRecipe;
            ShapedRecipes shapedRecipes = ((CraftingManagerInvoker) crafters).addRecipeP(recipesArgs.result, recipesArgs.include_in_lowest_crafting_difficulty_determination, recipesArgs.inputs);
            if (recipesArgs.isExtendsNBT()){
                shapedRecipes.func_92100_c();
            }
        }
        for (RecipesArgs args : shapelessRecipe) {
            recipesArgs = args;
            ShapelessRecipes shapelessRecipes = ((CraftingManagerInvoker) crafters).addShapelessRecipeP(recipesArgs.result, recipesArgs.include_in_lowest_crafting_difficulty_determination, recipesArgs.inputs);
            if (recipesArgs.isExtendsNBT()){
                shapelessRecipes.propagateTagCompound();
            }
        }
    }
}
