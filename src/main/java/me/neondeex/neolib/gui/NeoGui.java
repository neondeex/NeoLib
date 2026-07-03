package me.neondeex.neolib.gui;

import me.neondeex.neolib.text.NeoText;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class NeoGui {

    private NeoGui() {}

    public static void open(Player player, Gui gui, Component title) {
        Window.mergedBuilder()
                .setTitle(title)
                .setGui(gui)
                .open(player);
    }

    public static void open(Player player, Gui gui, String title) {
        Window.mergedBuilder()
                .setTitle(title)
                .setGui(gui)
                .open(player);
    }
}
