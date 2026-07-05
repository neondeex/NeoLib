package me.neondeex.neolib.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class NeoText {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    private NeoText() {
    }

    public static Component component(String text) {
        String safe = text == null ? "" : text;
        Component component;
        if (safe.contains("<") && safe.contains(">")) {
            component = MINI_MESSAGE.deserialize(safe);
        } else {
            component = safe.indexOf('&') >= 0 ? LEGACY.deserialize(safe) : MINI_MESSAGE.deserialize(safe);
        }
        return component.decoration(TextDecoration.ITALIC, false);
    }

}
