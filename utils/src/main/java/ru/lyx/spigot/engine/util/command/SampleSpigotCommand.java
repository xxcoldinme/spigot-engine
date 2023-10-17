package ru.lyx.spigot.engine.util.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class SampleSpigotCommand extends Command {

    private final CommandExecutor executor;
    private final TabCompleter tabCompleter;

    protected SampleSpigotCommand(CommandExecutor executor, TabCompleter tabCompleter) {
        super(null);

        this.executor = executor;
        this.tabCompleter = tabCompleter;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return executor.onCommand(commandSender, this, s, strings);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return tabCompleter.onTabComplete(sender, this, alias, args);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
        return tabCompleter.onTabComplete(sender, this, alias, args);
    }
}
