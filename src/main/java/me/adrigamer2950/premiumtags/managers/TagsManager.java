package me.adrigamer2950.premiumtags.managers;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.tag.Tag;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class TagsManager {

    private final PremiumTags plugin;

    public TagsManager(PremiumTags plugin) {
        this.plugin = plugin;
    }

    public List<Tag> getTagList() {
        return plugin.tagList;
    }

    public void registerTag(Tag t, boolean saveToDB) {
        Objects.requireNonNull(t);

        plugin.tagList.add(t);

        plugin.tagList.sort((tag1, tag2) -> tag2.getPriority() - tag1.getPriority());

        if (saveToDB) plugin.database.saveTags();
    }

    public void unRegisterTag(Tag t) {
        Objects.requireNonNull(t);

        plugin.tagList.remove(t);

        plugin.database.removeTag(t.getId());

        plugin.database.saveTags();
    }

    public void setTagToPlayer(UUID uuid, Tag tag) {
        List<Tag> tags = plugin.playersUsingTags.get(uuid);

        if (tags == null)
            tags = new ArrayList<>();

        tags.add(tag);

        tags.sort((tag1, tag2) -> tag2.getPriority() - tag1.getPriority());

        plugin.playersUsingTags.put(uuid, tags);

        plugin.database.updatePlayerTags(uuid, tags);
    }

    public void removeTagFromPlayer(UUID uuid, Tag tag) {
        List<Tag> tags = plugin.playersUsingTags.get(uuid);

        if (tags == null)
            return;

        tags.remove(tag);

        plugin.playersUsingTags.put(uuid, tags);

        plugin.database.updatePlayerTags(uuid, tags);
    }

    public List<Tag> getPlayerTags(OfflinePlayer player) {
        if (plugin.playersUsingTags.get(player.getUniqueId()) == null)
            return List.of();

        return plugin.playersUsingTags.get(player.getUniqueId());
    }

    public Tag getPlayerMainTag(OfflinePlayer player) {
        if (plugin.playersUsingTags.get(player.getUniqueId()) == null || plugin.playersUsingTags.get(player.getUniqueId()).isEmpty())
            return null;

        //noinspection OptionalGetWithoutIsPresent
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
