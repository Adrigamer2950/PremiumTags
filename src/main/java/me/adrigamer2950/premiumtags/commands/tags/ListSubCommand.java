package me.adrigamer2950.premiumtags.commands.tags;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.tag.Tag;
import me.adrigamer2950.premiumtags.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ListSubCommand extends SubCommand {

    public ListSubCommand(Command parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!sender.hasPermission(Permissions.ALL) || !sender.hasPermission(Permissions.LIST) || !sender.isOp()) {
            sender.sendMessage(Colors.translateColors("&cYou don't have permission to use this command!"));
            return true;
        }

        if (args.length < 2) {
            List<Tag> tags = ((PremiumTags) getPlugin()).tagList;

            sender.sendMessage(Colors.translateColors("&aTag list:"));

            for (Tag t : tags) {
                sender.sendMessage(Colors.translateColors(String.format("&f| &6%s&7: &6%s", t.getId(), "&7[&r" + t.getFormatted() + "&7]&r")));
            }
        } else {
            @SuppressWarnings("deprecation") OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
            if (!p.hasPlayedBefore()) {
                sender.sendMessage(Colors.translateColors("&cPlayer not found"));
                return true;
            }

            List<Tag> tags = ((PremiumTags) getPlugin()).playersUsingTags.get(p.getUniqueId());

            if (tags == null || tags.isEmpty()) {
                sender.sendMessage(Colors.translateColors("&cThat players doesn't have any tag selected!"));

                return true;
            }

            StringBuilder tagsStr = new StringBuilder("&7[" + tags.stream().findFirst().get().getFormatted() + "&7]");

            for (int i = 1; i < tags.size(); i++) {
                Tag t = tags.get(i);

                tagsStr.append("&f, &7[").append(t.getFormatted()).append("&7]");
            }

            sender.sendMessage(Colors.translateColors("&7| &6" + p.getName() + "'s &fTags:"));
            sender.sendMessage(Colors.translateColors("&7| " + tagsStr));

        }

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return null;
        if (args.length < 3)
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(name -> name.toLowerCase().startsWith(args[1])).collect(Collectors.toList());

        return null;
    }
}
