package me.adrigamer2950.premiumtags.managers;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.exceptions.TagFileNotExistsException;
import me.adrigamer2950.premiumtags.objects.Tag;
import me.adrigamer2950.premiumtags.objects.TagYamlFile;

import java.io.File;
import java.util.HashMap;

public class TagsFileManager {

    private static HashMap<Tag, TagYamlFile> tagsFiles;

    public static void createTag(Tag t) throws TagFileNotExistsException {
        PremiumTags plugin = PremiumTags.getPlugin(PremiumTags.class);
        TagYamlFile tFile = new TagYamlFile(plugin.getDataFolder().getAbsolutePath() + File.separator + "tags", t.getId(), plugin);

        if(!tFile.fileExists())
            throw new TagFileNotExistsException("");
    }

    public static void initTags() {

    }
}
