package me.adrigamer2950.premiumtags;

import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.premiumtags.commands.MainCommand;
import me.adrigamer2950.premiumtags.managers.InventoryManager;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.adrigamer2950.premiumtags.managers.TagsManager;
import me.adrigamer2950.premiumtags.placeholderapi.PAPIExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class PremiumTags extends JavaPlugin {

    private final APILogger logger = new APILogger(this.getDescription().getName(), null);

    private CommandManager commandManager;

    public List<Tag> tagList;
    public HashMap<UUID, List<Tag>> playersUsingTags;
    public TagsManager tagsManager;
    public InventoryManager invManager;

    @Override
    public void onEnable() {
        this.tagList = new ArrayList<>();
        this.playersUsingTags = new HashMap<>();
        this.tagsManager = new TagsManager(this);
        this.invManager = new InventoryManager(this);

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new PAPIExpansion(this).register();

        this.commandManager = new CommandManager(this);

        this.commandManager.registerCommand(new MainCommand(this, "tags"));

        getServer().getPluginManager().registerEvents(new InventoryManager(this), this);

        tagsManager.registerTag(new Tag("test", "§a☺", "", 0));
        tagsManager.registerTag(new Tag("test2", "§a♠", "", 0));
        tagsManager.registerTag(new Tag("test3", "§e⭐", "", 10));

        logger.log("&aEnabled");
    }

    @Override
    public void onDisable() {
        this.commandManager = null;
        this.tagList = null;
        this.playersUsingTags = null;
        this.tagsManager = null;
        this.invManager = null;

        logger.log("&cDisabled");
    }
}
