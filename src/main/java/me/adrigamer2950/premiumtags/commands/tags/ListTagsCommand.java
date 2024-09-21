package me.adrigamer2950.premiumtags.commands.tags;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.adriapi.api.user.User;
import me.adrigamer2950.premiumtags.PTPlugin;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.adrigamer2950.premiumtags.util.Permissions;

import java.util.Set;

public class ListTagsCommand extends SubCommand<PTPlugin> {

    public ListTagsCommand(Command<PTPlugin> parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(User user, String label, String[] args) {
        if (!user.hasPermission(Permissions.MANAGE_TAGS)) {
            user.sendMessage("&cYou don't have permissions!");
            return true;
        }

        user.sendMessage("&bTag list:");

        Set<Tag> tags = getPlugin().tags;

        for (Tag tag : tags) {
            user.sendMessage("&7%s &f- &7%s".formatted(tag.getId(), tag.getTag()));
        }

        return true;
    }
}
