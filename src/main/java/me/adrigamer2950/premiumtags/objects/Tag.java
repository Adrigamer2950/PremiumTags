package me.adrigamer2950.premiumtags.objects;

public final class Tag {

    private final String id;
    private final String formatted;
    private final String description;
    private final int priority;

    public Tag(String id, String formatted, String description, int priority) {
        this.id = id;
        this.formatted = formatted;
        this.description = description;
        this.priority = priority;
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

    public int getPriority() {
        return this.priority;
    }
}
