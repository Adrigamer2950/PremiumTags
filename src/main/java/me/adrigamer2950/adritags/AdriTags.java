package me.adrigamer2950.adritags;

import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.adritags.commands.MainCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AdriTags extends JavaPlugin {

    private final APILogger logger = new APILogger(this.getDescription().getName(), null);

    /** Command Manager */
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.commandManager = new CommandManager(this);

        this.commandManager.registerCommand(new MainCommand(this, "adritags", List.of("tags")));

        logger.log("&aEnabled");
    }

    @Override
    public void onDisable() {
        logger.log("&cDisabled");

        this.commandManager = null;
    }
}
