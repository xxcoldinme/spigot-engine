package ru.lyx.spigot.engine.util.math;

import lombok.*;
import org.bukkit.event.inventory.InventoryType;
import ru.lyx.spigot.engine.util.SpigotUtilException;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GuiSlot {

    private static final int DEFAULT_ROW_SIZE = 9;

    private static int getRowSize(InventoryType inventoryType) {
        switch (inventoryType) {
            case HOPPER: {
                return 1;
            }
            case ANVIL:
            case BEACON:
            case FURNACE:
            case ENCHANTING:
            case MERCHANT: {
                return 0;
            }
            case DISPENSER:
            case DROPPER:
            case CRAFTING: {
                return 3;
            }
        }
        return DEFAULT_ROW_SIZE;
    }

    public static GuiSlot first() {
        return new GuiSlot(0, DEFAULT_ROW_SIZE);
    }

    public static GuiSlot first(InventoryType inventoryType) {
        return new GuiSlot(0, getRowSize(inventoryType));
    }

    public static GuiSlot of(int slot) {
        return new GuiSlot(slot, DEFAULT_ROW_SIZE);
    }

    public static GuiSlot of(int slot, InventoryType inventoryType) {
        return new GuiSlot(slot, getRowSize(inventoryType));
    }

    public static GuiSlot asMatrix(int x, int y) {
        return new GuiSlot((y - 1) * DEFAULT_ROW_SIZE + (x - 1), DEFAULT_ROW_SIZE);
    }

    public static GuiSlot asMatrix(int x, int y, InventoryType inventoryType) {
        int rowSize = getRowSize(inventoryType);
        return new GuiSlot((y - 1) * rowSize + (x - 1), rowSize);
    }

    @Getter
    private int slot;
    private int rowSize;

    public GuiSlot up(int mod) {
        return left(mod * rowSize);
    }

    public GuiSlot up() {
        return up(1);
    }

    public GuiSlot upToEdge() {
        return up(slot / rowSize);
    }

    public GuiSlot down(int mod) {
        return right(mod * rowSize);
    }

    public GuiSlot down() {
        return down(1);
    }

    public GuiSlot left(int mod) {
        int left = (slot - mod);
        if (left <= 0) {
            throw new SpigotUtilException("Slot cannot to be left move at " + mod + " times");
        }

        return new GuiSlot(left, rowSize);
    }

    public GuiSlot left() {
        return left(1);
    }

    public GuiSlot leftToEdge() {
        return left(slot % rowSize);
    }

    public GuiSlot right(int mod) {
        int right = (slot + mod);
        if (right <= 0) {
            throw new SpigotUtilException("Slot cannot to be right move at " + mod + " times");
        }

        return new GuiSlot(right, rowSize);
    }

    public GuiSlot right() {
        return right(1);
    }

    public GuiSlot rightToEdge() {
        return right((((slot - (slot % rowSize)) + rowSize) - slot) - 1);
    }
}
