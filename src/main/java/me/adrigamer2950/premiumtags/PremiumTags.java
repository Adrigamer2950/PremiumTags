package me.adrigamer2950.premiumtags;

import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.config.manager.ConfigManager;
import me.adrigamer2950.adriapi.api.config.yaml.YamlConfig;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.premiumtags.commands.MainCommand;
import me.adrigamer2950.premiumtags.config.Config;
import me.adrigamer2950.premiumtags.database.Database;
import me.adrigamer2950.premiumtags.managers.InventoryManager;
import me.adrigamer2950.premiumtags.managers.TagsManager;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.adrigamer2950.premiumtags.placeholderapi.PAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class PremiumTags extends JavaPlugin {

    public final APILogger LOGGER = new APILogger(this.getDescription().getName(), null);

    private CommandManager commandManager;
    private ConfigManager configManager;

    public List<Tag> tagList;
    public HashMap<UUID, List<Tag>> playersUsingTags;
    public TagsManager tagsManager;
    public InventoryManager invManager;
    public Config config;
    public Database database;

    private PAPIExpansion papi;

    @Override
    public void onEnable() {
        this.tagList = new ArrayList<>();
        this.playersUsingTags = new HashMap<>();
        this.invManager = new InventoryManager(this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papi = new PAPIExpansion(this);
            papi.register();
        }

        this.commandManager = new CommandManager(this);

        this.commandManager.registerCommand(new MainCommand(this, "tags"));

        getServer().getPluginManager().registerEvents(new InventoryManager(this), this);

        this.configManager = new ConfigManager(this);

        YamlConfig configF = new YamlConfig(
                this.getDataFolder().getAbsolutePath(),
                "config",
                this,
                false,
                true
        );

        try {
            configF.loadConfig();

            this.config = new Config(configF);

            this.tagsManager = new TagsManager(this);

            this.database = Database.getDatabase(this);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        LOGGER.log("&aEnabled");
    }

    @Override
    public void onDisable() {
        this.configManager.saveConfigFiles();

        papi.unregister();

        this.commandManager = null;
        this.tagList = null;
        this.playersUsingTags = null;
        this.tagsManager = null;
        this.invManager = null;
        this.configManager = null;
        this.config = null;
        this.database = null;

        LOGGER.log("&cDisabled");
    }
}
