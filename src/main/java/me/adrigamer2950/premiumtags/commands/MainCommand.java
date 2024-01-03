package me.adrigamer2950.premiumtags.commands;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.premiumtags.commands.tags.SetSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainCommand extends Command {

    public MainCommand(@NotNull Plugin pl, @NotNull String name) {
        super(pl, name);

        addSubCommand(new SetSubCommand(this, "set"));

        setHelpSubCommand(new HelpSubCommand(this, "help"));
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
