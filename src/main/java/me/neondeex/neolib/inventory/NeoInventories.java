package me.neondeex.neolib.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class NeoInventories {

    private NeoInventories() {
    }

    public static Inventory create(InventoryHolder holder, int size, Component title) {
        return Bukkit.createInventory(holder, size, title == null ? Component.empty() : title);
    }
}
