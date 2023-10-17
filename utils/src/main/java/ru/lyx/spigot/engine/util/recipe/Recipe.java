package ru.lyx.spigot.engine.util.recipe;

import org.bukkit.inventory.ItemStack;

public interface Recipe {

    ItemStack[] toMatrix();

    boolean contains(ItemStack itemStack);

    boolean contains(int slot, ItemStack itemStack);

    boolean contains(int x, int y, ItemStack itemStack);

    boolean isEmpty(int slot);

    boolean isEmpty(int x, int y);

    void insert(int x, int y, ItemStack itemStack);

    void insert(int slot, ItemStack itemStack);
}
