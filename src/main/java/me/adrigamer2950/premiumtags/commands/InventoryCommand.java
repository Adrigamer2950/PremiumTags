package me.adrigamer2950.premiumtags.commands;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.inventory.SelectionInventoryHolder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InventoryCommand extends Command {

    public InventoryCommand(@NotNull Plugin pl, @NotNull String name) {
        super(pl, name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(Colors.translateColors("&c&lThis command can only be executed as a player!"));
            return true;
        }

        ((PremiumTags) getPlugin()).invManager.openInventory((Player) commandSender, new SelectionInventoryHolder((PremiumTags) getPlugin(), 0));

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
