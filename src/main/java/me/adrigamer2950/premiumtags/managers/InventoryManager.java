package me.adrigamer2950.premiumtags.managers;

import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.InvHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryManager implements Listener {

    public static final int[] GLASS_SLOTS = new int[] {
            0,  1,  2,  3,  4,  5,   6,   7,   8,
            9,                                 17,
            18,                                26,
            27,                                35,
            36,                                44,
            45,  46,  47, 48, 49, 50, 51, 52,  53
    };

    public static final int[] TAG_SLOTS = new int[] {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43
    };

    private final PremiumTags plugin;

    public InventoryManager(PremiumTags plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player p) {
        p.openInventory(new InvHolder(plugin).getInventory());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null)
            return;

        if(!(e.getClickedInventory().getHolder() instanceof InvHolder))
            return;


    }
}
