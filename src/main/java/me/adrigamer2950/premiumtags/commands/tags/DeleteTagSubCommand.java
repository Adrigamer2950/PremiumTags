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

public class DeleteTagSubCommand extends SubCommand {

    public DeleteTagSubCommand(Command parent, String name) {
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

        Tag t = ((PremiumTags) getPlugin()).tagsManager.getTag(id);

        if (t == null) {
            sender.sendMessage(Colors.translateColors("&cTag not found"));

            return true;
        }

        ((PremiumTags) getPlugin()).tagsManager.unRegisterTag(t);

        ((PremiumTags) getPlugin()).playersUsingTags.forEach((uuid, tags) -> {
            if (tags.stream().map(Tag::getId).toList().contains(t.getId())) tags.remove(t);

            ((PremiumTags) getPlugin()).playersUsingTags.put(uuid, tags);
        });

        sender.sendMessage(Colors.translateColors("&cTag deleted successfully"));

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
