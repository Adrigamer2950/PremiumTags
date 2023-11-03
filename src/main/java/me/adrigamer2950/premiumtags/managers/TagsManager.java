package me.adrigamer2950.premiumtags.managers;

import me.adrigamer2950.premiumtags.objects.Tag;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class TagsManager {

    private static List<Tag> tagList;
    private static HashMap<UUID, Tag> playerTags;

    public static void init() {
        tagList = new ArrayList<>();
        playerTags = new HashMap<>();
    }

    public static List<Tag> getTagList() {
        return tagList.stream().toList();
    }

    public static void registerTag(Tag t) {
        tagList.add(t);
    }

    public static void setTagToPlayer(Player p, Tag tag) {
        playerTags.put(p.getUniqueId(), tag);
    }

    public static Tag getPlayerTag(OfflinePlayer player) {
        return playerTags.get(player.getUniqueId());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static Tag getTag(String id) {
        try {
            return tagList.stream().filter(t -> t.getId().equals(id)).toList().stream().findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
