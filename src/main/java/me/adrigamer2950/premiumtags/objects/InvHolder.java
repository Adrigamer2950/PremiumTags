package me.adrigamer2950.premiumtags.objects;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.premiumtags.PremiumTags;
import me.adrigamer2950.premiumtags.managers.InventoryManager;
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
public class InvHolder implements InventoryHolder {

    private final Inventory inv;

    public InvHolder(PremiumTags plugin) {
        this.inv = Bukkit.createInventory(this, 54, Colors.translateColors("&1Premium&4Tags"));

        ItemStack stack;
        ItemMeta meta;

        stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        meta = stack.getItemMeta();

        meta.setDisplayName(" ");

        for(int slot : InventoryManager.GLASS_SLOTS)
            inv.setItem(slot, stack);

        if(plugin.tagList.size() > 28) {
            stack = new ItemStack(Material.PAPER, 1);
            meta = stack.getItemMeta();

            meta.setDisplayName(Colors.translateColors("&ePages: &l" + plugin.tagList.size() / 28));
            stack.setItemMeta(meta);

            inv.setItem(49, stack);
        }

        for(int i = 0 ; i < plugin.tagList.size() ; i++) {
            stack = new ItemStack(Material.NAME_TAG, 1);
            meta = stack.getItemMeta();

            Tag tag = plugin.tagList.get(i);

            List<String> lore = new ArrayList<>();

            lore.add(Colors.translateColors("&6&m                           "));
            lore.add(Colors.translateColors(""));
            lore.add(Colors.translateColors(String.format("&eID: &7%s", tag.getId())));
            lore.add(Colors.translateColors(""));

            if(tag.getDescription() != null
                    && !tag.getDescription().equals("")
                    && !tag.getDescription().replaceAll(" ", "").equals(""))
            {
                lore.add( Colors.translateColors("&bDescription: "));
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
}
