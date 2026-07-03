package me.neondeex.neolib.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandExecutor;
import dev.jorel.commandapi.executors.PlayerCommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Factory rápida para registrar comandos CommandAPI.
 */
public final class NeoCommand {

    private NeoCommand() {}

    /** Comando ejecutable por cualquier CommandSender. */
    public static CommandAPICommand of(String name, CommandExecutor executor) {
        return new CommandAPICommand(name).executes(executor);
    }

    /** Comando ejecutable solo por Players. */
    public static CommandAPICommand playerOnly(String name, PlayerCommandExecutor executor) {
        return new CommandAPICommand(name).executesPlayer(executor);
    }

    public static void register(JavaPlugin plugin, String name, org.bukkit.command.CommandExecutor executor) {
        PluginCommand command = plugin.getCommand(name);
        if (command != null) {
            command.setExecutor(executor);
        }
    }

    public static void register(JavaPlugin plugin, String name, org.bukkit.command.CommandExecutor executor, TabCompleter tabCompleter) {
        PluginCommand command = plugin.getCommand(name);
        if (command != null) {
            command.setExecutor(executor);
            command.setTabCompleter(tabCompleter);
        }
    }
}
