package club.michaelshipley.utils;

import java.awt.Font;

import club.michaelshipley.client.gui.font.MinecraftFontRenderer;

public final class FontManager {

    public static final MinecraftFontRenderer watermark = new MinecraftFontRenderer(new Font("Arial", 0, 36), true, true);
    public static final MinecraftFontRenderer text = new MinecraftFontRenderer(new Font("Arial", 0, 25), true, true);
    public static final MinecraftFontRenderer version = new MinecraftFontRenderer(new Font("Arial", 0, 14), true, true);
    public static final MinecraftFontRenderer array = new MinecraftFontRenderer(new Font("Arial", 0, 25), true, true);
}