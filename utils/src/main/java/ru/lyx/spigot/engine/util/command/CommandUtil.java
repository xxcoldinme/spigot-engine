package ru.lyx.spigot.engine.util.command;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import ru.lyx.spigot.engine.util.SpigotUtilException;

import java.lang.reflect.Field;

@UtilityClass
public class CommandUtil {

    private static final CommandMap COMMAND_MAP;
    static {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

            Class<?> craftServerClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
            Object craftServerObject = craftServerClass.cast(Bukkit.getServer());
            Field commandMapField = craftServerClass.getDeclaredField("commandMap");

            commandMapField.setAccessible(true);

            COMMAND_MAP = (SimpleCommandMap) commandMapField.get(craftServerObject);
        }
        catch (Exception exception) {
            throw new SpigotUtilException(exception);
        }
    }

    public void register(CommandRegistration commandRegistration) {
        final Command bukkitCommand = commandRegistration.createBukkitCommand();
        COMMAND_MAP.register(commandRegistration.getName(), bukkitCommand);
    }
}
