package me.adrigamer2950.premiumtags.commands;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.adriapi.api.user.User;
import me.adrigamer2950.premiumtags.PTPlugin;
import me.adrigamer2950.premiumtags.commands.tags.AddTagCommand;
import me.adrigamer2950.premiumtags.commands.tags.ListTagsCommand;
import me.adrigamer2950.premiumtags.commands.tags.RemoveTagCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainCommand extends Command<PTPlugin> {

    public MainCommand(@NotNull PTPlugin pl, @NotNull String name) {
        super(pl, name);

        // Manage tags
        addSubCommand(new ListTagsCommand(this, "list"));
        addSubCommand(new AddTagCommand(this, "add"));
        addSubCommand(new RemoveTagCommand(this, "remove"));
    }

    @Override
    public boolean execute(User user, String label, String[] args) {
        return parseSubCommands(user, label, args);
    }

    @Override
    public List<String> tabComplete(@NotNull User user, @NotNull String label, @NotNull String[] args) {
        if (args.length > 1 && !args[0].isEmpty())
            for (SubCommand<PTPlugin> cmd : this.getSubCommands())
                if (cmd.getName().equals(args[0]))
                    return cmd.tabComplete(user, label, args);

        if (args.length < 2) {
            return this.getSubCommands().stream().map(Command::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        }

        return null;
    }
}
