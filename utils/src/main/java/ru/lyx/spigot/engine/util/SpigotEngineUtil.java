package ru.lyx.spigot.engine.util;

import lombok.experimental.UtilityClass;
import ru.lyx.spigot.engine.util.recipe.SpigotRecipeUtil;

@UtilityClass
public class SpigotEngineUtil {

    public void registerUtils() {
        SpigotRecipeUtil.registerMinecraftRecipes();
    }
}
