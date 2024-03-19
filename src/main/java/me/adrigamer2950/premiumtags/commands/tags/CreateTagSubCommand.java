package me.adrigamer2950.premiumtags.commands.tags;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.Tag;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CreateTagSubCommand extends SubCommand {

    public CreateTagSubCommand(Command parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        args = Arrays.copyOfRange(args, 2, args.length);

        if (args.length == 0) {
            sender.sendMessage(Colors.translateColors("&cYou need to specify an id"));

            return true;
        }

        String id = args[0];

        if (args.length == 1) {
            sender.sendMessage(Colors.translateColors("&cYou need to specify a priority"));

            return true;
        }

        int priority;
        try {
            priority = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Colors.translateColors("&cPriority is not valid, you need to use a number"));

            return true;
        }


        if (args.length == 2) {
            sender.sendMessage(Colors.translateColors("&cYou need to specify a tag"));

            return true;
        }

        if (((PremiumTags) getPlugin()).tagsManager.getTag(id) != null) {
            sender.sendMessage(Colors.translateColors("&cTag already exists"));

            return true;
        }

        String tag = String.join(" ", Arrays.copyOfRange(args, 2, args.length)).replaceAll("\"", "");

        ((PremiumTags) getPlugin()).tagsManager.registerTag(new Tag(id, tag, "", priority), true);

        sender.sendMessage(Colors.translateColors("&aTag created successfully"));

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
