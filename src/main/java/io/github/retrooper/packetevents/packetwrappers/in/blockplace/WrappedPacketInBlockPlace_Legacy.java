package io.github.retrooper.packetevents.packetwrappers.in.blockplace;

import io.github.retrooper.packetevents.enums.Hand;
import io.github.retrooper.packetevents.enums.ServerVersion;
import io.github.retrooper.packetevents.packetwrappers.api.WrappedPacket;
import io.github.retrooper.packetevents.tinyprotocol.Reflection;
import io.github.retrooper.packetevents.utils.NMSUtils;
import io.github.retrooper.packetevents.utils.vector.Vector3i;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class WrappedPacketInBlockPlace_Legacy extends WrappedPacket {
    private Vector3i blockPosition;
    private ItemStack itemStack;
    private Hand hand;

    WrappedPacketInBlockPlace_Legacy(final Object packet) {
        super(packet);
    }

    @Override
    protected void setup() {
        try {
            final Hand hand = Hand.MAIN_HAND;
            final ItemStack itemStack;
            final int x, y, z;
            if (version.isHigherThan(ServerVersion.v_1_7_10)) {
                final Object nmsBlockPosObj = fields[0].get(packet);

                x = blockPosXYZ[0].get(nmsBlockPosObj);
                y = blockPosXYZ[1].get(nmsBlockPosObj);
                z = blockPosXYZ[2].get(nmsBlockPosObj);

                Object nmsItemStackObj = fields[1].get(packet);

                Object craftItemStack = asBukkitCopyMethod.invoke(null, nmsItemStackObj);

                itemStack = (ItemStack) craftItemStack;
            }
            //1.7.10
            else {
                x = fields_1_7[0].getInt(packet);
                y = fields_1_7[1].getInt(packet);
                z = fields_1_7[2].getInt(packet);

                Object nmsItemStackObj = fields_1_7[3].get(packet);

                Object craftItemStack = asBukkitCopyMethod.invoke(null, nmsItemStackObj);

                itemStack = (ItemStack) craftItemStack;
            }

            this.hand = hand;
            this.itemStack = itemStack;
            this.blockPosition = new Vector3i(x, y, z);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public Hand getHand() {
        return hand;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Vector3i getBlockPosition() {
        return blockPosition;
    }


    private static Class<?> blockPlaceClass;
    private static Class<?> itemStackClass;
    private static Class<?> craftItemStackClass;

    private static Class<?> blockPositionClass;

    private static Field[] fields = new Field[2];

    private static Field[] fields_1_7 = new Field[4];

    private static Reflection.FieldAccessor<Integer>[] blockPosXYZ = new Reflection.FieldAccessor[3];

    private static Method asBukkitCopyMethod;


    protected static void setupStaticVars() {
        try {
            blockPlaceClass = NMSUtils.getNMSClass("PacketPlayInBlockPlace");
            itemStackClass = NMSUtils.getNMSClass("ItemStack");
            craftItemStackClass = NMSUtils.getOBCClass("inventory.CraftItemStack");
            if (version.isHigherThan(ServerVersion.v_1_7_10)) {
                blockPositionClass = NMSUtils.getNMSClass("BlockPosition");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            asBukkitCopyMethod = craftItemStackClass.getMethod("asBukkitCopy", itemStackClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            if (version.isLowerThan(ServerVersion.v_1_8)) {
                //x
                fields_1_7[0] = blockPlaceClass.getDeclaredField("a");
                //y
                fields_1_7[1] = blockPlaceClass.getDeclaredField("b");
                //z
                fields_1_7[2] = blockPlaceClass.getDeclaredField("c");
                //itemstack
                fields_1_7[3] = blockPlaceClass.getDeclaredField("e");
            } else {
                //block pos
                fields[0] = blockPlaceClass.getDeclaredField("b");
                //itemstack
                fields[1] = blockPlaceClass.getDeclaredField("d");


                blockPosXYZ[0] = Reflection.getField(blockPlaceClass.getSuperclass(), int.class, 0);
                blockPosXYZ[1] = Reflection.getField(blockPlaceClass.getSuperclass(), int.class, 1);
                blockPosXYZ[2] = Reflection.getField(blockPlaceClass.getSuperclass(), int.class, 2);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        for (Field f : fields) {
            if (f != null) {
                f.setAccessible(true);
            }
        }

        for (Field f : fields_1_7) {
            if (f != null) {
                f.setAccessible(true);
            }
        }

    }
}
