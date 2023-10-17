package ru.lyx.spigot.engine.util.recipe;

import lombok.*;
import org.bukkit.inventory.ItemStack;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class WorkbenchRecipe implements Recipe {

    @Getter
    private final ItemStack result;

    @Getter
    @Setter
    private ItemStack[] matrix = new ItemStack[9];

    @Override
    public void insert(int x, int y, ItemStack itemStack) {
        matrix[(y - 1) * 9 + (x - 1)] = itemStack;
    }

    @Override
    public void insert(int slot, ItemStack itemStack) {
        matrix[slot] = itemStack;
    }

    @Override
    public ItemStack[] toMatrix() {
        return matrix;
    }

    @Override
    public boolean contains(ItemStack itemStack) {
        for (ItemStack other : matrix) {
            if (other == null) {
                continue;
            }

            if (other.isSimilar(itemStack)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean contains(int slot, ItemStack itemStack) {
        return matrix[slot - 1] != null && matrix[slot - 1].isSimilar(itemStack);
    }

    @Override
    public boolean contains(int x, int y, ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isEmpty(int slot) {
        return false;
    }

    @Override
    public boolean isEmpty(int x, int y) {
        return false;
    }

}
