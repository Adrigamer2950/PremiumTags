package me.adrigamer2950.premiumtags.objects.inventory;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.managers.InventoryManager;
import me.adrigamer2950.premiumtags.objects.tag.Tag;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
public class SelectionInventoryHolder implements InventoryHolder {

    private final Inventory inv;
    private final int currentPage;

    public SelectionInventoryHolder(PremiumTags plugin, int page) {
        this.inv = Bukkit.createInventory(this, 54, Colors.translateColors(plugin.config.PlaceHolders.TAG_SELECTION_INVENTORY_TITLE()));
        this.currentPage = page;

        if (page < 0) throw new IndexOutOfBoundsException("Page cannot be lower than zero");

        ItemStack stack;
        ItemMeta meta;

        stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        meta = stack.getItemMeta();

        meta.setDisplayName(" ");

        for (int slot : InventoryManager.GLASS_SLOTS)
            inv.setItem(slot, stack);

        if (plugin.tagList.size() > 28) {
            stack = new ItemStack(Material.PAPER, 1);
            meta = stack.getItemMeta();

            meta.setDisplayName(Colors.translateColors(String.format(
                    "&ePage: &a&l%s&e/&a&l%s",
                    this.getPage() + 1,
                    (plugin.tagList.size() / 28) + 1
            )));
            stack.setItemMeta(meta);

            inv.setItem(49, stack);

            if (page > 0) {
                stack = new ItemStack(Material.ARROW, 1);
                meta = stack.getItemMeta();

                meta.setDisplayName(Colors.translateColors("&ePrevious Page"));
                stack.setItemMeta(meta);

                inv.setItem(48, stack);
            }

            if ((page == 0 && plugin.tagList.size() > 28) || (page > 0 && plugin.tagList.size() * page < plugin.tagList.size())) {
                stack = new ItemStack(Material.ARROW, 1);
                meta = stack.getItemMeta();

                meta.setDisplayName(Colors.translateColors("&eNext Page"));
                stack.setItemMeta(meta);

                inv.setItem(50, stack);
            }
        }

        List<Tag> tags = new Pagination<>(28, plugin.tagList).getPage(this.getPage());

        for (int i = 0; i < tags.size(); i++) {
            stack = new ItemStack(Material.NAME_TAG, 1);
            meta = stack.getItemMeta();

            Tag tag = tags.get(i);

            List<String> lore = new ArrayList<>();

            lore.add(Colors.translateColors("&6&m                           "));
            lore.add(Colors.translateColors(""));
            lore.add(Colors.translateColors(String.format("&eID: &7%s", tag.getId())));
            lore.add(Colors.translateColors(""));

            if (tag.getDescription() != null
                    && !tag.getDescription().isEmpty()
                    && !tag.getDescription().replaceAll(" ", "").isEmpty()) {
                lore.add(Colors.translateColors("&bDescription: "));
                lore.add(Colors.translateColors("&7" + tag.getDescription()));
                lore.add(Colors.translateColors(""));
            }

            lore.add(Colors.translateColors("&6&m                           "));

            meta.setDisplayName(Colors.translateColors(tag.getFormatted()));
            meta.setLore(lore);
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "tag_item"), PersistentDataType.STRING, tag.getId());
            stack.setItemMeta(meta);

            inv.setItem(InventoryManager.TAG_SLOTS[i], stack);
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inv;
    }

    public int getPage() {
        return this.currentPage;
    }
}
