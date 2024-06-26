package me.adrigamer2950.premiumtags.commands.player;

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

public class RemoveSubCommand extends SubCommand {

    public RemoveSubCommand(Command parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!sender.hasPermission(Permissions.ALL) || !sender.hasPermission(Permissions.SET_TAG_TO) || sender.isOp()) {
            sender.sendMessage(Colors.translateColors("&cYou don't have permission to use this command!"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(Colors.translateColors("&cYou must name a player!"));
            return true;
        }

        @SuppressWarnings("deprecation") OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
        if (!p.hasPlayedBefore()) {
            sender.sendMessage(Colors.translateColors("&cPlayer not found"));
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(Colors.translateColors("&cYou must specify a tag id"));
            return true;
        }

        String id = args[2];
        Tag tag = ((PremiumTags) getPlugin()).tagsManager.getTag(id);

        if (tag == null) {
            sender.sendMessage(Colors.translateColors("&cTag not found"));
            return true;
        }

        if (!((PremiumTags) getPlugin()).tagsManager.getPlayerTags(p).stream().map(Tag::getId).toList().contains(tag.getId())) {
            sender.sendMessage(Colors.translateColors("&cThat player doesn't have that tag selected"));
            return true;
        }

        ((PremiumTags) getPlugin()).tagsManager.removeTagFromPlayer(p.getUniqueId(), tag);
        sender.sendMessage(Colors.translateColors(
                String.format("&aTag &7[%s&7] &awas removed successfully from &6%s", tag.getFormatted(), p.getName())
        ));

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return null;
        if (args.length < 3)
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(name -> name.toLowerCase().startsWith(args[1])).collect(Collectors.toList());
        if (args.length < 4)
            return ((PremiumTags) getPlugin())
                    .tagsManager.getTagList().stream().map(Tag::getId)
                    .filter(tag ->
                            ((PremiumTags) getPlugin()).tagsManager.getPlayerTags((OfflinePlayer) sender).stream().map(Tag::getId).collect(Collectors.toSet()).contains(tag))
                    .filter(id -> id.toLowerCase().startsWith(args[2])).collect(Collectors.toList());

        return null;
    }
}
