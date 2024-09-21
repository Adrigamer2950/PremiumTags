package me.adrigamer2950.premiumtags;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.premiumtags.commands.MainCommand;
import me.adrigamer2950.premiumtags.objects.Tag;

import java.util.HashSet;
import java.util.Set;

public class PTPlugin extends APIPlugin {

    public Set<Tag> tags;

    @Override
    public void onPreLoad() {
        getApiLogger().info("&6Loading...");

        this.tags = new HashSet<>();
    }

    @Override
    public void onPostLoad() {
        registerCommand(new MainCommand(this, "premiumtags"));

        getApiLogger().info("&aEnabled!");
    }

    @Override
    public void onUnload() {
        getApiLogger().info("&cDisabled!");
    }
}
