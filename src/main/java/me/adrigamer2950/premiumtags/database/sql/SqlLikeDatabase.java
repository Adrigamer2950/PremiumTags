package me.adrigamer2950.premiumtags.database.sql;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.database.Database;
import me.adrigamer2950.premiumtags.database.DatabaseType;
import me.adrigamer2950.premiumtags.objects.tag.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class SqlLikeDatabase extends Database {

    protected final String url;

    protected SqlLikeDatabase(PremiumTags plugin, DatabaseType type, String url) throws SQLException, ClassNotFoundException {
        super(plugin, type);

        this.url = url;

        initDatabase();

        loadTags();
        loadPlayerData();
    }

    public abstract void initDatabase() throws SQLException, ClassNotFoundException;

    public abstract Connection getConnection() throws ClassNotFoundException;

    @Override
    public void loadTags() {
        List<Tag> tags = getTags();

        for (Tag tag : tags)
            plugin.tagsManager.registerTag(tag, false);
    }

    @Override
    public void removeTag(String id) {
        Connection connection = null;

        try {
            connection = getConnection();

            PreparedStatement statement = connection.prepareStatement("delete from tags where id = ?");

            statement.setString(1, id);

            statement.executeUpdate();

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tag> getTags() {
        Connection connection = null;

        try {
            connection = getConnection();

            PreparedStatement statement = connection.prepareStatement("select * from tags");

            ResultSet result = statement.executeQuery();

            List<Tag> tags = new ArrayList<>();

            while (result.next()) {
                String id = result.getString(1);
                String tag = result.getString(2);
                String description = result.getString(3);
                int priority = result.getInt(4);

                tags.add(new Tag(id, tag, description, priority));
            }

            connection.close();

            return tags;
        } catch (ClassNotFoundException | SQLException e) {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }

            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadPlayerData() {
        Connection connection = null;

        String uuidS = null;
        try {
            connection = getConnection();

            PreparedStatement statement = connection.prepareStatement("select * from players");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                uuidS = result.getString(1);

                UUID uuid = UUID.fromString(uuidS);

                List<Tag> tags = Arrays.stream(result.getString(2).split(",")).map(
                        id -> plugin.tagsManager.getTag(id)
                ).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));

                plugin.playersUsingTags.put(uuid, tags);
            }

            connection.close();
        } catch (IllegalArgumentException e) {
            try {
                assert connection != null;
                PreparedStatement statement = connection.prepareStatement("delete from players where uuid = ?");

                statement.setString(1, uuidS);

                statement.executeUpdate();
            } catch (SQLException ex) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }

                throw new RuntimeException(ex);
            }
        } catch (ClassNotFoundException | SQLException e) {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }

            throw new RuntimeException(e);
        }
    }
}
