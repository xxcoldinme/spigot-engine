package ru.lyx.spigot.engine.util.recipe;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public final class SpigotRecipeUtil {

    private final Set<WorkbenchRecipe> workbenchRecipeSet = new HashSet<>();

    public void registerMinecraftRecipes() {
        Bukkit.recipeIterator().forEachRemaining(recipe -> {

            if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                workbenchRecipeSet.add(wrap(shapedRecipe));
            }
        });
    }

    public void registerCustomRecipe(WorkbenchRecipe recipe) {
        boolean add = workbenchRecipeSet.add(recipe);
        Preconditions.checkArgument(add, "Recipe already exists!");
    }

    private WorkbenchRecipe wrap(ShapedRecipe shapedRecipe) {
        final WorkbenchRecipe workbenchRecipe = new WorkbenchRecipe(shapedRecipe.getResult());
        final String[] shapeMatrix = shapedRecipe.getShape();

        shapedRecipe.getIngredientMap().forEach((character, itemStack) -> {

            int slot = 1;
            for (String matrixColumn : shapeMatrix) {

                for (String sign : matrixColumn.split("")) {

                    if (sign.equals(Character.toString(character))) {
                        workbenchRecipe.insert(slot, itemStack);
                    }

                    slot++;
                }
            }
        });

        return workbenchRecipe;
    }
}
