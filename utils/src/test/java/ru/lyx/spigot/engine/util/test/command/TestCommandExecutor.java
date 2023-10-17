package ru.lyx.spigot.engine.util.test.command;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import ru.lyx.spigot.engine.util.command.CommandRegistration;
import ru.lyx.spigot.engine.util.command.CommandUtil;

import java.util.List;

public class TestCommandExecutor implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(ChatColor.GREEN + "Sample green message");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Lists.newArrayList();
    }

    public static void main(String[] args) {
        TestCommandExecutor executor = new TestCommandExecutor();

        CommandUtil.register(
                CommandRegistration.newBuilder()
                        .name("test")
                        .permission("test.use")
                        .executor(executor)
                        .tabCompleter(executor)
                        .build());
    }
}
