package me.adrigamer2950.premiumtags.database.sql;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.database.DatabaseType;
import me.adrigamer2950.premiumtags.objects.tag.Tag;

import java.sql.*;
import java.util.*;

public class H2Database extends SqlLikeDatabase {

    public H2Database(PremiumTags plugin) throws SQLException, ClassNotFoundException {
        super(
                plugin,
                DatabaseType.H2,
                "jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/database"
        );
    }

    public Connection getConnection() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");

        Connection connection;
        try {
            connection = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            LOGGER.log("Error while trying to connect to the H2 database: " + e);
            throw new RuntimeException(e);
        }

        return connection;
    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("create table if not exists players (uuid varchar, tags varchar);");

            statement.execute();
        } catch (SQLException e) {
            LOGGER.log("Error while trying to create the players table");

            connection.close();

            throw new RuntimeException(e);
        }

        try {
            statement = connection.prepareStatement("create table if not exists tags (id varchar, tag varchar, description text, priority integer);");

            statement.execute();

            connection.close();
        } catch (SQLException e) {
            LOGGER.log("Error while trying to create the tags table");

            connection.close();

            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveTags() {
        Connection connection = null;

        try {
            connection = getConnection();

            for (Tag t : plugin.tagList) {
                PreparedStatement statement = connection.prepareStatement(
                        "merge into tags (id, tag, description, priority) key (id) values (?, ?, ?, ?)"
                );

                statement.setString(1, t.getId());
                statement.setString(2, t.getFormatted());
                statement.setString(3, t.getDescription());
                statement.setInt(4, t.getPriority());

                statement.executeUpdate();
            }

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ignored) {
            }

            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePlayerTags(UUID uuid, List<Tag> tags) {
        Connection connection = null;

        try {
            connection = getConnection();

            PreparedStatement statement;
            if (tags.isEmpty()) {
                statement = connection.prepareStatement(
                        "delete from players where uuid = ?"
                );

                statement.setString(1, uuid.toString());

            } else {
                statement = connection.prepareStatement(
                        "merge into players (uuid, tags) key (uuid) values (?, ?)"
                );

                @SuppressWarnings("OptionalGetWithoutIsPresent")
                Tag firstT = tags.stream().findFirst().get();

                StringBuilder tagsS = new StringBuilder(firstT.getId());

                for (String id : tags.stream().map(Tag::getId).filter(id -> !Objects.equals(id, firstT.getId())).toList()) {
                    tagsS.append(",").append(id);
                }

                statement.setString(1, uuid.toString());
                statement.setString(2, tagsS.toString());

            }
            statement.executeUpdate();

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ignored) {
            }

            throw new RuntimeException(e);
        }
    }
}
