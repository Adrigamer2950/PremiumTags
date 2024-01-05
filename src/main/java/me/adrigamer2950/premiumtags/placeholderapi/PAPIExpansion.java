package me.adrigamer2950.premiumtags.placeholderapi;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PAPIExpansion extends PlaceholderExpansion {

    private final PremiumTags plugin;

    public PAPIExpansion(PremiumTags plugin) {
        this.plugin = plugin;
    }

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
        if (params.startsWith("tag_id")) {
            Tag t = plugin.tagsManager.getPlayerMainTag(player);

            String[] args = params.split("tag_id");

            if (t == null) return "";

            return t.getId() + (!(args.length < 1) && args[1].equalsIgnoreCase("_spaced") ? " " : "");
        }
        if (params.startsWith("tag")) {
            Tag t = plugin.tagsManager.getPlayerMainTag(player);

            String[] args = params.split("tag");

            if (t == null) return "";

            if (!(args.length < 1) && args[1].equalsIgnoreCase("_wrapped"))
                return "&7[" + t.getFormatted() + "&7]";

            if (!(args.length < 1) && args[1].equalsIgnoreCase("_wrapped_spaced"))
                return "&7[" + t.getFormatted() + "&7] ";

            return t.getFormatted() + (!(args.length < 1) && args[1].equalsIgnoreCase("_spaced") ? " " : "");
        }

        return null;
    }
}
