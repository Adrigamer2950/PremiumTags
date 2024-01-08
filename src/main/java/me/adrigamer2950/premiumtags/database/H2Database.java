package me.adrigamer2950.premiumtags.database;

import me.adrigamer2950.premiumtags.PremiumTags;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Database extends Database {

    public H2Database(PremiumTags plugin) throws ClassNotFoundException, SQLException {
        super(
                plugin,
                DatabaseType.H2,
                "jdbc:h2:" + plugin.getDataFolder().getAbsolutePath() + "/database",
                "MERGE INTO players(uuid, tags) KEY(uuid) VALUES (?, ?);",
        );
    }

    @Override
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

    @Override
    public void initDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");

        super.initDatabase();
    }
}
