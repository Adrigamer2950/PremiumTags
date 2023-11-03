package me.adrigamer2950.premiumtags;

import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.premiumtags.commands.MainCommand;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.adrigamer2950.premiumtags.managers.TagsManager;
import me.adrigamer2950.premiumtags.placeholderapi.TagsPlaceHolderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PremiumTags extends JavaPlugin {

    private final APILogger logger = new APILogger(this.getDescription().getName(), null);

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new TagsPlaceHolderAPI().register();

        this.commandManager = new CommandManager(this);

        this.commandManager.registerCommand(new MainCommand(this, "tags"));

        TagsManager.init();

        TagsManager.registerTag(new Tag("test", "§a☺ §f"));
        TagsManager.registerTag(new Tag("test2", "§a♠ §f"));

        logger.log("&aEnabled");
    }

    @Override
    public void onDisable() {
        logger.log("&cDisabled");

        this.commandManager = null;
    }
}
