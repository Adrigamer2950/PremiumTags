package me.adrigamer2950.premiumtags.database;

import me.adrigamer2950.adriapi.api.logger.SubLogger;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public abstract class Database {

    protected final SubLogger LOGGER;

    protected final String url;

    protected final String updatePlayersTagsQuery;

    protected Database(PremiumTags plugin, DatabaseType type, String url, String updatePlayersTagsQuery) throws SQLException, ClassNotFoundException {
        this.LOGGER = new SubLogger(type.name() + " Database", plugin.LOGGER);

        this.url = url;

        this.updatePlayersTagsQuery = updatePlayersTagsQuery;

        initDatabase();
    }

    public enum DatabaseType {
        H2,
        MYSQL
    }

    public abstract Connection getConnection() throws ClassNotFoundException;

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Players(UUID varchar, TAGS varchar);");

            statement.execute();

            connection.close();
        } catch (SQLException e) {
            LOGGER.log("Error while trying to create the players table");

            connection.close();

            throw new RuntimeException(e);
        }
    }

    public final void updatePlayersTagsAtDB(String uuid, List<Tag> tags) throws SQLException, ClassNotFoundException {
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Tag firstT = tags.stream().findFirst().get();

        StringBuilder tagsForDB = new StringBuilder(firstT.getId());

        for (String id : tags.stream().map(Tag::getId).filter(id -> !Objects.equals(id, firstT.getId())).toList()) {
            tagsForDB.append(",").append(id);
        }

        Connection connection = this.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    this.updatePlayersTagsQuery
            );
            statement.setString(1, uuid);
            statement.setString(2, tagsForDB.toString());

            statement.execute();

            connection.close();
        } catch (SQLException e) {
            connection.close();

            throw new RuntimeException(e);
        }
    }
}
