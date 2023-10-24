package me.adrigamer2950.adritags.commands;

import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainCommand extends Command {

    public MainCommand(@NotNull Plugin pl, @NotNull String name, @NotNull List<String> aliases) {
        super(pl, name, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return parseSubCommands(sender, label, args);
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        return parseSubCommandsTabCompleter(sender, label, args);
    }
}
