package me.adrigamer2950.premiumtags.commands.tags;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.adriapi.api.user.User;
import me.adrigamer2950.premiumtags.PTPlugin;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.adrigamer2950.premiumtags.util.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddTagCommand extends SubCommand<PTPlugin> {

    public AddTagCommand(Command<PTPlugin> parent, String name) {
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

        if (getPlugin().tags.stream().anyMatch(t -> Objects.equals(t.getId(), id))) {
            user.sendMessage("&cThat tag already exists!");
            return true;
        }

        if (args.length == 1) {
            user.sendMessage("&cYou need to specify a tag!");
            return true;
        }

        String tag = args[1];

        getPlugin().tags.add(new Tag(id, tag));
        user.sendMessage("&aTag added successfully");

        return true;
    }
}
