package me.adrigamer2950.premiumtags.commands;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HelpSubCommand extends SubCommand {

    public HelpSubCommand(Command parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        List<String> list = List.of(
                "&7-------------------[&b&lPremium&a&lTags&7]-------------------",
                "&f&l| &6/tags help",
                "&f&l| &6/tags add <player> <tag>",
                "&f&l| &6/tags remove <player> <tag>",
                "&f&l| &6/tags list <player>",
                "&f&l| &6/tags create <id> <priority> <tag>",
                "&f&l| &6/tags delete <id>",
                "&7---------------------------------------------------"
        );

        for (String str : list) {
            commandSender.sendMessage(Colors.translateColors(str));
        }

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
