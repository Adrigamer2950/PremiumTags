package me.adrigamer2950.premiumtags.commands.tags;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.SubCommand;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.Tag;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SetSubCommand extends SubCommand {

    public SetSubCommand(Command parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length < 2) {
            sender.sendMessage(Colors.translateColors("&cYou must name a player!"));
            return true;
        }

        Player p = Bukkit.getPlayer(args[1]);
        if(p == null) {
            sender.sendMessage(Colors.translateColors("&cPlayer not found"));
            return true;
        }

        if(args.length < 3) {
            sender.sendMessage(Colors.translateColors("&cYou must specify a tag id"));
            return true;
        }

        String id = args[2];
        Tag tag = ((PremiumTags) getPlugin()).tagsManager.getTag(id);

        if(tag == null) {
            sender.sendMessage(Colors.translateColors("&cTag not found"));
            return true;
        }

        ((PremiumTags) getPlugin()).tagsManager.setTagToPlayer(p, tag);
        sender.sendMessage(Colors.translateColors(
                String.format("&aTag &7[%s&7] &aadded successfully to &6%s", tag.getFormatted(), p.getName())
        ));

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if(args.length < 2) return null;
        if(args.length < 3)
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(name -> name.toLowerCase().startsWith(args[1])).collect(Collectors.toList());
        if(args.length < 4)
            return ((PremiumTags) getPlugin()).tagsManager.getTagList().stream().map(Tag::getId).filter(id -> id.toLowerCase().startsWith(args[2])).collect(Collectors.toList());

        return null;
    }
}
