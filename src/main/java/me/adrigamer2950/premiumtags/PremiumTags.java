package me.adrigamer2950.premiumtags;

import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.premiumtags.commands.MainCommand;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.adrigamer2950.premiumtags.managers.TagsManager;
import me.adrigamer2950.premiumtags.placeholderapi.PAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class PremiumTags extends JavaPlugin {

    private final APILogger logger = new APILogger(this.getDescription().getName(), null);

    private CommandManager commandManager;

    public Set<Tag> tagList;
    public HashMap<UUID, Tag> playersUsingTags;
    public TagsManager tagsManager;

    @Override
    public void onEnable() {
        this.tagList = new HashSet<>();
        this.playersUsingTags = new HashMap<>();
        this.tagsManager = new TagsManager(this);

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new PAPIExpansion(this).register();

        this.commandManager = new CommandManager(this);

        this.commandManager.registerCommand(new MainCommand(this, "tags"));

        tagsManager.registerTag(new Tag("test", "§a☺", ""));
        tagsManager.registerTag(new Tag("test2", "§a♠", ""));

        logger.log("&aEnabled");
    }

    @Override
    public void onDisable() {
        logger.log("&cDisabled");

        this.commandManager = null;
    }
}
