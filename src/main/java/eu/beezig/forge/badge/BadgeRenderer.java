/*
 * This file is part of BeezigForge.
 *
 * BeezigForge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigForge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigForge.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.forge.badge;

import eu.beezig.forge.api.BeezigAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;

public class BadgeRenderer {
    public static final int BADGE_SIZE = 255;
    private static final Gui dummyGui = new Gui();
    private ResourceLocation resourceLocation, fallback;
    private boolean compiled;
    private BufferedImage cachedImage;

    public static BadgeRenderer staticTexture(ResourceLocation loc) {
        BadgeRenderer renderer = new BadgeRenderer();
        renderer.resourceLocation = loc;
        renderer.fallback = loc;
        renderer.compiled = true;
        return renderer;
    }

    public static BadgeRenderer dynamicTexture(ResourceLocation loc, ResourceLocation fallback) {
        BadgeRenderer renderer = new BadgeRenderer();
        renderer.resourceLocation = loc;
        renderer.fallback = fallback;
        return renderer;
    }

    /**
     * Draws the 2D badge corresponding to the given user at the given position with the given size
     * @param uuid The user's UUID
     * @param x The badge's x coordinate
     * @param y The badge's y coordinate
     * @param size The badge's size. The Badge will always keep the same aspect ratio (currently 1:1)
     * @return Convenience value (for ASM injection) that defines the badge's width. Currently, this just returns size.
     */
    public static int renderBadge(UUID uuid, double x, double y, double size) {
        if (!((boolean) BeezigAPI.getSetting ("TABLIST_BADGES"))) {
            return 0;
        }
        int userRole = BeezigAPI.getUserRole(uuid);
        BadgeRenderer badge;
        Map<String, Object> overrides = BeezigAPI.getOverrides(uuid);
        if(overrides != null && overrides.containsKey("custom-badge.priority") &&
                ((int) overrides.get("custom-badge.priority")) >= userRole) {
            badge = BadgeService.getBadge((String) overrides.get("custom-badge.url"), uuid);
        } else {
            badge = BadgeService.getBadge(userRole);
        }
        if (badge == null) {
            return 0;
        }

        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(badge.render());
        double scaleFactor = size / (double) BADGE_SIZE;
        GL11.glScaled(scaleFactor, scaleFactor, 0.0D);
        dummyGui.drawTexturedModalRect((int) (x / scaleFactor), (int) (y / scaleFactor), 0, 0, BADGE_SIZE, BADGE_SIZE);
        GL11.glPopMatrix();
        return (int) size;
    }

    public static boolean shouldRenderTabBadge(UUID uuid) {
        return ((boolean) BeezigAPI.getSetting ("TABLIST_BADGES")) && BeezigAPI.getUserRole(uuid) != -1;
    }

    public static void renderBadge(ResourceLocation badge, float x, float y, float z, RenderManager renderer, int width) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(-renderer.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderer.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        double pos = -width - 10D;
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(badge);
        double factor = 8D / (double) BADGE_SIZE;
        GlStateManager.pushMatrix();
        GlStateManager.scale(factor, factor, 0D);
        dummyGui.drawTexturedModalRect((int)(pos / factor),(int)(-0.5D/factor), 0, 0, 255, 255);
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public ResourceLocation render() {
        if(compiled) return resourceLocation;
        if(cachedImage == null) return fallback;
        compile();
        return resourceLocation;
    }

    public void setCachedImage(BufferedImage cachedImage) {
        this.cachedImage = cachedImage;
    }

    private void compile() {
        Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, new DynamicTexture(cachedImage));
    }
}
