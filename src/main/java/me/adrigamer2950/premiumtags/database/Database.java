package me.adrigamer2950.premiumtags.database;

import me.adrigamer2950.adriapi.api.logger.SubLogger;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.database.sql.H2Database;
import me.adrigamer2950.premiumtags.database.sql.MySQLDatabase;
import me.adrigamer2950.premiumtags.database.yaml.YAMLDatabase;
import me.adrigamer2950.premiumtags.objects.tag.Tag;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public abstract class Database {

    protected final PremiumTags plugin;

    protected final SubLogger LOGGER;

    protected Database(PremiumTags plugin, DatabaseType type) {
        this.LOGGER = new SubLogger(type.name() + " Database", plugin.LOGGER);

        this.plugin = plugin;
    }

    public abstract void loadTags();

    public abstract void saveTags();

    public abstract void removeTag(String id);

    public abstract List<Tag> getTags();

    public abstract void updatePlayerTags(UUID uuid, List<Tag> tags);

    public abstract void loadPlayerData();

    public void reloadData() {
        plugin.tagList.clear();
        plugin.playersUsingTags.clear();

        loadTags();
        loadPlayerData();
    }

    public static Database getDatabase(PremiumTags plugin) throws SQLException, ClassNotFoundException {
        switch (plugin.config.Database.DRIVER()) {
            case H2 -> {
                return new H2Database(plugin);
            }
            case YAML -> {
                return new YAMLDatabase(plugin);
            }
            case MYSQL -> {
                return new MySQLDatabase(plugin);
            }
        }

        return null;
    }
}
