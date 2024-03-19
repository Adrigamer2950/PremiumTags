package me.adrigamer2950.premiumtags.util;

import me.adrigamer2950.adriapi.api.colors.Colors;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpMessages {

    public static void sendHelpMessage(CommandSender sender, String s, List<String> list) {
        for (String str : List.of("&7-------------------[&b&lPremium&a&lTags&7]-------------------",
                "&7<> arguments are necessary, [] arguments are optional")) {
            sender.sendMessage(Colors.translateColors(str));
        }

        for (String str : list) {
            sender.sendMessage(
                    Colors.translateColors(
                            String.format(str, s)
                    )
            );
        }

        sender.sendMessage(Colors.translateColors("&7---------------------------------------------------"));
    }
}
