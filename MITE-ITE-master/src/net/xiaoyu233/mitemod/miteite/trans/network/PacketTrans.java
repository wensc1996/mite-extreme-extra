package net.xiaoyu233.mitemod.miteite.trans.network;

import net.minecraft.EnumQuality;
import net.minecraft.ItemStack;
import net.minecraft.NBTTagCompound;
import net.minecraft.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Mixin(Packet.class)
public abstract class PacketTrans{

    @Shadow
    public static NBTTagCompound readNBTTagCompound(DataInput par0DataInput) throws IOException{
        return null;
    };

    @Shadow
    private static int readItemStackDamage(ItemStack item_stack, DataInput dis) throws IOException {
        return -1;
    }

    @Shadow
    protected static void writeNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutput par1DataOutput) throws IOException{}

    @Shadow
    private static void writeItemStackDamage(ItemStack item_stack, DataOutput dos) throws IOException {}

    @Shadow
    private static int getPacketSizeOfItemStackDamage(int max_damage) {
        return -1;
    }

    @Overwrite
    public static ItemStack readItemStack(DataInput par0DataInput) throws IOException {
        ItemStack var1 = null;
        short var2 = par0DataInput.readShort();
        if (var2 >= 0) {
            byte var3 = par0DataInput.readByte();
            short var4 = par0DataInput.readShort();
            var1 = new ItemStack(var2, var3, var4);
            int quality_ordinal = par0DataInput.readByte();
            if (quality_ordinal >= 0) {
                var1.setQuality(EnumQuality.values()[quality_ordinal]);
            }

            var1.stackTagCompound = readNBTTagCompound(par0DataInput);
            if (var1.isItemStackDamageable()) {
                var1.setItemDamage(readItemStackDamage(var1, par0DataInput));
            }
            int gemLength = par0DataInput.readByte();
            for(int i = 0; i < gemLength; i++) {
                int index = par0DataInput.readByte();
                int id = par0DataInput.readShort();
                int meta = par0DataInput.readByte();
                if(id > 0 && meta > 0) {
                    var1.GemsList[index] = new ItemStack(id, 1, meta);
                } else {
                    var1.GemsList[index] = null;
                }
            }

        }

        return var1;
    }

    @Overwrite
    public static void writeItemStack(ItemStack par0ItemStack, DataOutput par1DataOutput) throws IOException {
        if (par0ItemStack == null) {
            par1DataOutput.writeShort(-1);
        } else {
            par1DataOutput.writeShort(par0ItemStack.itemID);
            par1DataOutput.writeByte(par0ItemStack.stackSize);
            par1DataOutput.writeShort(par0ItemStack.getItemSubtype());
            par1DataOutput.writeByte(par0ItemStack.getQuality() == null ? -1 : par0ItemStack.getQuality().ordinal());
            NBTTagCompound var2 = null;
            if (par0ItemStack.getItem().isDamageable() || par0ItemStack.getItem().getShareTag()) {
                var2 = par0ItemStack.stackTagCompound;
            }

            writeNBTTagCompound(var2, par1DataOutput);
            if (par0ItemStack.isItemStackDamageable()) {
                writeItemStackDamage(par0ItemStack, par1DataOutput);
            }

            par1DataOutput.writeByte(par0ItemStack.GemsList.length);
            for(int i = 0; i < par0ItemStack.GemsList.length; i++) {
                if(par0ItemStack.GemsList[i] != null) {
                    System.out.println("index:" + i+ " id:" + par0ItemStack.itemID +" meta:" +par0ItemStack.getItemSubtype());
                    par1DataOutput.writeByte(i);
                    par1DataOutput.writeShort(par0ItemStack.itemID);
                    par1DataOutput.writeByte(par0ItemStack.getItemSubtype());
                } else {
                    par1DataOutput.writeByte(i);
                    par1DataOutput.writeShort(-1);
                    par1DataOutput.writeByte(-1);
                }
            }
        }

    }

    @Overwrite
    public static int getPacketSizeOfItemStack(ItemStack item_stack) {
        int length = item_stack == null ? 2 : (!item_stack.isItemStackDamageable() ? 6 : 6 + getPacketSizeOfItemStackDamage(item_stack.getMaxDamage())) + item_stack.GemsList.length * 4 + 1;
        return length;
    }
}
