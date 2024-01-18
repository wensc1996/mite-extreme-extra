package net.xiaoyu233.mitemod.miteite.util;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;

import java.util.Random;

public class Constant {
    public static final double[] ENHANCE_FACTORS;
    public static final bjo icons_ite = new bjo("textures/gui/icons_ite.png");
    public static final String MITE_ITE_VERSION = "v0.0.3";
    public static final int MITE_ITE_VER_NUM = 3;
    public static final bjo RES_VIBRANIUM_SINGLE = new bjo("textures/entity/chest/vibranium_single.png");
    public static int nextItemID = 2024;
    public static int nextBlockID = 160;
    public static int nextEnchantmentID = 97;
    public static int nextAchievementID = 136;
    public static ItemArmor[] HELMETS = null;
    public static ItemArmor[] CHESTPLATES = null;
    public static ItemArmor[] LEGGINGS = null;
    public static ItemArmor[] BOOTS = null;
    public static Item[][] SWORDS = null;
    public static ItemArmor[][] ARMORS = null;
    public static BlockBed [] bedBlockTypes= null;
    public static Random GARandom = new Random();

    static {
        ENHANCE_FACTORS = new double[Short.MAX_VALUE];
        for (int i = 0, enhance_factorsLength = ENHANCE_FACTORS.length; i < enhance_factorsLength; i++) {
            ENHANCE_FACTORS[i] = Math.pow(1.028, i*2.28) - 1;
        }
    }

    public static int getNextItemID() {
        return Constant.nextItemID++;
    }

    public static int getFoliageColorMaple()
    {
        // 纯白色
        return 16777215;
    }

    public static int getFoliageColorCherry()
    {
        // 纯白色
        return 16777215;
    }

    public static ItemStack getBlockComponentWithNewWood(int metadata) {
        Material tool_material = BlockWorkbench.getToolMaterial(metadata);
        if (tool_material == Material.flint) {
            return new ItemStack(Blocks.wood1, 1, metadata);
        } else {
            return tool_material == Material.obsidian ? new ItemStack(Blocks.wood1, 1, metadata - 11) : null;
        }
    }

    public static void initItemArray() {
        HELMETS = new ItemArmor[]{Item.helmetLeather, Item.helmetChainCopper, Item.helmetCopper, Item.helmetRustedIron, Item.helmetChainIron, Item.helmetIron, Item.helmetChainAncientMetal, Item.helmetAncientMetal, Item.helmetChainMithril, Item.helmetMithril, Item.helmetChainAdamantium, Item.helmetAdamantium, Items.VIBRANIUM_HELMET};
        CHESTPLATES = new ItemArmor[]{Item.plateLeather, Item.plateChainCopper, Item.plateCopper, Item.plateRustedIron, Item.plateChainIron, Item.plateIron, Item.plateChainAncientMetal, Item.plateAncientMetal, Item.plateChainMithril, Item.plateMithril, Item.plateChainAdamantium, Item.plateAdamantium, Items.VIBRANIUM_CHESTPLATE};
        LEGGINGS = new ItemArmor[]{Item.legsLeather, Item.legsChainCopper, Item.legsCopper, Item.legsRustedIron, Item.legsChainIron, Item.legsIron, Item.legsChainAncientMetal, Item.legsAncientMetal, Item.legsChainMithril, Item.legsMithril, Item.legsChainAdamantium, Item.legsAdamantium, Items.VIBRANIUM_LEGGINGS};
        BOOTS = new ItemArmor[]{Item.bootsLeather, Item.bootsChainCopper, Item.bootsCopper, Item.bootsRustedIron, Item.bootsChainIron, Item.bootsIron, Item.bootsChainAncientMetal, Item.bootsAncientMetal, Item.bootsChainMithril, Item.bootsMithril, Item.bootsChainAdamantium, Item.bootsAdamantium, Items.VIBRANIUM_BOOTS};
        ARMORS = new ItemArmor[][]{HELMETS, CHESTPLATES, LEGGINGS, BOOTS};
        SWORDS = new Item[][]{
                {Items.clubWood, Items.cudgelWood, Items.shovelWood},
                {Items.swordCopper, Items.swordSilver, Items.swordGold, Item.pickaxeCopper, Item.pickaxeSilver, Item.pickaxeGold, Item.shovelCopper, Item.shovelSilver, Item.shovelGold,
                        Item.axeCopper, Item.axeSilver, Item.axeGold,Item.hoeCopper, Item.hoeSilver, Item.hoeGold, Item.mattockCopper, Item.mattockSilver, Item.mattockGold, Item.battleAxeCopper,
                        Item.battleAxeSilver, Item.battleAxeGold, Item.warHammerCopper, Item.warHammerSilver, Item.warHammerGold, Item.scytheCopper, Item.scytheSilver, Item.scytheGold,
                        Item.hatchetCopper, Item.hatchetSilver, Item.hatchetGold, Item.shearsCopper, Item.shearsSilver, Item.shearsGold},
                {Item.swordRustedIron, Item.swordIron, Items.clubIron, Item.pickaxeIron, Item.pickaxeRustedIron, Item.shovelIron, Item.shovelRustedIron, Item.axeIron,
                        Item.axeRustedIron, Item.hoeIron, Item.hoeRustedIron, Item.mattockIron, Item.mattockRustedIron, Item.battleAxeIron, Item.battleAxeRustedIron,
                        Item.warHammerIron, Item.warHammerRustedIron, Item.scytheIron, Item.scytheRustedIron, Item.hatchetIron, Item.hatchetRustedIron},
                {Item.swordAncientMetal, Item.pickaxeAncientMetal, Item.shearsAncientMetal, Item.axeAncientMetal, Item.hoeAncientMetal, Item.mattockAncientMetal, Item.battleAxeAncientMetal,
                        Item.warHammerAncientMetal, Item.scytheAncientMetal, Item.hatchetAncientMetal, Item.hatchetAncientMetal},
                {Item.swordMithril, Items.clubMithril, Item.pickaxeMithril, Item.shovelMithril, Item.axeMithril, Item.hoeMithril, Item.mattockMithril,
                        Item.battleAxeMithril, Item.warHammerMithril, Item.scytheMithril, Item.hatchetMithril, Item.hatchetMithril},
                {Item.swordAdamantium, Items.clubAdamantium, Item.pickaxeAdamantium, Item.shovelAdamantium, Item.axeAdamantium, Item.hoeAdamantium,
                        Item.mattockAdamantium, Item.battleAxeAdamantium, Item.scytheAdamantium, Item.hatchetAdamantium},
                {Items.VIBRANIUM_SWORD, Items.clubVibranium, Items.VIBRANIUM_PICKAXE, Items.VIBRANIUM_SHOVEL, Items.VIBRANIUM_AXE, Items.VIBRANIUM_BATTLE_AXE}};
        bedBlockTypes = new BlockBed[] {Blocks.blackBed,
                Blocks.redBed,Blocks.greenBed,Blocks.brownBed,Blocks.blueBed,Blocks.purpleBed,Blocks.cyanBed,Blocks.silverBed,Blocks.grayBed,Blocks.pinkBed,Blocks.limeBed,Blocks.yellowBed
                ,Blocks.lightBlueBed,Blocks.magentaBed,Blocks.orangeBed, Blocks.bed};
    }
}
