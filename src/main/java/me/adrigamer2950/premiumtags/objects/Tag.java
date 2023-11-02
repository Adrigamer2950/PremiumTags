package me.adrigamer2950.premiumtags.objects;

public class Tag {

    private final String id;
    private final String formatted;

    public Tag(String id, String formatted) {
        this.id = id;
        this.formatted = formatted;
    }

    public String getId() {
        return this.id;
    }

    public String getFormatted() {
        return this.formatted;
    }
}
