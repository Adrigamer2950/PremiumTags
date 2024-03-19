package me.adrigamer2950.premiumtags.commands.tags;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.premiumtags.util.HelpMessages;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TagsMainCommand extends SubCommand {

    public TagsMainCommand(Command parent, String name) {
        super(parent, name);

        setHelpSubCommand(new TagsHelpCommand(this, "help"));

        addSubCommand(new CreateTagSubCommand(this, "create"));
        addSubCommand(new DeleteTagSubCommand(this, "delete"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return parseSubCommands(commandSender, s, strings);
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    private static class TagsHelpCommand extends SubCommand {

        public TagsHelpCommand(Command parent, String name) {
            super(parent, name);
        }

        @Override
        public boolean execute(CommandSender commandSender, String s, String[] strings) {
            HelpMessages.sendHelpMessage(commandSender, s, List.of(
                    "&f&l| &6/%s tag create <id> <priority> <tag>",
                    "&f&l| &6/%s tag delete <id>"
            ));

            return true;
        }

        @Override
        public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
            return null;
        }
    }
}
