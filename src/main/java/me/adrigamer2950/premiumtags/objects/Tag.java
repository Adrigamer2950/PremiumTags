package me.adrigamer2950.premiumtags.objects;

public class Tag {

    private final String id;
    private final String formatted;
    private final String description;

    public Tag(String id, String formatted, String description) {
        this.id = id;
        this.formatted = formatted;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getFormatted() {
        return this.formatted;
    }

    public String getDescription() {
        return this.description;
    }
}
