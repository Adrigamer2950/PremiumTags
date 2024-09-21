package me.adrigamer2950.premiumtags.commands.tags;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.adriapi.api.user.User;
import me.adrigamer2950.premiumtags.PTPlugin;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.adrigamer2950.premiumtags.util.Permissions;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class RemoveTagCommand extends SubCommand<PTPlugin> {

    public RemoveTagCommand(Command<PTPlugin> parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(User user, String label, String[] args) {
        if (!user.hasPermission(Permissions.MANAGE_TAGS)) {
            user.sendMessage("&cYou don't have permissions!");
            return true;
        }

        List<String> _args = new ArrayList<>(Arrays.asList(args));
        _args.remove(0);
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        args = _args.toArray(new String[_args.size()]);

        if (args.length == 0) {
            user.sendMessage("&cYou need to specify an ID!");
            return true;
        }

        String id = args[0];

        Optional<Tag> tag = getPlugin().tags.stream().filter(t -> Objects.equals(t.getId(), id)).findAny();

        if (tag.isEmpty()) {
            user.sendMessage("&cThat tag doesn't exist!");
            return true;
        }

        getPlugin().tags.remove(tag.get());
        user.sendMessage("&cTag removed successfully");

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull User user, @NotNull String label, @NotNull String[] args) {
        List<String> _args = new ArrayList<>(Arrays.asList(args));
        _args.remove(0);
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        args = _args.toArray(new String[_args.size()]);

        if (args.length < 2) {
            return getPlugin().tags.stream().map(Tag::getId).collect(Collectors.toList());
        }

        return List.of();
    }
}
