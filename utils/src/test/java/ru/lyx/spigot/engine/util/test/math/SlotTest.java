package ru.lyx.spigot.engine.util.test.math;

import ru.lyx.spigot.engine.util.math.Slot;

public class SlotTest {

    /*
            GUI SLOTS MATRIX:
            ----------------

             0  1  2  3  4  5  6  7  8
             9 10 11 12 13 14 15 16 17
            18 19 20 21 22 23 24 25 26
            27 28 29 30 31 32 33 34 35
     */

    public static void main(String[] args) {
        System.out.println(Slot.asMatrix(1, 1)); // GuiSlot(slot=0, rowSize=9)
        System.out.println(Slot.asMatrix(2, 3)); // GuiSlot(slot=19, rowSize=9)

        final Slot slot_23 = Slot.of(23);

        System.out.println(slot_23.up()); // GuiSlot(slot=14, rowSize=9)
        System.out.println(slot_23.up(2)); // GuiSlot(slot=5, rowSize=9)
        System.out.println(slot_23.upToEdge()); // GuiSlot(slot=5, rowSize=9)

        System.out.println(slot_23.down()); // GuiSlot(slot=32, rowSize=9)
        System.out.println(slot_23.down(2)); // GuiSlot(slot=41, rowSize=9)

        System.out.println(slot_23.left()); // GuiSlot(slot=22, rowSize=9)
        System.out.println(slot_23.left(3)); // GuiSlot(slot=20, rowSize=9)
        System.out.println(slot_23.leftToEdge()); // GuiSlot(slot=18, rowSize=9)

        System.out.println(slot_23.right()); // GuiSlot(slot=24, rowSize=9)
        System.out.println(slot_23.right(2)); // GuiSlot(slot=25, rowSize=9)
        System.out.println(slot_23.rightToEdge()); // GuiSlot(slot=26, rowSize=9)
    }
}
