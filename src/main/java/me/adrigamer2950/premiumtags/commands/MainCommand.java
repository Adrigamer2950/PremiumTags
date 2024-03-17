package me.adrigamer2950.premiumtags.commands;

import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.commands.tags.*;
import me.adrigamer2950.premiumtags.objects.SelectionInventoryHolder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class MainCommand extends Command {

    public MainCommand(@NotNull Plugin pl, @NotNull String name) {
        super(pl, name);

        addSubCommand(new AddSubCommand(this, "add"));
        addSubCommand(new RemoveSubCommand(this, "remove"));
        addSubCommand(new ListSubCommand(this, "list"));

        addSubCommand(new CreateTagSubCommand(this, "create"));
        addSubCommand(new DeleteTagSubCommand(this, "delete"));

        addSubCommand(new ReloadSubCommand(this, "reload"));

        setHelpSubCommand(new HelpSubCommand(this, "help"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length < 1 && sender instanceof Player) {
            ((PremiumTags) getPlugin()).invManager.openInventory((Player) sender, new SelectionInventoryHolder((PremiumTags) getPlugin(), 0));

            return true;
        }

        return parseSubCommands(sender, label, args);
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            return parseSubCommandsTabCompleter(sender, label, args).stream().filter(str -> str.startsWith(args[0])).collect(Collectors.toList());
        }

        return parseSubCommandsTabCompleter(sender, label, args);
    }
}
