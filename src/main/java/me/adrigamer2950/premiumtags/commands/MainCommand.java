package me.adrigamer2950.premiumtags.commands;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.user.User;
import me.adrigamer2950.premiumtags.PTPlugin;
import org.jetbrains.annotations.NotNull;

public class MainCommand extends Command<PTPlugin> {

    public MainCommand(@NotNull PTPlugin pl, @NotNull String name) {
        super(pl, name);
    }

    @Override
    public boolean execute(User user, String label, String[] args) {
        return parseSubCommands(user, label, args);
    }
}
