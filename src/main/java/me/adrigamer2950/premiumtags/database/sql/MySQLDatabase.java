package me.adrigamer2950.premiumtags.database.sql;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.database.DatabaseType;
import me.adrigamer2950.premiumtags.objects.Tag;

import java.sql.*;
import java.util.*;

public class MySQLDatabase extends SqlLikeDatabase {

    public MySQLDatabase(PremiumTags plugin) throws SQLException, ClassNotFoundException {
        super(
                plugin, DatabaseType.MYSQL,
                "jdbc:mysql://" +
                        plugin.config.Database.MYSQL_HOSTNAME +
                        ":" +
                        plugin.config.Database.MYSQL_PORT + "/" +
                        plugin.config.Database.MYSQL_DATABASE
        );
    }

    @Override
    public Connection getConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection;
        try {
            connection = DriverManager.getConnection(this.url, plugin.config.Database.MYSQL_USERNAME, plugin.config.Database.MYSQL_PASSWORD);
        } catch (SQLException e) {
            LOGGER.log("Error while trying to connect to the MySQL database: " + e);
            throw new RuntimeException(e);
        }

        return connection;
    }

    @Override
    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("create table if not exists players (uuid varchar(36), tags varchar(512));");

            statement.execute();
        } catch (SQLException e) {
            LOGGER.log("Error while trying to create the players table");

            connection.close();

            throw new RuntimeException(e);
        }

        try {
            statement = connection.prepareStatement("create table if not exists tags (id varchar(32) not null, tag varchar(512), description text, priority integer, primary key (id)) ");

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
                        "insert into tags (id, tag, description, priority) " +
                                "values(?, ?, ?, ?) " +
                                "on duplicate key update " +
                                "id = values(id), tag = values(tag), description = values(description), priority = values(priority)"
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
                        "insert into players (uuid, tags) " +
                                "values(?, ?) " +
                                "on duplicate key update " +
                                "uuid = values(uuid)"
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
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }

            throw new RuntimeException(e);
        }
    }


}
