// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.Render3DEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.RenderUtils;
import club.michaelshipley.utils.SallosRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
@Mod(shown = false)
public class ESP extends Module
{
    @Option.Op
    private boolean players;
    @Option.Op
    private boolean monsters;
    @Option.Op
    private boolean animals;
    @Option.Op(name = "2D ESP")
    private boolean twoDimension;
    @Option.Op(min = 0.1, max = 1.0, increment = 0.025, name = "Hue")
    private double hue;
    @Option.Op(name = "Custom color")
    private boolean customkkk;
    private int state;
    private static float[] rainbowArray;
    
    public ESP() {
        super();
        this.players = true;
    }
    
    public void onEnable() {
        super.enable();
    }
    

    public void onDisable() {
        super.disable();
    }
    
    @EventTarget
    private void onRender3D(final Render3DEvent event) {
        if (!this.twoDimension) {
            for (final Object o : ClientUtils.world().loadedEntityList) {
                if (o instanceof EntityLivingBase) {
                    final Object o2 = o;
                    ClientUtils.mc();
                    if (o2 == Minecraft.thePlayer) {
                        continue;
                    }
                    final EntityLivingBase entity = (EntityLivingBase)o;
                    if (!this.checkValidity(entity)) {
                        continue;
                    }
                    final double color = java.awt.Color.HSBtoRGB((float)this.hue, 0.9f, 0.9f);
                    if (!this.twoDimension) {
                        RenderUtils.drawEsp(entity, event.partialTicks, -1, this.customkkk ? ((entity.hurtTime != 0) ? -1 : ((int)(color + 1.17440512E9))) : ((entity.hurtTime != 0) ? -1 :-1));
                    }
                    else {
                        if (!entity.isEntityAlive()) {
                            return;
                        }
                        continue;
                    }
                }
            }
        }
        else {
            for (final Object o : ClientUtils.world().loadedEntityList) {
                if (o instanceof EntityLivingBase) {
                    final Object o3 = o;
                    ClientUtils.mc();
                    if (o3 == Minecraft.thePlayer) {
                        continue;
                    }
                    final EntityLivingBase entity = (EntityLivingBase)o;
                    if (entity == ClientUtils.player()) {
                        continue;
                    }
                    if (!this.checkValidity(entity)) {
                        continue;
                    }
                    if (!entity.isEntityAlive()) {
                        continue;
                    }
                    final float posX = (float)((float)entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ClientUtils.mc().timer.renderPartialTicks);
                    final float posY = (float)((float)entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ClientUtils.mc().timer.renderPartialTicks);
                    final float posZ = (float)((float)entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ClientUtils.mc().timer.renderPartialTicks);
                    final float distance = ClientUtils.player().getDistanceToEntity(entity);
                    final float health = entity.getHealth();
                    if (!FriendManager.isFriend(entity.getCommandSenderEntity().getName())) {
                        final float percent = health / 2.0f;
                        if (percent >= 6.0f) {
                            draw2D(entity, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, 0.1f, 1.0f, 0.1f, 255.0f);
                        }
                        if (percent < 6.0f) {
                            draw2D(entity, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, 1.0f, 0.5f, 0.0f, 255.0f);
                        }
                        if (percent >= 3.0f) {
                            continue;
                        }
                        draw2D(entity, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, 1.0f, 0.0f, 0.0f, 255.0f);
                    }
                    else {
                        draw2D(entity, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, 0.0f, 0.7f, 0.8f, 255.0f);
                    }
                }
            }
        }
    }
    
