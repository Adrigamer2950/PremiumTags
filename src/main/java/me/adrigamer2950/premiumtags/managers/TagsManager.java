package me.adrigamer2950.premiumtags.managers;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.Tag;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TagsManager {

    private final PremiumTags plugin;

    public TagsManager(PremiumTags plugin) {
        this.plugin = plugin;
    }

    public List<Tag> getTagList() {
        return plugin.tagList;
    }

    public void getDataFromDatabase() throws ClassNotFoundException, SQLException {
        Connection connection = plugin.database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PLAYERS");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                UUID uuid;
                try {
                    //noinspection ResultOfMethodCallIgnored
                    uuid = UUID.fromString(result.getString("UUID"));
                } catch (IllegalArgumentException e) {
                    statement = connection.prepareStatement("DELETE FROM PLAYERS WHERE UUID = ?");
                    statement.setString(1, result.getString("UUID"));

                    statement.execute();

                    connection.close();

                    return;
                }

                String[] tags = result.getString("TAGS").split(",");

                for (String id : tags) {
                    Tag t = this.getTag(id);
                    if (t == null) continue;

                    this.setTagToPlayer(Bukkit.getOfflinePlayer(uuid), t);
                }
            }

            connection.close();
        } catch (SQLException e) {
            connection.close();

            throw new RuntimeException(e);
        }
    }

    public void registerTag(Tag t) {
        Objects.requireNonNull(t);

        plugin.tagList.add(t);

        plugin.tagList.sort((tag1, tag2) -> tag2.getPriority() - tag1.getPriority());
    }

    public void setTagToPlayer(OfflinePlayer p, Tag tag) {
        List<Tag> tags = plugin.playersUsingTags.get(p.getUniqueId());

        if (tags == null)
            tags = new ArrayList<>();

        tags.add(tag);

        tags.sort((tag1, tag2) -> tag2.getPriority() - tag1.getPriority());

        plugin.playersUsingTags.put(p.getUniqueId(), tags);

        try {
            plugin.database.updatePlayersTagsAtDB(p.getUniqueId().toString(), tags);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTagFromPlayer(OfflinePlayer p, Tag tag) {
        List<Tag> tags = plugin.playersUsingTags.get(p.getUniqueId());

        if (tags == null)
            return;

        tags.remove(tag);

        plugin.playersUsingTags.put(p.getUniqueId(), tags);

        try {
            plugin.database.updatePlayersTagsAtDB(p.getUniqueId().toString(), tags);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tag> getPlayerTags(OfflinePlayer player) {
        if (plugin.playersUsingTags.get(player.getUniqueId()) == null)
            return List.of();

        return plugin.playersUsingTags.get(player.getUniqueId());
    }

    public Tag getPlayerMainTag(OfflinePlayer player) {
        if (plugin.playersUsingTags.get(player.getUniqueId()) == null || plugin.playersUsingTags.get(player.getUniqueId()).isEmpty())
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
