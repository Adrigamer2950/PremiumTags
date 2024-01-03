package me.adrigamer2950.premiumtags.managers;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.Tag;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.NoSuchElementException;

public class TagsManager {

    private final PremiumTags plugin;
    public TagsManager(PremiumTags plugin) {
        this.plugin = plugin;
    }

    public List<Tag> getTagList() {
        return plugin.tagList;
    }

    public void registerTag(Tag t) {
        plugin.tagList.add(t);
    }

    public void setTagToPlayer(Player p, Tag tag) {
        plugin.playersUsingTags.put(p.getUniqueId(), tag);
    }

    public Tag getPlayerTag(OfflinePlayer player) {
        return plugin.playersUsingTags.get(player.getUniqueId());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Tag getTag(String id) {
        try {
            return plugin.tagList.stream().filter(t -> t.getId().equals(id)).toList().stream().findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