    private boolean checkValidity(final EntityLivingBase entity) {
        if (entity instanceof EntityOtherPlayerMP) {
            return this.players;
        }
        return (this.monsters && entity instanceof EntityMob) || (this.animals && entity instanceof EntityAnimal) || (this.animals && entity instanceof EntityBat);
    }
    
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void drawRect(final Rectangle rectangle, final int color) {
       SallosRender.R2DUtils.drawBorderedRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color, -1);
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1, final int color) {
        enableGL2D();
        glColor(color);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }
    
    public static void drawBorderedRect(final float x, final float y, final float x1, final float y1, final float width, final int internalColor, final int borderColor) {
        enableGL2D();
        glColor(internalColor);
        drawRect(x + width, y + width, x1 - width, y1 - width);
        glColor(borderColor);
        drawRect(x + width, y, x1 - width, y + width);
        drawRect(x, y, x + width, y1);
        drawRect(x1 - width, y, x1, y1);
        drawRect(x + width, y1 - width, x1 - width, y1);
        disableGL2D();
    }
    
    public static void drawBorderedRect(float x, float y, float x1, float y1, final int insideC, final int borderC) {
        enableGL2D();
        x *= 2.0f;
        x1 *= 2.0f;
        y *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x, y, y1, borderC);
        drawVLine(x1 - 1.0f, y, y1, borderC);
        drawHLine(x, x1 - 1.0f, y, borderC);
        drawHLine(x, x1 - 2.0f, y1 - 1.0f, borderC);
        drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawBorderedRectReliant(final float x, final float y, final float x1, final float y1, final float lineWidth, final int inside, final int border) {
        enableGL2D();
        drawRect(x, y, x1, y1, inside);
        glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }
    
    public static void drawGradientBorderedRectReliant(final float x, final float y, final float x1, final float y1, final float lineWidth, final int border, final int bottom, final int top) {
        enableGL2D();
        drawGradientRect(x, y, x1, y1, top, bottom);
        glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }
    
    public static void drawRoundedRect(float x, float y, float x1, float y1, final int borderC, final int insideC) {
        enableGL2D();
        x *= 2.0f;
        y *= 2.0f;
        x1 *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x, y + 1.0f, y1 - 2.0f, borderC);
        drawVLine(x1 - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
        drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
        drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
        drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
        drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
        drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawBorderedRect(final Rectangle rectangle, final float width, final int internalColor, final int borderColor) {
        final float x = rectangle.x;
        final float y = rectangle.y;
        final float x2 = rectangle.x + rectangle.width;
        final float y2 = rectangle.y + rectangle.height;
        enableGL2D();
        glColor(internalColor);
       SallosRender.R2DUtils.drawRect(x + width, y + width, x2 - width, y2 - width);
        glColor(borderColor);
        drawRect(x + 1.0f, y, x2 - 1.0f, y + width);
        drawRect(x, y, x + width, y2);
        drawRect(x2 - width, y, x2, y2);
        drawRect(x + 1.0f, y2 - width, x2 - 1.0f, y2);
        disableGL2D();
    }
    
    public static void drawGradientRect(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(topColor);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        glColor(bottomColor);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
    }
    
    public static void drawGradientHRect(final float x, final float y, final float x1, final float y1, final int topColor, final int bottomColor) {
        enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        glColor(topColor);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        glColor(bottomColor);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        disableGL2D();
    }
    
    public static void drawGradientRect(final double x, final double y, final double x2, final double y2, final int col1, final int col2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        glColor(col1);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        glColor(col2);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void drawGradientBorderedRect(final double x, final double y, final double x2, final double y2, final float l1, final int col1, final int col2, final int col3) {
        enableGL2D();
        GL11.glPushMatrix();
        glColor(col1);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        drawGradientRect(x, y, x2, y2, col2, col3);
        disableGL2D();
    }
    
   
    
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void glColor(final float alpha, final int redRGB, final int greenRGB, final int blueRGB) {
        final float red = 0.003921569f * redRGB;
        final float green = 0.003921569f * greenRGB;
        final float blue = 0.003921569f * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void drawStrip(final int x, final int y, final float width, final double angle, final float points, final float radius, final int color) {
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0.0);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(width);
        if (angle > 0.0) {
            GL11.glBegin(3);
            for (int i = 0; i < angle; ++i) {
                final float a = (float)(i * (angle * 3.141592653589793 / points));
                final float xc = (float)(Math.cos(a) * radius);
                final float yc = (float)(Math.sin(a) * radius);
                GL11.glVertex2f(xc, yc);
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin(3);
            for (int i = 0; i > angle; --i) {
                final float a = (float)(i * (angle * 3.141592653589793 / points));
                final float xc = (float)(Math.cos(a) * -radius);
                final float yc = (float)(Math.sin(a) * -radius);
                GL11.glVertex2f(xc, yc);
            }
            GL11.glEnd();
        }
        disableGL2D();
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }
    
    public static void drawHLine(float x, float y, final float x1, final int y1) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
    }
    
    public static void drawVLine(final float x, float y, float x1, final int y1) {
        if (x1 < y) {
            final float var5 = y;
            y = x1;
            x1 = var5;
        }
        drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
    }
    
    public static void drawHLine(float x, float y, final float x1, final int y1, final int y2) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1, final float r, final float g, final float b, final float a) {
        enableGL2D();
        GL11.glColor4f(r, g, b, a);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
    }
    
    public static void drawCircle(float cx, float cy, float r, final int num_segments, final int c) {
        r *= 2.0f;
        cx *= 2.0f;
        cy *= 2.0f;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        final float theta = (float)(6.2831852 / num_segments);
        final float p = (float)Math.cos(theta);
        final float s = (float)Math.sin(theta);
        float x = r;
        float y = 0.0f;
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f(x + cx, y + cy);
            final float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawFullCircle(int cx, int cy, double r, final int c) {
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360; ++i) {
            final double x = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }
    
    public static void drawSmallString(final String s, final int x, final int y, final int color) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft();
        Minecraft.fontRendererObj.drawString(s, x * 2, y * 2, color);
        GL11.glPopMatrix();
    }
    
    public static void drawLargeString(final String text, int x, final int y, final int color) {
        x *= 2;
        GL11.glPushMatrix();
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        Minecraft.getMinecraft();
        Minecraft.fontRendererObj.drawString(text, x, y, color);
        GL11.glPopMatrix();
    }
    
    public static ScaledResolution getScaledResolution() {
        return getScaledResolution();
    }
    
    public static void draw2D(final Entity e, final double posX, final double posY, final double posZ, final float alpha, final float red, final float green, final float blue) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        drawOutlineRect(-6.2f, -19.2f, 6.2f, 2.2f, Integer.MIN_VALUE);
        drawOutlineRect(-5.8f, -18.8f, 5.8f, 1.8f, Integer.MIN_VALUE);
        drawOutlinedRect(-6.0f, -19.0f, 6.0f, 2.0f, alpha, red, green, blue);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.popMatrix();
    }
    
    public static void drawRectColor(final double d, final double e, final double f2, final double f3, final float alpha, final float red, final float green, final float blue) {
        GlStateManager.enableBlend();
        enableGL2D();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushMatrix();
        GL11.glColor4f(alpha, red, green, blue);
        GL11.glBegin(7);
        GL11.glVertex2d(f2, e);
        GL11.glVertex2d(d, e);
        GL11.glVertex2d(d, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glEnd();
        disableGL2D();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }
    
    public static void drawOutlineRect(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final int color) {
        drawRect(drawX, drawY, drawWidth, drawY + 0.5f, color);
        drawRect(drawX, drawY + 0.5f, drawX + 0.5f, drawHeight, color);
        drawRect(drawWidth - 0.5f, drawY + 0.5f, drawWidth, drawHeight - 0.5f, color);
        drawRect(drawX + 0.5f, drawHeight - 0.5f, drawWidth, drawHeight, color);
    }
    
    public static void drawOutlinedRect(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final float alpha, final float red, final float green, final float blue) {
        drawRectColor(drawX, drawY, drawWidth, drawY + 0.5f, alpha, red, green, blue);
        drawRectColor(drawX, drawY + 0.5f, drawX + 0.5f, drawHeight, alpha, red, green, blue);
        drawRectColor(drawWidth - 0.5f, drawY + 0.5f, drawWidth, drawHeight - 0.5f, alpha, red, green, blue);
        drawRectColor(drawX + 0.5f, drawHeight - 0.5f, drawWidth, drawHeight, alpha, red, green, blue);
    }
}
