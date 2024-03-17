package me.adrigamer2950.premiumtags.config;

import me.adrigamer2950.adriapi.api.config.yaml.YamlConfig;
import me.adrigamer2950.premiumtags.database.DatabaseType;

public class Config {

    public final Database Database;

    public Config(YamlConfig yaml) {
        this.Database = new Database(yaml);
    }

    public static class Database {

        public final DatabaseType DRIVER;
        public final String MYSQL_HOSTNAME;
        public final int MYSQL_PORT;
        public final String MYSQL_DATABASE;
        public final String MYSQL_USERNAME;
        public final String MYSQL_PASSWORD;

        public Database(YamlConfig yaml) {
            this.DRIVER = DatabaseType.valueOf(yaml.getYaml().getString("database.driver"));

            this.MYSQL_HOSTNAME = yaml.getYaml().getString("database.mysql.hostname");
            this.MYSQL_PORT = yaml.getYaml().getInt("database.mysql.port");
            this.MYSQL_DATABASE = yaml.getYaml().getString("database.mysql.database");
            this.MYSQL_USERNAME = yaml.getYaml().getString("database.mysql.username");
            this.MYSQL_PASSWORD = yaml.getYaml().getString("database.mysql.password");
        }
    }
}
