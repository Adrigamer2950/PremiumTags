package me.adrigamer2950.adritags;

import me.adrigamer2950.adriapi.api.logger.APILogger;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdriTags extends JavaPlugin {

    private final APILogger logger = new APILogger(this.getDescription().getName(), null);
    @Override
    public void onEnable() {
        logger.log("&aEnabled");
    }

    @Override
    public void onDisable() {
        logger.log("&cDisabled");
    }
}
