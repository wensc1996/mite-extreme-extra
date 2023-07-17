package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.GemModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ItemLeaves1;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableLevel;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.RecipeRegister;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.injection.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static net.xiaoyu233.mitemod.miteite.item.Items.VIBRANIUM_INGOT;
import static net.xiaoyu233.mitemod.miteite.item.Items.VIBRANIUM_NUGGET;

public class Blocks extends Block{
    public static final Block blockForgingTable = new BlockForgingTable(getNextBlockID())
            .setBlockHardness(8.0F).setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep);

    public static final BlockAnvil anvilVibranium = ReflectHelper.createInstance(BlockAnvil.class, new Class[]{int.class, Material.class}, getNextBlockID(), Materials.vibranium);
    public static final BlockOreBlock blockVibranium = new BlockOreBlock(getNextBlockID(),Materials.vibranium);
    public static final Block furnaceVibraniumBurning = new BlockFurnaceVibranium(getNextBlockID(), true)
            .setBlockHardness(8.0F)
            .setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep);
    public static final Block furnaceVibraniumIdle = new BlockFurnaceVibranium(getNextBlockID(),false).setCreativeTab(CreativeModeTab.tabDecorations)
            .setBlockHardness(8.0F).setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep);
    public static final Block netherAdamantiumOre = new BlockNetherAdamantiumOre(getNextBlockID())
            .setCreativeTab(CreativeModeTab.tabBlock)
            .setBlockHardness(4.0F)
            .setStepSound_(soundStoneFootstep)
            .setUnlocalizedName("oreNetherAdamantium");
    public static final Block chestVibranium = (ReflectHelper.createInstance(BlockStrongbox.class,new Class[] {int.class,Material.class},getNextBlockID(), Materials.vibranium))
            .setStepSound_(soundMetalFootstep);

    public static final BlockGotcha blockGotcha = (new BlockGotcha(getNextBlockID(), Material.glass, false)).setCraftingDifficultyAsComponent(1.0E-11F);

    public static final Block blockColorful = (ReflectHelper.createInstance(BlockColorful.class, new Class[] {int.class,Material.class}, getNextBlockID(), Materials.stone)).setUnlocalizedName("blockColorful");
    public static final Block blockColorfulBrick = (ReflectHelper.createInstance(BlockColorfulBrick.class, new Class[] {int.class,Material.class}, getNextBlockID(), Materials.stone)).setUnlocalizedName("blockColorfulBrick");
    public static final Block blockLantern = (ReflectHelper.createInstance(BlockLantern.class, new Class[] {int.class,Material.class}, 174, Materials.circuits)).setHardness(0.0F).setStepSound_(soundPowderFootstep).setUnlocalizedName("blockLantern");

    public static final Block blockStairsColorful0 = new BlockStairsColorful(175, blockColorful, 0);
    public static final Block blockStairsColorful1 = new BlockStairsColorful(176, blockColorful, 1);
    public static final Block blockStairsColorful2 = new BlockStairsColorful(177, blockColorful, 2);
    public static final Block blockStairsColorful3 = new BlockStairsColorful(178, blockColorful, 3);
    public static final Block blockStairsColorful4 = new BlockStairsColorful(179, blockColorful, 4);
    public static final Block blockStairsColorful5 = new BlockStairsColorful(181, blockColorful, 5);
    public static final Block blockStairsColorful6 = new BlockStairsColorful(182, blockColorful, 6);
    public static final Block blockStairsColorful7 = new BlockStairsColorful(183, blockColorful, 7);
    public static final Block blockStairsColorful8 = new BlockStairsColorful(184, blockColorful, 8);
    public static final Block blockStairsColorful9 = new BlockStairsColorful(185, blockColorful, 9);
    public static final Block blockStairsColorful10 = new BlockStairsColorful(186, blockColorful, 10);
    public static final Block blockStairsColorful11 = new BlockStairsColorful(187, blockColorful, 11);
    public static final Block blockStairsColorful12 = new BlockStairsColorful(188, blockColorful, 12);
    public static final Block blockStairsColorful13 = new BlockStairsColorful(189, blockColorful, 13);
    public static final Block blockStairsColorful14 = new BlockStairsColorful(190, blockColorful, 14);
    public static final Block blockStairsColorful15 = new BlockStairsColorful(191, blockColorful, 15);

    public static final BlockSpawn blockSpawn = (new BlockSpawn(192, Material.stone));

    public static final Block fancyRed = (new BlockFancyRed(193, Material.diamond, 4)).setCreativeTab(CreativeModeTab.tabBlock);

    public static final Block gemSetting = (new BlockGemSetting(194, Material.stone, (new BlockConstants()).setNeverHidesAdjacentFaces())).setUnlocalizedName("gem_setting").setCreativeTab(CreativeModeTab.tabDecorations);

    public static final BlockBed blackBed = (BlockBed)(new BlockBed(256).setSub(0).setAttr(0.2F, 0.6F, "black_bed", "black_bed"));
    public static final BlockBed redBed = (BlockBed)(new BlockBed(257).setSub(1).setAttr(0.2F, 0.6F, "red_bed", "red_bed"));
    public static final BlockBed greenBed = (BlockBed)(new BlockBed(258).setSub(2).setAttr(0.2F, 0.6F, "green_bed", "green_bed"));
    public static final BlockBed brownBed = (BlockBed)(new BlockBed(259).setSub(3).setAttr(0.2F, 0.6F, "brown_bed", "brown_bed"));
    public static final BlockBed blueBed = (BlockBed)(new BlockBed(260).setSub(4).setAttr(0.2F, 0.6F, "blue_bed", "blue_bed"));
    public static final BlockBed purpleBed = (BlockBed)(new BlockBed(261).setSub(5).setAttr(0.2F, 0.6F, "purple_bed", "purple_bed"));
    public static final BlockBed cyanBed = (BlockBed)(new BlockBed(262).setSub(6).setAttr(0.2F, 0.6F, "cyan_bed", "cyan_bed"));
    public static final BlockBed silverBed = (BlockBed)(new BlockBed(263).setSub(7).setAttr(0.2F, 0.6F, "silver_bed", "silver_bed"));
    public static final BlockBed grayBed = (BlockBed)(new BlockBed(264).setSub(8).setAttr(0.2F, 0.6F, "gray_bed", "gray_bed"));
    public static final BlockBed pinkBed = (BlockBed)(new BlockBed(265).setSub(9).setAttr(0.2F, 0.6F, "pink_bed", "pink_bed"));
    public static final BlockBed limeBed = (BlockBed)(new BlockBed(266).setSub(10).setAttr(0.2F, 0.6F, "lime_bed", "lime_bed"));
    public static final BlockBed yellowBed = (BlockBed)(new BlockBed(267).setSub(11).setAttr(0.2F, 0.6F, "yellow_bed", "yellow_bed"));
    public static final BlockBed lightBlueBed = (BlockBed)(new BlockBed(268).setSub(12).setAttr(0.2F, 0.6F, "light_blue_bed", "light_blue_bed"));
    public static final BlockBed magentaBed = (BlockBed)(new BlockBed(269).setSub(13).setAttr(0.2F, 0.6F, "magenta_bed", "magenta_bed"));
    public static final BlockBed orangeBed = (BlockBed)(new BlockBed(270).setSub(14).setAttr(0.2F, 0.6F, "yellow_bed", "yellow_bed"));

    public static final BlockLog1 wood1 = (BlockLog1)(new BlockLog1(271));
    public static final BlockLeaves1 leaves1 = (BlockLeaves1)(new BlockLeaves1(272));
    public static final Block sapling1 = (new BlockSapling1(195));

    public static final Block stairsMaple = (new BlockStairs1(273, planks, 4)).setUnlocalizedName("stairsMaple");
    public static final Block stairsCherry = (new BlockStairs1(274, planks, 5)).setUnlocalizedName("stairsCherry");

    static {
        try {
            Field field = Block.class.getDeclaredField("is_normal_cube_lookup");
            field.setAccessible(true);
            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null,new boolean[4096]);
            boolean[] is_normal_block = (boolean[]) field.get(null);
            for (Block block : Block.blocksList) {
                if (block !=null) {
                    is_normal_block[block.blockID] = block.is_normal_cube;
                }
            }
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        Block.bed.setAttr(0.2F, 0.6F, "white_bed", "white_bed").setSub(15);
    }

    protected Blocks(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    private static void registerAnvil(BlockAnvil block,String resourceLocation){
        block.setUnlocalizedName(resourceLocation);
        block.setResourceLocation(resourceLocation);
        Item item = new ItemAnvil(block).setUnlocalizedName(resourceLocation);
        Item.itemsList[block.blockID] = item;
        item.setMaxStackSize(block.getItemStackLimit());
    }
    public static int getNextBlockID() {
        return Constant.nextBlockID++;
    }

    private static void registerBlock(Block block,String resourceLocation){
        block.setUnlocalizedName(resourceLocation);
        block.setResourceLocation(resourceLocation);
    }

    public static void registerBlocks(){

        registerAnvil(anvilVibranium,"anvil_vibranium");
        registerItemBlock(blockVibranium,"block_vibranium");
        anvilVibranium.stepSound = Block.soundAnvilFootstep;
        registerItemBlock(furnaceVibraniumIdle,"furnace_vibranium_idle");
        registerItemBlock(furnaceVibraniumBurning,"furnace_vibranium_burning");
        registerItemBlock(blockForgingTable,"block_forging_table");
        registerItemBlock(netherAdamantiumOre,"nether_adamantium_ore");
        registerItemBlock(chestVibranium,"vibranium_chest");

        registerItemBlock(blockGotcha, "gotcha");
        registerItemBlock(blockSpawn, "block_spawn");
        registerItemBlock(fancyRed, "fancy_red");
        registerItemBlock(gemSetting, "gem_setting");

        registerItemBlock(blockColorful, "colorful");
        registerItemBlock(blockColorfulBrick, "colorful_brick");
        registerItemBlock(blockLantern, "block_lantern");

        registerItemBlock(blockStairsColorful0, "colorful_stair");
        registerItemBlock(blockStairsColorful1, "colorful_stair");
        registerItemBlock(blockStairsColorful2, "colorful_stair");
        registerItemBlock(blockStairsColorful3, "colorful_stair");
        registerItemBlock(blockStairsColorful4, "colorful_stair");
        registerItemBlock(blockStairsColorful5, "colorful_stair");
        registerItemBlock(blockStairsColorful6, "colorful_stair");
        registerItemBlock(blockStairsColorful7, "colorful_stair");
        registerItemBlock(blockStairsColorful8, "colorful_stair");
        registerItemBlock(blockStairsColorful9, "colorful_stair");
        registerItemBlock(blockStairsColorful10, "colorful_stair");
        registerItemBlock(blockStairsColorful11, "colorful_stair");
        registerItemBlock(blockStairsColorful12, "colorful_stair");
        registerItemBlock(blockStairsColorful13, "colorful_stair");
        registerItemBlock(blockStairsColorful14, "colorful_stair");
        registerItemBlock(blockStairsColorful15, "colorful_stair");

        registerItemBlock(blackBed, (Item) null);
        registerItemBlock(redBed, (Item) null);
        registerItemBlock(greenBed, (Item) null);
        registerItemBlock(brownBed, (Item) null);
        registerItemBlock(blueBed, (Item) null);
        registerItemBlock(purpleBed, (Item) null);
        registerItemBlock(cyanBed, (Item) null);
        registerItemBlock(silverBed, (Item) null);
        registerItemBlock(grayBed, (Item) null);
        registerItemBlock(pinkBed, (Item) null);
        registerItemBlock(limeBed, (Item) null);
        registerItemBlock(yellowBed, (Item) null);
        registerItemBlock(lightBlueBed, (Item) null);
        registerItemBlock(magentaBed, (Item) null);
        registerItemBlock(orangeBed, (Item) null);
        registerItemBlock(wood1, (new ItemMultiTexture(wood1, wood1.getNames())).setUnlocalizedName("log1"));
        registerItemBlock(leaves1, (new ItemLeaves1(leaves1)).setUnlocalizedName("leaves1"));
        registerItemBlock(sapling1, (new ItemMultiTexture(sapling1, BlockSapling1.WOOD_TYPES)).setUnlocalizedName("sapling1"));
    }

    private static void registerItemBlock(Block block,String resourceLocation){
        block.setUnlocalizedName(resourceLocation);
        block.setResourceLocation(resourceLocation);
        Item item = new ItemBlock(block).setUnlocalizedName(resourceLocation);
        item.setMaxStackSize(block.getItemStackLimit());
        Item.itemsList[block.blockID] = item;
    }

    private static void registerItemBlock(Block block, Item item){
        if(item != null) {
            item.setMaxStackSize(block.getItemStackLimit());
            Item.itemsList[block.blockID] = item;
        } else {
            Item itemBlock = new ItemBlock(block);
            itemBlock.setMaxStackSize(block.getItemStackLimit());
            Item.itemsList[block.blockID] = itemBlock;
        }
    }


    public static void registerRecipes(RecipeRegister register) {
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful0, 9), true, new ItemStack(blockStairsColorful15, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful1, 9), true, new ItemStack(blockStairsColorful0, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful2, 9), true, new ItemStack(blockStairsColorful1, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful3, 9), true, new ItemStack(blockStairsColorful2, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful4, 9), true, new ItemStack(blockStairsColorful3, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful5, 9), true, new ItemStack(blockStairsColorful4, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful6, 9), true, new ItemStack(blockStairsColorful5, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful7, 9), true, new ItemStack(blockStairsColorful6, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful8, 9), true, new ItemStack(blockStairsColorful7, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful9, 9), true, new ItemStack(blockStairsColorful8, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful10, 9), true, new ItemStack(blockStairsColorful9, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful11, 9), true, new ItemStack(blockStairsColorful10, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful12, 9), true, new ItemStack(blockStairsColorful11, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful13, 9), true, new ItemStack(blockStairsColorful12, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful14, 9), true, new ItemStack(blockStairsColorful13, 9));
        register.registerShapelessRecipe(new ItemStack(blockStairsColorful15, 9), true, new ItemStack(blockStairsColorful14, 9));

        for(int i = 0; i < 15; ++i) {
            register.registerShapelessRecipe(new ItemStack(blockGotcha, 1, i + 1), true, new ItemStack(blockGotcha, 1, i));

            register.registerShapelessRecipe(new ItemStack(blockColorful, 9, i + 1), true, new ItemStack(blockColorful, 9, i));
            register.registerShapelessRecipe(new ItemStack(blockColorfulBrick, 9, i + 1), true, new ItemStack(blockColorfulBrick, 9, i));
        }
        register.registerShapelessRecipe(new ItemStack(blockGotcha, 1, 0), true, new ItemStack(blockGotcha, 1, 15));
        register.registerShapelessRecipe(new ItemStack(blockColorful, 9, 0), true, new ItemStack(blockColorful, 9, 15));
        register.registerShapelessRecipe(new ItemStack(blockColorfulBrick, 9, 0), true, new ItemStack(blockColorfulBrick, 9, 15));

        register.registerShapedRecipe(new ItemStack(blockSpawn), true,
                "ABA",
                "BCB",
                "ABA",
                'A', Blocks.obsidian,
                'B', Items.diamond,
                'C', Items.enderPearl);
        register.registerShapedRecipe(new ItemStack(anvilVibranium),true,
                "AVA",
                " I ",
                "IaI",
                'A', Item.ingotAdamantium,
                'V', blockVibranium,
                'I', VIBRANIUM_INGOT,
                'a', Block.anvilAncientMetal
        );
        register.registerShapelessRecipe(new ItemStack(blockVibranium),true,
                VIBRANIUM_INGOT, VIBRANIUM_INGOT, VIBRANIUM_INGOT,
                VIBRANIUM_INGOT, VIBRANIUM_INGOT, VIBRANIUM_INGOT,
                VIBRANIUM_INGOT, VIBRANIUM_INGOT, VIBRANIUM_INGOT
        );
        register.registerShapedRecipe(new ItemStack(Blocks.furnaceVibraniumIdle),true,
                "VOA",
                "DND",
                "AOV",
                'V',VIBRANIUM_INGOT,
                'O',Block.obsidian,
                'D',Item.diamond,
                'A',Item.ingotAdamantium,
                'N', Block.furnaceNetherrackIdle
        );
        register.registerShapedRecipe(new ItemStack(Blocks.blockForgingTable,1,0),true,
                "WIT",
                " A ",
                "OOO",
                'W',Block.planks,
                'A',Block.anvil,
                'I', Items.ingotIron,
                'T',new ItemStack(Block.workbench,1,7),
                'O',Blocks.obsidian);

        for(int i = 0; i < GemModifierTypes.values().length; i++) {
            register.registerShapedRecipe(new ItemStack(Blocks.gemSetting,1, 0),false,
                    "AAA",
                    "BCB",
                    "BBB",
                    'A', Items.ingotIron,
                    'B',Block.planks,
                    'C',new ItemStack(Items.itemEnhanceGem, 1, i)
            );
        }


        register.registerShapedRecipe(new ItemStack(Blocks.chestVibranium), true,
                "III",
                "I I",
                "III",
                'I', VIBRANIUM_INGOT);
        registerForgingTableUpgradeRecipes(register,ForgingTableLevel.IRON,Item.ingotMithril);
        registerForgingTableUpgradeRecipes(register,ForgingTableLevel.MITHRIL,Item.ingotAdamantium);
        registerForgingTableUpgradeRecipes(register,ForgingTableLevel.ADAMANTIUM, VIBRANIUM_INGOT);
        RecipesFurnace.smelting().addSmelting(Blocks.netherAdamantiumOre.blockID, new ItemStack(Item.ingotAdamantium));

        RecipesFurnace.smelting().addSmelting(Blocks.blockCopper.blockID, new ItemStack(Items.itemEnhanceGemBox));
        RecipesFurnace.smelting().addSmelting(Blocks.blockSilver.blockID, new ItemStack(Items.itemEnhanceGemBox));
        RecipesFurnace.smelting().addSmelting(Blocks.blockGold.blockID, new ItemStack(Items.itemEnhanceGemBox));
        RecipesFurnace.smelting().addSmelting(Blocks.blockIron.blockID, new ItemStack(Items.itemGemShard.itemID, 1, 0));
        RecipesFurnace.smelting().addSmelting(Blocks.blockAncientMetal.blockID, new ItemStack(Items.itemGemShard.itemID, 1, 1));
        RecipesFurnace.smelting().addSmelting(Blocks.blockMithril.blockID, new ItemStack(Items.itemGemShard.itemID, 1, 2));
        RecipesFurnace.smelting().addSmelting(Blocks.blockAdamantium.blockID, new ItemStack(Items.itemGemShard.itemID, 1, 3));
        RecipesFurnace.smelting().addSmelting(Blocks.blockVibranium.blockID, new ItemStack(Items.itemGemShard.itemID, 1, 4));

        RecipesFurnace.smelting().addSmelting(Blocks.wood1.blockID, new ItemStack(Item.coal, 1, 1));
    }

    private static void registerForgingTableUpgradeRecipes(RecipeRegister register, ForgingTableLevel originalLevel, Item ingot){
        register.registerShapedRecipe(new ItemStack(Blocks.blockForgingTable,1,originalLevel.getLevel() + 1),true,
                "III",
                " T ",
                'I', ingot,
                'T', new ItemStack(Blocks.blockForgingTable,1,originalLevel.getLevel()));
    }
}