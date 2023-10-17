package ru.lyx.spigot.engine.util.math;

import lombok.*;
import org.bukkit.event.inventory.InventoryType;
import ru.lyx.spigot.engine.util.SpigotUtilException;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Slot {

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

    public static Slot first() {
        return new Slot(0, DEFAULT_ROW_SIZE);
    }

    public static Slot first(InventoryType inventoryType) {
        return new Slot(0, getRowSize(inventoryType));
    }

    public static Slot of(int slot) {
        return new Slot(slot, DEFAULT_ROW_SIZE);
    }

    public static Slot of(int slot, InventoryType inventoryType) {
        return new Slot(slot, getRowSize(inventoryType));
    }

    public static Slot asMatrix(int x, int y) {
        return new Slot((y - 1) * DEFAULT_ROW_SIZE + (x - 1), DEFAULT_ROW_SIZE);
    }

    public static Slot asMatrix(int x, int y, InventoryType inventoryType) {
        int rowSize = getRowSize(inventoryType);
        return new Slot((y - 1) * rowSize + (x - 1), rowSize);
    }

    @Getter
    private int slot;
    private int rowSize;

    public Slot up(int mod) {
        return left(mod * rowSize);
    }

    public Slot up() {
        return up(1);
    }

    public Slot upToEdge() {
        return up(slot / rowSize);
    }

    public Slot down(int mod) {
        return right(mod * rowSize);
    }

    public Slot down() {
        return down(1);
    }

    public Slot left(int mod) {
        int left = (slot - mod);
        if (left <= 0) {
            throw new SpigotUtilException("Slot cannot to be left move at " + mod + " times");
        }

        return new Slot(left, rowSize);
    }

    public Slot left() {
        return left(1);
    }

    public Slot leftToEdge() {
        return left(slot % rowSize);
    }

    public Slot right(int mod) {
        int right = (slot + mod);
        if (right <= 0) {
            throw new SpigotUtilException("Slot cannot to be right move at " + mod + " times");
        }

        return new Slot(right, rowSize);
    }

    public Slot right() {
        return right(1);
    }

    public Slot rightToEdge() {
        return right((((slot - (slot % rowSize)) + rowSize) - slot) - 1);
    }
}
