package me.neondeex.neolib.hooks;

import me.neondeex.neolib.NeoLib;
import org.bukkit.Bukkit;

public class HookManager {

    private final NeoLib plugin;
    // Economy handled by EconomyManager (EmakiCoreLib)
    // PAPI handled by PlaceholderApiResolver (EmakiCoreLib)
    private boolean worldGuardEnabled;

    public HookManager(NeoLib plugin) {
        this.plugin = plugin;
    }

    public void register() {
        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            worldGuardEnabled = true;
            plugin.getLogger().info("[Hooks] WorldGuard: OK");
        }
    }

    public boolean hasWorldGuard() { return worldGuardEnabled; }

    public String getSummary() {
        return String.format("WorldGuard=%s", hasWorldGuard() ? "OK" : "no");
    }
}
