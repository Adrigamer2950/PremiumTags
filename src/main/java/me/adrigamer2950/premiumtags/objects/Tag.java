package me.adrigamer2950.premiumtags.objects;

import org.jetbrains.annotations.Nullable;

public class Tag {

    private final String id;
    private final String formatted;
    private final String category;
    private final String description;

    public Tag(String id, String formatted, @Nullable String category, String description) {
        this.id = id;
        this.formatted = formatted;
        this.category = category;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getFormatted() {
        return this.formatted;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }
}
