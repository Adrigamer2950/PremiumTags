package me.adrigamer2950.premiumtags.database;

import me.adrigamer2950.premiumtags.PremiumTags;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase extends Database {

    public MySQLDatabase(PremiumTags plugin) throws SQLException, ClassNotFoundException {
        super(
                plugin, DatabaseType.MYSQL,
                "jdbc:mysql://" +
                        plugin.config.Database.MYSQL_HOSTNAME +
                        ":" +
                        plugin.config.Database.MYSQL_PORT + "/" +
                        plugin.config.Database.MYSQL_DATABASE,
                "INSERT INTO players (uuid, tags) VALUES (?, ?) ON DUPLICATE KEY UPDATE tags = VALUES(tags);",
                "CREATE TABLE IF NOT EXISTS players (uuid varchar(36), tags varchar(512));"
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
}
