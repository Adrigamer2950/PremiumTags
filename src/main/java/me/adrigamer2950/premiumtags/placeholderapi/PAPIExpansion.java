package me.adrigamer2950.premiumtags.placeholderapi;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.tag.Tag;
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
        if (params.equalsIgnoreCase("tag_id") || params.split("_")[0].equals("tag_id")) {
            Tag t = plugin.tagsManager.getPlayerMainTag(player);

            if (t == null) return "";

            return t.getId();
        }
        if (params.equalsIgnoreCase("tag") || params.split("_")[0].equalsIgnoreCase("tag")) {
            Tag t = plugin.tagsManager.getPlayerMainTag(player);

            String[] args = params.split("_");

            if (t == null) return "";

            if (args.length >= 2 && args[1].equalsIgnoreCase("wrapped"))
                return (args.length > 3 && args[2].equalsIgnoreCase("spaced") && args[3].equalsIgnoreCase("left") ? " " : "")
                        + "&7[" + t.getFormatted() + "&7]"
                        + (args.length > 3 && args[2].equalsIgnoreCase("spaced") && args[3].equalsIgnoreCase("right") ? " " : "");

            return (!(args.length < 2) && args[1].equalsIgnoreCase("spaced") && args[2].equalsIgnoreCase("left") ? " " : "")
                    + t.getFormatted()
                    + (!(args.length < 2) && args[1].equalsIgnoreCase("spaced") && args[2].equalsIgnoreCase("right") ? " " : "");
        }

        return null;
    }
}
