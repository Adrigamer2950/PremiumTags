package me.adrigamer2950.premiumtags.config;

import me.adrigamer2950.adriapi.api.config.yaml.YamlConfig;
import me.adrigamer2950.premiumtags.database.DatabaseType;

public class Config {

    public final Database Database;

    public Config(YamlConfig yaml) {
        this.Database = new Database(
                DatabaseType.valueOf(yaml.getYaml().getString("database.driver")),
                yaml.getYaml().getString("database.mysql.hostname"),
                yaml.getYaml().getInt("database.mysql.port"),
                yaml.getYaml().getString("database.mysql.database"),
                yaml.getYaml().getString("database.mysql.username"),
                yaml.getYaml().getString("database.mysql.password")
        );
    }

    public record Database(
            DatabaseType DRIVER,
            String MYSQL_HOSTNAME,
            int MYSQL_PORT,
            String MYSQL_DATABASE,
            String MYSQL_USERNAME,
            String MYSQL_PASSWORD
    ) {}
}
