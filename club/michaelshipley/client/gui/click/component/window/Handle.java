// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.gui.click.component.window;

import java.awt.Color;
import java.util.Iterator;

import club.michaelshipley.client.gui.click.ClickGui;
import club.michaelshipley.client.gui.click.component.Component;
import club.michaelshipley.client.module.modules.view.ClientColor;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.RenderUtils;

public class Handle extends Component<Window>
{
    private static final float BORDER_WIDTH = 1.5f;
    private static final double SEPERATOR_HEIGHT = 2.0;
    public static final double TITLE_SCALE = 1.1;
    private static final int BACKGROUND_COLOR = -13290187;
    private static final int SEPERATOR_COLOR = -14803426;
    private String name;
    
    public Handle(final String name, final double x, final double y, final double width, final double height) {
        super(null, x, y, width, height);
        this.name = name;
    }
    
    public void draw(final int mouseX, final int mouseY, final boolean extended) {
        final int[] fillGradient = { new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB(), new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB(), RenderUtils.blend(new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB(), new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB(), 0.95f), RenderUtils.blend(new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB(), new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB(), 0.95f) };
        final int[] outlineGradient = { 0x7d24eb, 0x7d24eb, RenderUtils.blend(0x7d24eb, 0x7d24eb, 0.95f), RenderUtils.blend(0x7d24eb, 0x7d24eb, 0.95f) };
        RenderUtils.rectangleBorderedGradient(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), fillGradient, outlineGradient, 0.5);
        RenderUtils.rectangle(this.getX() + 1.0, this.getY() + 0.5, this.getX() + this.getWidth() - 1.0, this.getY() + 1.0, 553648127);
        ClientUtils.clientFont().drawStringWithShadow(this.name, this.getX() + 4, this.getY() + 4 + 1.5, -1);
    }
    
    @Override
    public void click(final int mouseX, final int mouseY, final int button) {
        for (final Window window : ClickGui.getInstance().getWindows()) {
            window.setDragging(false);
        }
        if (button == 0) {
            this.getParent().setStartOffset(new double[] { mouseX - this.getParent().getX(), mouseY - this.getParent().getY() });
            this.getParent().setDragging(true);
        }
        else if (button == 1) {
            this.getParent().setExtended(!this.getParent().isExtended());
        }
    }
    
    @Override
    public void drag(final int mouseX, final int mouseY, final int button) {
        if (button == 0) {
            this.getParent().drag(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void release(final int mouseX, final int mouseY, final int button) {
        if (button == 0) {
            for (final Window window : ClickGui.getInstance().getWindows()) {
                window.setDragging(false);
            }
        }
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
    }
    
    @Override
    public void keyPress(final int keyInt, final char keyChar) {
    }
}
