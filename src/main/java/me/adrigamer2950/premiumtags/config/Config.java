package me.adrigamer2950.premiumtags.config;

import me.adrigamer2950.adriapi.api.config.yaml.YamlConfig;

public class Config {

    private final YamlConfig yaml;

    public final Database Database;

    public Config(YamlConfig yaml) {
        this.yaml = yaml;

        this.Database = new Database(yaml);
    }

    public static class Database {

        public final boolean H2;
        public final String MYSQL_HOSTNAME;
        public final int MYSQL_PORT;
        public final String MYSQL_DATABASE;
        public final String MYSQL_USERNAME;
        public final String MYSQL_PASSWORD;

        public Database(YamlConfig yaml) {
            this.H2 = yaml.getYaml().getBoolean("database.h2");

            this.MYSQL_HOSTNAME = yaml.getYaml().getString("database.mysql.hostname");
            this.MYSQL_PORT = yaml.getYaml().getInt("database.mysql.port");
            this.MYSQL_DATABASE = yaml.getYaml().getString("database.mysql.database");
            this.MYSQL_USERNAME = yaml.getYaml().getString("database.mysql.username");
            this.MYSQL_PASSWORD = yaml.getYaml().getString("database.mysql.password");
        }
    }
}
