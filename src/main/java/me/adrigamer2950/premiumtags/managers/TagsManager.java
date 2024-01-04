package me.adrigamer2950.premiumtags.managers;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.Tag;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class TagsManager {

    private final PremiumTags plugin;
    public TagsManager(PremiumTags plugin) {
        this.plugin = plugin;
    }

    public List<Tag> getTagList() {
        return plugin.tagList;
    }

    public void registerTag(Tag t) {
        Objects.requireNonNull(t);

        plugin.tagList.add(t);

        plugin.tagList.sort((tag1, tag2) -> tag2.getPriority() - tag1.getPriority());
    }

    public void setTagToPlayer(Player p, Tag tag) {
        List<Tag> tags = plugin.playersUsingTags.get(p.getUniqueId());

        if(tags == null)
            tags = new ArrayList<>();

        tags.add(tag);

        tags.sort((tag1, tag2) -> tag2.getPriority() - tag1.getPriority());

        plugin.playersUsingTags.put(p.getUniqueId(), tags);
    }

    public void removeTagFromPlayer(Player p, Tag tag) {
        List<Tag> tags = plugin.playersUsingTags.get(p.getUniqueId());

        if(tags == null)
            return;

        tags.remove(tag);

        plugin.playersUsingTags.put(p.getUniqueId(), tags);
    }

    public List<Tag> getPlayerTags(OfflinePlayer player) {
        if(plugin.playersUsingTags.get(player.getUniqueId()) == null)
            return List.of();

        return plugin.playersUsingTags.get(player.getUniqueId());
    }

    public Tag getPlayerMainTag(OfflinePlayer player) {
        if(plugin.playersUsingTags.get(player.getUniqueId()) == null || plugin.playersUsingTags.get(player.getUniqueId()).isEmpty())
            return null;

        return plugin.playersUsingTags.get(player.getUniqueId()).stream().findFirst().get();
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
