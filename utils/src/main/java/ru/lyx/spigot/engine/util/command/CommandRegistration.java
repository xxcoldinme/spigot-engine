package ru.lyx.spigot.engine.util.command;

import lombok.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "newBuilder", builderClassName = "build")
public class CommandRegistration {

    private final CommandExecutor executor;
    private final TabCompleter tabCompleter;

    private final String permission;
    private final String description;
    private final String usage;

    private final String permissionMessage;

    private final String label;
    private final String name;

    private final List<String> aliases;

    public Command createBukkitCommand() {
        SampleSpigotCommand command = new SampleSpigotCommand(executor, tabCompleter);

        command.setPermission(permission);
        command.setDescription(description);
        command.setUsage(usage);

        command.setPermissionMessage(permissionMessage);

        command.setLabel(label);
        command.setName(name);

        command.setLabel(label);

        return command;
    }
}
