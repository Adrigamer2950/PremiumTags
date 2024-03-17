package me.adrigamer2950.premiumtags.database.yaml;

import me.adrigamer2950.adriapi.api.config.yaml.YamlConfig;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.database.Database;
import me.adrigamer2950.premiumtags.database.DatabaseType;
import me.adrigamer2950.premiumtags.objects.Tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class YAMLDatabase extends Database {

    private final YamlConfig yamlTags;
    private final YamlConfig yamlPlayers;

    public YAMLDatabase(PremiumTags plugin) {
        super(plugin, DatabaseType.YAML);

        this.yamlTags = new YamlConfig(
                plugin.getDataFolder().getAbsolutePath() + File.separator + "database",
                "tags",
                plugin,
                false,
                false
        );

        this.yamlPlayers = new YamlConfig(
                plugin.getDataFolder().getAbsolutePath() + File.separator + "database",
                "players",
                plugin,
                false,
                false
        );

        loadTags();
        loadPlayerData();
    }

    @Override
    public void loadTags() {
        List<Tag> tags = getTags();

        for (Tag tag : tags)
            plugin.tagsManager.registerTag(tag, false);
    }

    @Override
    public void saveTags() {
        for(Tag t : plugin.tagList) {
            this.yamlTags.getYaml().set(t.getId() + ".tag", t.getFormatted());
            this.yamlTags.getYaml().set(t.getId() + ".description", t.getDescription());
            this.yamlTags.getYaml().set(t.getId() + ".priority", t.getPriority());
        }

        try {
            this.yamlTags.saveConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeTag(String id) {
        this.yamlTags.getYaml().set(id, null);
    }

    @Override
    public List<Tag> getTags() {
        if(this.yamlTags.getYaml() == null)
            try {
                this.yamlTags.loadConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        List<Tag> tags = new ArrayList<>();

        for (String id : this.yamlTags.getYaml().getKeys(false)) {
            String tag = this.yamlTags.getYaml().getString(id + ".tag");
            String description = this.yamlTags.getYaml().getString(id + ".description");
            int priority = this.yamlTags.getYaml().getInt(id + ".priority");

            tags.add(new Tag(id, tag, description, priority));
        }

        return tags;
    }

    @Override
    public void updatePlayerTags(UUID uuid, List<Tag> tags) {
        if(tags.isEmpty())
            this.yamlPlayers.getYaml().set(uuid.toString(), null);
        else
            this.yamlPlayers.getYaml().set(uuid.toString(), tags.stream().map(Tag::getId).toList());

        try {
            this.yamlPlayers.saveConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadPlayerData() {
        String uuidS = null;
        try {
            this.yamlPlayers.loadConfig();

            for(String _uuidS : this.yamlPlayers.getYaml().getKeys(false)) {
                uuidS = _uuidS;
                UUID uuid = UUID.fromString(uuidS);

                List<Tag> tags = this.yamlPlayers.getYaml().getStringList(uuidS)
                        .stream()
                        .map(id -> plugin.tagsManager.getTag(id))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toCollection(ArrayList::new));

                plugin.playersUsingTags.put(uuid, tags);

                uuidS = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            if (uuidS != null)
                this.yamlPlayers.getYaml().set(uuidS, null);
        }
    }
}
