package me.adrigamer2950.premiumtags.objects.tag;

public final class Tag {

    private final String id;
    private final String formatted;
    private final String description;
    private final int priority;
    private final boolean needsPerm;

    public Tag(String id, String formatted, String description, int priority) {
        this(id, formatted, description, priority, false);
    }

    public Tag(String id, String formatted, String description, int priority, boolean needsPerm) {
        this.id = id;
        this.formatted = formatted;
        this.description = description;
        this.priority = priority;
        this.needsPerm = needsPerm;
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

    public boolean isPermissionNeeded() {
        return this.needsPerm;
    }
}
