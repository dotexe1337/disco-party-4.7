// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.event.events;

import club.michaelshipley.event.Event;

public class RenderTextEvent extends Event
{
    private int width;
    private int height;
    
    public RenderTextEvent(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
}
