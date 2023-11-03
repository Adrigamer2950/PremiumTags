package me.adrigamer2950.premiumtags.placeholderapi;

import me.adrigamer2950.premiumtags.managers.TagsManager;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TagsPlaceHolderAPI extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "premiumtags";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Adrigamer2950";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.startsWith("tag_id")) {
            Tag t = TagsManager.getPlayerTag(player);

            String[] args = params.split("tag_id");

            if(t == null) return "";

            return t.getId() + (!(args.length < 1) && args[1].equalsIgnoreCase("_spaced") ? " " : "");
        }
        if(params.startsWith("tag_formatted")) {
            Tag t = TagsManager.getPlayerTag(player);

            String[] args = params.split("tag_formatted");

            if(t == null) return "";

            return t.getFormatted() + (!(args.length < 1) && args[1].equalsIgnoreCase("_spaced") ? " " : "");
        }

        return null;
    }
}
