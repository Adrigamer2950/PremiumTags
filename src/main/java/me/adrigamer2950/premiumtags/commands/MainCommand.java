package me.adrigamer2950.premiumtags.commands;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.premiumtags.commands.other.ReloadSubCommand;
import me.adrigamer2950.premiumtags.commands.player.AddSubCommand;
import me.adrigamer2950.premiumtags.commands.player.RemoveSubCommand;
import me.adrigamer2950.premiumtags.commands.tags.ListSubCommand;
import me.adrigamer2950.premiumtags.commands.tags.TagsMainCommand;
import me.adrigamer2950.premiumtags.util.HelpMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class MainCommand extends Command {

    public MainCommand(@NotNull Plugin pl, @NotNull String name, @Nullable List<String> aliases) {
        super(pl, name, aliases);

        // Player commands (add tag, remove tag...)
        addSubCommand(new AddSubCommand(this, "add"));
        addSubCommand(new RemoveSubCommand(this, "remove"));

        // List existing tags or tags used by specified player
        addSubCommand(new ListSubCommand(this, "list"));

        // Tag Category
        addSubCommand(new TagsMainCommand(this, "tag"));

        // Miscellaneous
        addSubCommand(new ReloadSubCommand(this, "reload"));

        setHelpSubCommand(new HelpSubCommand(this, "help"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return parseSubCommands(sender, label, args);
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            return parseSubCommandsTabCompleter(sender, label, args).stream().filter(str -> str.startsWith(args[0])).collect(Collectors.toList());
        }

        return parseSubCommandsTabCompleter(sender, label, args);
    }

    private static class HelpSubCommand extends SubCommand {

        public HelpSubCommand(Command parent, String name) {
            super(parent, name);
        }

        @Override
        public boolean execute(CommandSender commandSender, String s, String[] strings) {
            HelpMessages.sendHelpMessage(commandSender, s, List.of(
                    "&f&l| &6/%s add <player> <tag>",
                    "&f&l| &6/%s remove <player> <tag>",
                    "&f&l| &6/%s tag",
                    "&f&l| &6/%s list [player]",
                    "&f&l| &6/%s reload"
            ));

            return true;
        }

        @Override
        public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
            return null;
        }
    }

}
