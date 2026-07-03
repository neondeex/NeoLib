package me.neondeex.neolib.items;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(XMaterial material) {
        this.item = material.parseItem();
        if (this.item == null) throw new IllegalArgumentException("Unsupported XMaterial: " + material);
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder(ItemStack base) {
        this.item = base.clone();
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder name(Component name) {
        meta.displayName(name);
        return this;
    }

    public ItemBuilder lore(Component... lines) {
        meta.lore(Arrays.asList(lines));
        return this;
    }

    public ItemBuilder lore(List<Component> lines) {
        meta.lore(lines);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(Math.max(1, Math.min(amount, 64)));
        return this;
    }

    // --- NBT (item-nbt-api) --- use for raw NBT; prefer PdcService (EmakiCoreLib) for plugin data ---

    public ItemBuilder nbtString(String key, String value) {
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setString(key, value);
        item = nbt.getItem();
        meta = item.getItemMeta();
        return this;
    }

    public ItemBuilder nbtInt(String key, int value) {
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setInteger(key, value);
        item = nbt.getItem();
        meta = item.getItemMeta();
        return this;
    }

    public ItemBuilder nbtBoolean(String key, boolean value) {
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setBoolean(key, value);
        item = nbt.getItem();
        meta = item.getItemMeta();
        return this;
    }

    // --- Static NBT helpers ---

    public static String getNbtString(ItemStack item, String key) {
        if (item == null) return null;
        return new NBTItem(item).getString(key);
    }

    public static boolean hasNbtKey(ItemStack item, String key) {
        if (item == null) return false;
        return new NBTItem(item).hasKey(key);
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
