package me.adrigamer2950.premiumtags.managers;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.objects.InvHolder;
import me.adrigamer2950.premiumtags.objects.Tag;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InventoryManager implements Listener {

    public static final int[] GLASS_SLOTS = new int[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    public static final int[] TAG_SLOTS = new int[]{
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
        if (!(e.getWhoClicked() instanceof Player p))
            return;

        if (e.getClickedInventory() == null
                || !(e.getClickedInventory().getHolder() instanceof InvHolder)
                || e.getClickedInventory().getItem(e.getRawSlot()) == null
        )
            return;

        e.setCancelled(true);

        ItemStack stack = e.getClickedInventory().getItem(e.getRawSlot());

        if (stack == null)
            return;

        if (stack.getItemMeta() == null)
            return;

        if (!stack.getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(plugin, "tag_item"), PersistentDataType.STRING)
        )
            return;

        Tag tag = plugin.tagsManager.getTag(stack.getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(plugin, "tag_item"), PersistentDataType.STRING));

        if (tag == null) {
            e.setCancelled(true);

            plugin.invManager.openInventory(p);

            return;
        }

        if (plugin.tagsManager.getPlayerTags(p).stream().map(Tag::getId).toList().contains(tag.getId())) {
            plugin.tagsManager.removeTagFromPlayer(p.getUniqueId(), tag);
            p.sendMessage(Colors.translateColors(
                    String.format("&cTag &7[%s&7] &csuccessfully removed", tag.getFormatted())
            ));
        } else {
            plugin.tagsManager.setTagToPlayer(p.getUniqueId(), tag);
            p.sendMessage(Colors.translateColors(
                    String.format("&aTag &7[%s&7] &asuccessfully set", tag.getFormatted())
            ));
        }

        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
        p.closeInventory();
    }
}
