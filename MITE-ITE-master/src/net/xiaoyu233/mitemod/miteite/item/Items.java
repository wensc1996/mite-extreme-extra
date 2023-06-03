package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.RecipeRegister;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.createInstance;

public class Items extends Item{
    public static final Item BLAZE_COAL_POWDER = new ItemBlazeCoalPowder(Constant.getNextItemID());
    public static final Item DIAMOND_CHUNK = createInstance(Item.class,new Class[]{int.class,Material.class,String.class},Constant.getNextItemID(),Material.diamond,"diamond_chunk").setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.diamond) /(float)4);
    public static final Item OBSIDIAN_STICK = createInstance(Item.class,new Class[]{int.class,Material.class,String.class},Constant.getNextItemID(),Material.obsidian,"obsidian_stick");
    public static final ItemAxe VIBRANIUM_AXE = createInstance(ItemAxe.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemBattleAxe VIBRANIUM_BATTLE_AXE = createInstance(ItemBattleAxe.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_BOOTS = new ItemBoots(Constant.getNextItemID(),Materials.vibranium,false);
    public static final ItemBow VIBRANIUM_BOW = new ItemBow(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_CHESTPLATE = new ItemCuirass(Constant.getNextItemID(), Materials.vibranium, false);
    public static final ItemDagger VIBRANIUM_DAGGER = createInstance(ItemDagger.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_HELMET = new ItemHelmet(Constant.getNextItemID(),Materials.vibranium,false);
    public static final Item VIBRANIUM_INGOT = createInstance(ItemIngot.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_LEGGINGS = new ItemLeggings(Constant.getNextItemID(),Materials.vibranium,false);
    public static final ItemNugget VIBRANIUM_NUGGET = createInstance(ItemNugget.class, new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemPickaxe VIBRANIUM_PICKAXE = createInstance(ItemPickaxe.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemShovel VIBRANIUM_SHOVEL = createInstance(ItemShovel.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemSword VIBRANIUM_SWORD = createInstance(ItemSword.class,new Class[]{int.class,Material.class},Constant.getNextItemID(), Materials.vibranium);
    public static final ItemWarHammer VIBRANIUM_WAR_HAMMER = createInstance(ItemWarHammer.class,new Class[]{int.class,Material.class},Constant.getNextItemID(),Materials.vibranium);
    public static final ItemEnhanceStone IRON_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.iron).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.iron) * 2f);
    public static final ItemEnhanceStone MITHRIL_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.mithril).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.mithril) * 2f);
    public static final ItemEnhanceStone ADAMANTIUM_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.adamantium).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.diamond) * 2f);
    public static final ItemEnhanceStone UNIVERSAL_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.universal).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.ender_pearl) * 2f);

    public static final Item lavaInPipes = new ItemLavaInPipes(Constant.getNextItemID(), Materials.lava);

    public static final Item clubIron = new ItemClubMetal(Constant.getNextItemID(), Material.iron);
    public static final Item clubMithril = new ItemClubMetal(Constant.getNextItemID(), Material.mithril);
    public static final Item clubAdamantium = new ItemClubMetal(Constant.getNextItemID(), Material.adamantium);
    public static final Item clubVibranium = new ItemClubMetal(Constant.getNextItemID(), Materials.vibranium);

    public static final Item ringKillerCopper = new ItemRingKiller(Constant.getNextItemID(), Materials.copper).setUnlocalizedName("ringKillerCopper");
    public static final Item ringKillerIron = new ItemRingKiller(Constant.getNextItemID(), Materials.iron).setUnlocalizedName("ringKillerIron");
    public static final Item ringKillerAncient = new ItemRingKiller(Constant.getNextItemID(), Materials.ancient_metal).setUnlocalizedName("ringKillerAncient");
    public static final Item ringKillerMithril = new ItemRingKiller(Constant.getNextItemID(), Materials.mithril).setUnlocalizedName("ringKillerMithril");
    public static final Item ringKillerAdamantium = new ItemRingKiller(Constant.getNextItemID(), Materials.adamantium).setUnlocalizedName("ringKillerAdamantium");
    public static final Item ringKillerVibranium = new ItemRingKiller(Constant.getNextItemID(), Materials.vibranium).setUnlocalizedName("ringKillerVibranium");
    public static final Item Stack_Torch = createInstance(Item.class, new Class[]{int.class, Material.class, String.class}, Constant.getNextItemID(), Material.circuits, "stack_torch");

    public static final Item voucherExchanger = new ItemMobVoucher(Constant.getNextItemID(), "exchanger");
    public static final Item voucherDoor = new ItemMobVoucher(Constant.getNextItemID(), "door");
    public static final Item voucherZombieLord = new ItemMobVoucher(Constant.getNextItemID(), "zombie_lord");
    public static final Item voucherAnnihilationSkeleton = new ItemMobVoucher(Constant.getNextItemID(), "annihilation_skeleton");
    public static final Item voucherPigman = new ItemMobVoucher(Constant.getNextItemID(), "pigman");
    public static final Item voucherWitch = new ItemMobVoucher(Constant.getNextItemID(), "witch");
    public static final Item voucherCore = new ItemMobVoucher(Constant.getNextItemID(), "core");
    public static final Item voucherFishing = new ItemMobVoucher(Constant.getNextItemID(), "fishing");
    public static final Item voucherPlanting = new ItemMobVoucher(Constant.getNextItemID(), "planting");
    public static final Item voucherVillager = new ItemMobVoucher(Constant.getNextItemID(), "villager");
    public static final Item voucherClubCore = new ItemMobVoucher(Constant.getNextItemID(), "club_core");

    public static final Item itemDynamicCoreIron = new ItemDynamicCore(Constant.getNextItemID(), Materials.iron, 1).setUnlocalizedName("dynamic_core_iron");
    public static final Item itemDynamicCoreAncient_metal = new ItemDynamicCore(Constant.getNextItemID(), Materials.ancient_metal, 2).setUnlocalizedName("dynamic_core_ancient_metal");
    public static final Item itemDynamicCoreMithril = new ItemDynamicCore(Constant.getNextItemID(), Materials.mithril, 3).setUnlocalizedName("dynamic_core_mithril");

    public static final Item itemDynamicCoreAdamantium = new ItemDynamicCore(Constant.getNextItemID(), Materials.adamantium, 4).setUnlocalizedName("dynamic_core_adamantium");
    public static final Item itemDynamicCoreVibranium = new ItemDynamicCore(Constant.getNextItemID(), Materials.vibranium, 5).setUnlocalizedName("dynamic_core_vibranium");
    public static final Item fancyRed = (new ItemFancyRed(Constant.getNextItemID(), Material.diamond, "fancy_red"));

    public static ItemEnhanceGem itemEnhanceGem = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 1)).setUnlocalizedName("enhance_gem_phase1");
    public static ItemEnhanceGem itemEnhanceGem2 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 2)).setUnlocalizedName("enhance_gem_phase2");
    public static ItemEnhanceGem itemEnhanceGem3 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 3)).setUnlocalizedName("enhance_gem_phase3");
    public static ItemEnhanceGem itemEnhanceGem4 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 4)).setUnlocalizedName("enhance_gem_phase4");
    public static ItemEnhanceGem itemEnhanceGem5 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 5)).setUnlocalizedName("enhance_gem_phase5");
    public static ItemEnhanceGem itemEnhanceGem6 = (ItemEnhanceGem)(new ItemEnhanceGem(Constant.getNextItemID(), 6)).setUnlocalizedName("enhance_gem_phase6");

    public static ItemEnhanceGemBox itemEnhanceGemBox = (ItemEnhanceGemBox)(new ItemEnhanceGemBox(Constant.getNextItemID())).setUnlocalizedName("enhance_gem_box_phase1");
    public static ItemGemShard itemGemShard = (ItemGemShard)(new ItemGemShard(Constant.getNextItemID())).setUnlocalizedName("gem_shard");

    private static Item register(String resourceLocation, Item item, CreativeModeTab tab) {
        item.setResourceLocation(item.getResourceLocationPrefix() + resourceLocation);
        item.setUnlocalizedName(resourceLocation);
        item.setCreativeTab(tab);
        return item;
    }

    private static Item register(String resourceLocation, Item item) {
        item.setResourceLocation(item.getResourceLocationPrefix() + resourceLocation);
        item.setUnlocalizedName(resourceLocation);
        return item;
    }

    @Overwrite
    public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List) {
//        par3List.add(new ItemStack(par1, 1, 0));
    }

    public static void registerItems() {
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

        Items.bread.setItemPrice(0.2D).setSoldPrice(0.2D);
        Items.brewingStand.setItemPrice(500D);
        Items.netherStalkSeeds.setItemPrice(10D);

        Items.getItem(Blocks.plantYellow.blockID).setSoldPrice(0.1D);
        Items.getItem(Blocks.plantRed.blockID).setSoldPrice(0.1D);
        Items.getItem(Blocks.torchWood.blockID).setSoldPrice(0.2D);

        Items.getItem(Blocks.leaves.blockID).setSoldPrice(0.25D);
        Items.getItem(Blocks.planks.blockID).setSoldPrice(0.25D);
        Items.getItem(Blocks.pumpkin.blockID).setSoldPrice(0.25D);
        Items.getItem(Blocks.dirt.blockID).setSoldPrice(0.5D);
        Items.getItem(Blocks.sand.blockID).setSoldPrice(0.5D);
        Items.getItem(Blocks.cobblestone.blockID).setSoldPrice(1D);
        Items.getItem(Blocks.stone.blockID).setSoldPrice(2D);
        Items.getItem(Blocks.cobblestoneWall.blockID).setSoldPrice(1D);
        Items.getItem(Blocks.wood.blockID).setSoldPrice(1D);

        register("obsidian_stick", OBSIDIAN_STICK, CreativeModeTab.tabMaterials);
        register("vibranium", VIBRANIUM_INGOT, CreativeModeTab.tabMaterials);
        register("vibranium_nugget", VIBRANIUM_NUGGET, CreativeModeTab.tabMaterials);
        register("vibranium_helmet", VIBRANIUM_HELMET);
        register("vibranium_chestplate", VIBRANIUM_CHESTPLATE);
        register("vibranium_leggings", VIBRANIUM_LEGGINGS);
        register("vibranium_boots", VIBRANIUM_BOOTS);
        register("vibranium_sword", VIBRANIUM_SWORD);
        register("vibranium_shovel", VIBRANIUM_SHOVEL);
        register("vibranium_pickaxe", VIBRANIUM_PICKAXE);
        register("vibranium_axe", VIBRANIUM_AXE);
        register("vibranium_dagger", VIBRANIUM_DAGGER);
        register("vibranium_war_hammer", VIBRANIUM_WAR_HAMMER);
        register("vibranium_battle_axe", VIBRANIUM_BATTLE_AXE);
        register("diamond_chunk", DIAMOND_CHUNK, CreativeModeTab.tabMaterials);
        register("blaze_coal_powder", BLAZE_COAL_POWDER, CreativeModeTab.tabMaterials);
        register("bows/vibranium/", VIBRANIUM_BOW).setUnlocalizedName("vibranium_bow");
        register("enhance_stone/iron",IRON_ENHANCE_STONE , CreativeModeTab.tabMaterials);
        register("enhance_stone/mithril",MITHRIL_ENHANCE_STONE , CreativeModeTab.tabMaterials);
        register("enhance_stone/adamantium",ADAMANTIUM_ENHANCE_STONE , CreativeModeTab.tabMaterials);
        register("enhance_stone/universal",UNIVERSAL_ENHANCE_STONE , CreativeModeTab.tabMaterials);

        register("iron_club", clubIron).setUnlocalizedName("iron_club").setLowestCraftingDifficultyToProduce(1.0F);
        register("mithril_club", clubMithril).setUnlocalizedName("mithril_club").setLowestCraftingDifficultyToProduce(1.0F);
        register("adamantium_club", clubAdamantium).setUnlocalizedName("adamantium_club").setLowestCraftingDifficultyToProduce(1.0F);
        register("vibranium_club", clubVibranium).setUnlocalizedName("vibranium_club").setLowestCraftingDifficultyToProduce(1.0F);

        register("stack_torch", Stack_Torch).setUnlocalizedName("stack_torch").setMaxStackSize(64).setCraftingDifficultyAsComponent(1.0E-9F);

        register("lava_in_pipes", lavaInPipes).setUnlocalizedName("lava_in_pipes").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_copper", ringKillerCopper).setUnlocalizedName("ring_killer_copper").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_iron", ringKillerIron).setUnlocalizedName("ring_killer_iron").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_ancient", ringKillerAncient).setUnlocalizedName("ring_killer_ancient").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_mithril", ringKillerMithril).setUnlocalizedName("ring_killer_mithril").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_adamantium", ringKillerAdamantium).setUnlocalizedName("ring_killer_adamantium").setLowestCraftingDifficultyToProduce(1.0F);
        register("ring_killer/ring_killer_vibranium", ringKillerVibranium).setUnlocalizedName("ring_killer_vibranium").setLowestCraftingDifficultyToProduce(1.0F);

        register("voucher/voucher_exchanger", voucherExchanger).setUnlocalizedName("voucher_exchanger").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_door", voucherDoor).setUnlocalizedName("voucher_door").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_zombie_lord", voucherZombieLord).setUnlocalizedName("voucher_zombie_lord").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_annihilation_skeleton", voucherAnnihilationSkeleton).setUnlocalizedName("voucher_annihilation_skeleton").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_pigman", voucherPigman).setUnlocalizedName("voucher_pigman").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_witch", voucherWitch).setUnlocalizedName("voucher_witch").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_core", voucherCore).setUnlocalizedName("voucher_core").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_fishing", voucherFishing).setUnlocalizedName("voucher_fishing").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_planting", voucherPlanting).setUnlocalizedName("voucher_planting").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_villager", voucherVillager).setUnlocalizedName("voucher_villager").setLowestCraftingDifficultyToProduce(1.0F);
        register("voucher/voucher_club_core", voucherClubCore).setUnlocalizedName("voucher_club_core").setLowestCraftingDifficultyToProduce(1.0F);

        register("dynamic_core/dynamic_core_1", itemDynamicCoreIron).setUnlocalizedName("dynamic_core_iron").setLowestCraftingDifficultyToProduce(1.0F);
        register("dynamic_core/dynamic_core_2", itemDynamicCoreAncient_metal).setUnlocalizedName("dynamic_core_ancient_metal").setLowestCraftingDifficultyToProduce(1.0F);
        register("dynamic_core/dynamic_core_3", itemDynamicCoreMithril).setUnlocalizedName("dynamic_core_mithril").setLowestCraftingDifficultyToProduce(1.0F);
        register("dynamic_core/dynamic_core_4", itemDynamicCoreAdamantium).setUnlocalizedName("dynamic_core_adamantium").setLowestCraftingDifficultyToProduce(1.0F);
        register("dynamic_core/dynamic_core_5", itemDynamicCoreVibranium).setUnlocalizedName("dynamic_core_vibranium").setLowestCraftingDifficultyToProduce(1.0F);

        register("fancy_red", fancyRed).setUnlocalizedName("fancy_red").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase1", itemEnhanceGem).setUnlocalizedName("enhance_gem_phase1").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase2", itemEnhanceGem2).setUnlocalizedName("enhance_gem_phase2").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase3", itemEnhanceGem3).setUnlocalizedName("enhance_gem_phase3").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase4", itemEnhanceGem4).setUnlocalizedName("enhance_gem_phase4").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase5", itemEnhanceGem5).setUnlocalizedName("enhance_gem_phase5").setLowestCraftingDifficultyToProduce(1.0F);
        register("gem/enhance_gem_phase5", itemEnhanceGem6).setUnlocalizedName("enhance_gem_phase6").setLowestCraftingDifficultyToProduce(1.0F);

        Constant.initItemArray();
    }

    public static void registerRecipes(RecipeRegister register) {
        register.registerShapelessRecipe(new ItemStack(Blocks.blockLantern, 1), true,Blocks.torchWood, ironNugget, ironNugget, ironNugget, ironNugget, ironNugget, ironNugget, ironNugget, ironNugget);
        register.registerShapelessRecipe(new ItemStack(Items.voucherCore, 1), true, Items.voucherAnnihilationSkeleton, Items.voucherDoor, Items.voucherExchanger, Items.voucherPigman, Items.voucherZombieLord, Items.voucherWitch);
        register.registerShapedRecipe(new ItemStack(clubIron, 1), true, new Object[]{"###", "#*#"," # ", '#', Items.ironNugget , '*', Items.ingotIron});
        register.registerShapedRecipe(new ItemStack(clubMithril, 1), true, new Object[]{"###", "#*#"," # ", '#', Items.mithrilNugget , '*', Items.ingotMithril});
        register.registerShapedRecipe(new ItemStack(clubAdamantium, 1), true, new Object[]{"###", "#*#"," # ", '#', Items.adamantiumNugget , '*', Items.ingotAdamantium});
        register.registerShapedRecipe(new ItemStack(clubVibranium, 1), true, new Object[]{"###", "#*#"," A ", '#', Items.VIBRANIUM_NUGGET , '*', Items.VIBRANIUM_INGOT, 'A', Items.voucherClubCore});

        register.registerShapedRecipe(new ItemStack(itemDynamicCoreIron, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.ingotIron, 'D', Blocks.blockIron , 'B', Blocks.glass, 'C', Blocks.blockRedstone});
        register.registerShapedRecipe(new ItemStack(itemDynamicCoreAncient_metal, 1), true, new Object[]{"ABA", "BCB","DBD",'A', Items.ingotAncientMetal, 'D', Blocks.blockAncientMetal , 'B', Blocks.glass, 'C', Items.itemDynamicCoreIron});
        register.registerShapedRecipe(new ItemStack(itemDynamicCoreMithril, 1), true, new Object[]{"ABA", "BCB","DBD",'A', Items.ingotMithril, 'D', Blocks.blockMithril , 'B', Blocks.glass, 'C', Items.itemDynamicCoreAncient_metal});
        register.registerShapedRecipe(new ItemStack(itemDynamicCoreAdamantium, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.ingotAdamantium, 'D', Blocks.blockAdamantium , 'B', Blocks.glass, 'C', Items.itemDynamicCoreMithril});
        register.registerShapedRecipe(new ItemStack(itemDynamicCoreVibranium, 1), true, new Object[]{"ABA", "BCB","DBD", 'A', Items.VIBRANIUM_INGOT, 'D', Blocks.blockVibranium , 'B', Blocks.glass, 'C', Items.itemDynamicCoreAdamantium});

        register.registerShapelessRecipe(new ItemStack(Items.voucherClubCore, 1), true, Items.voucherFishing, Items.voucherVillager, Items.voucherPlanting);

        if(Configs.wenscConfig.isRecipeGATorch.ConfigValue) {
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{Block.wood, Item.silk, Item.coal, Item.coal});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{Block.wood, Item.sinew, Item.coal, Item.coal});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{Block.wood, Item.silk, new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{Block.wood, Item.sinew, new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 1), Item.silk, Item.coal, Item.coal});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 1), Item.sinew, Item.coal, Item.coal});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 1), Item.silk, new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 1), Item.sinew, new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 2), Item.silk, Item.coal, Item.coal});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 2), Item.sinew, Item.coal, Item.coal});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 2), Item.silk, new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 2), Item.sinew, new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 3), Item.silk, Item.coal, Item.coal});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 3), Item.sinew, Item.coal, Item.coal});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 3), Item.silk, new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
            register.registerShapelessRecipe(new ItemStack(Stack_Torch, 1), true, new Object[]{new ItemStack(Block.wood, 1, 3), Item.sinew, new ItemStack(Item.coal, 1, 1), new ItemStack(Item.coal, 1, 1)});
            register.registerShapelessRecipe(new ItemStack(Block.torchWood, 64), true, new Object[]{Stack_Torch, Stack_Torch, Stack_Torch, Stack_Torch});
            register.registerShapelessRecipe(new ItemStack(Block.torchWood, 48), true, new Object[]{Stack_Torch, Stack_Torch, Stack_Torch});
            register.registerShapelessRecipe(new ItemStack(Block.torchWood, 32), true, new Object[]{Stack_Torch, Stack_Torch});
            register.registerShapelessRecipe(new ItemStack(Block.torchWood, 16), true, new Object[]{Stack_Torch});
        }


        if(Configs.wenscConfig.isRecipeRingKiller.ConfigValue) {
            register.registerShapedRecipe(new ItemStack(ringKillerCopper, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordCopper , '*', Items.emerald});
            register.registerShapedRecipe(new ItemStack(ringKillerIron, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordIron , '*', Items.ringKillerCopper});
            register.registerShapedRecipe(new ItemStack(ringKillerAncient, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordAncientMetal , '*', Items.ringKillerIron});
            register.registerShapedRecipe(new ItemStack(ringKillerMithril, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordMithril , '*', Items.ringKillerAncient});
            register.registerShapedRecipe(new ItemStack(ringKillerAdamantium, 1), true, new Object[]{"###", "#*#","###", '#', Items.swordAdamantium , '*', Items.ringKillerMithril});
            register.registerShapedRecipe(new ItemStack(ringKillerVibranium, 1), true, new Object[]{"###", "#*#","###", '#', VIBRANIUM_SWORD , '*', Items.ringKillerAdamantium});
        }

        for(int i =0; i < GemModifierTypes.values().length; i++) {
            register.registerShapedRecipe(new ItemStack(itemEnhanceGem2, 1, i), true, new Object[]{" # ", "#*#"," # ", '#', new ItemStack(Items.itemGemShard, 1, 0), '*', new ItemStack(itemEnhanceGem, 1, i)});
            register.registerShapedRecipe(new ItemStack(itemEnhanceGem3, 1, i), true, new Object[]{" # ", "#*#"," # ", '#', new ItemStack(Items.itemGemShard, 1, 1), '*', new ItemStack(itemEnhanceGem2, 1, i)});
            register.registerShapedRecipe(new ItemStack(itemEnhanceGem4, 1, i), true, new Object[]{" # ", "#*#"," # ", '#', new ItemStack(Items.itemGemShard, 1, 2), '*', new ItemStack(itemEnhanceGem3, 1, i)});
            register.registerShapedRecipe(new ItemStack(itemEnhanceGem5, 1, i), true, new Object[]{" # ", "#*#"," # ", '#', new ItemStack(Items.itemGemShard, 1, 3), '*', new ItemStack(itemEnhanceGem4, 1, i)});
            register.registerShapedRecipe(new ItemStack(itemEnhanceGem6, 1, i), true, new Object[]{" # ", "#*#"," # ", '#', new ItemStack(Items.itemGemShard, 1, 4), '*', new ItemStack(itemEnhanceGem5, 1, i)});
        }

        register.registerShapedRecipe(new ItemStack(OBSIDIAN_STICK), true, "#", "#", '#', Block.obsidian);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_INGOT),
                false,
                "NIN",
                "IDI",
                "NIN",
                'N',
                Item.mithrilNugget,
                'I',
                Item.ingotAdamantium,
                'D',
                Items.voucherCore);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_NUGGET, 9), true, VIBRANIUM_INGOT);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_INGOT),
                true,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_HELMET),
                true,
                "#A#",
                "# #",
                '#', VIBRANIUM_INGOT,
                'A', Item.helmetAdamantium).extendsNBT();
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_CHESTPLATE),
                true,
                "# #",
                "#A#",
                "###",
                '#', VIBRANIUM_INGOT,
                'A', Item.plateAdamantium).extendsNBT();
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_LEGGINGS),
                true,
                "#A#",
                "# #",
                "# #",
                '#', VIBRANIUM_INGOT,
                'A', Item.legsAdamantium).extendsNBT();
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_BOOTS),
                true,
                "A #",
                "# #",
                '#', VIBRANIUM_INGOT,
                'A', Item.bootsAdamantium).extendsNBT();
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_PICKAXE),
                true,
                "###",
                " A ",
                " S ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', pickaxeAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_SHOVEL),
                true,
                "#",
                "A",
                "S",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', shovelAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_SWORD),
                true,
                " # ",
                " # ",
                " A ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', swordAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_AXE),
                true,
                "##",
                "A#",
                "S ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', axeAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_DAGGER),
                true,
                "I",
                "A",
                'I', VIBRANIUM_INGOT,
                'A', daggerAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_WAR_HAMMER),
                true,
                "III",
                "IAI",
                " S ",
                'I', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', warHammerAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_BATTLE_AXE),
                true,
                "I I",
                "IAI",
                " S ",
                'I', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', battleAxeAncientMetal);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_INGOT, 9), true, Blocks.blockVibranium);
        register.registerShapelessRecipe(new ItemStack(Item.diamond),
                false,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK);
        register.registerShapedRecipe(new ItemStack(BLAZE_COAL_POWDER),
                true,
                " B ",
                "BCB",
                " B ",
                'B', Item.blazePowder,
                'C', Item.coal);
        RecipesFurnace.smelting().addSmelting(Block.coalBlock.blockID, new ItemStack(DIAMOND_CHUNK));
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_BOW),
                true,
                "NSL",
                "AVL",
                "NSL",
                'S', OBSIDIAN_STICK,
                'V', VIBRANIUM_INGOT,
                'L', Item.silk,
                'N', VIBRANIUM_NUGGET,
                'A', bowAncientMetal);
        register.registerShapedRecipe(new ItemStack(Item.emerald),
                true,
                "SS",
                "SS",
                'S', Item.shardEmerald);
        register.registerShapelessRecipe(new ItemStack(Item.shardEmerald, 4), true, Item.emerald);
        register.registerShapedRecipe(new ItemStack(IRON_ENHANCE_STONE),
                true,
                " C ",
                "SIS",
                " C ",
                'I', Item.ingotIron,
                'C', Item.ingotCopper,
                'S', Item.ingotSilver);
        register.registerShapedRecipe(new ItemStack(MITHRIL_ENHANCE_STONE),
                true,
                " I ",
                "GMG",
                " I ",
                'I', Item.ingotIron,
                'G', Item.ingotGold,
                'M', Item.ingotMithril);
        register.registerShapedRecipe(new ItemStack(ADAMANTIUM_ENHANCE_STONE),
                true,
                " D ",
                "MAM",
                " R ",
                'R', Block.blockRedstone,
                'D', Item.diamond,
                'M', Item.ingotMithril,
                'A', Item.ingotAdamantium);
        register.registerShapedRecipe(new ItemStack(UNIVERSAL_ENHANCE_STONE),
                true,
                "gDg",
                "bTb",
                "BEB",
                'g', Item.glowstone,
                'D', Item.bottleOfDisenchanting,
                'b', Item.blazePowder,
                'T', Item.ghastTear,
                'B', Item.book,
                'E', Item.enderPearl);
        // 开始注入商品价格配置文件
        Configs.beginToLoadShopConfig();
    }
}
