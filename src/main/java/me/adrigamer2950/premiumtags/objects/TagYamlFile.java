package me.adrigamer2950.premiumtags.objects;

import me.adrigamer2950.adriapi.api.config.yaml.YamlConfig;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class TagYamlFile extends YamlConfig {

    public TagYamlFile(String path, String name, Plugin plugin) {
        super(path, name, plugin, true, false);
    }

    @Override
    public void loadConfig() throws IOException {
        File f = new File(this.path, this.name + ".yml");
        if(!f.exists())
            Files.copy(Objects.requireNonNull(plugin.getResource("exampletag.yml")), f.toPath());

        super.loadConfig();
    }
}
