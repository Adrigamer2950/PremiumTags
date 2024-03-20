package me.adrigamer2950.premiumtags.commands.other;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.util.Permissions;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand(Command parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if(!sender.hasPermission(Permissions.ALL) || !sender.hasPermission(Permissions.RELOAD) || sender.isOp()) {
            sender.sendMessage(Colors.translateColors("&cYou don't have permission to use this command!"));
            return true;
        }

        try {
            ((PremiumTags) getPlugin()).database.reloadData();
            getPlugin().reloadConfig();

            sender.sendMessage(Colors.translateColors("&aConfig files and databases reloaded"));
        } catch (Exception e) {
            sender.sendMessage(Colors.translateColors("&cAn error ocurred while trying to reload config files and databases, check console for more info"));

            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
