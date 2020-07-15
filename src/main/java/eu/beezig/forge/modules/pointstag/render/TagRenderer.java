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

package eu.beezig.forge.modules.pointstag.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TagRenderer {

    private final Gui dummyGui = new Gui();
    private static final ResourceLocation BADGE_DEV = new ResourceLocation("beezig/badges/developer-256.png");
    private static final ResourceLocation BADGE_TRANSLATOR = new ResourceLocation("beezig/badges/translator-256.png");
    private static final ResourceLocation BADGE_USER = new ResourceLocation("beezig/badges/user-256.png");

    public void renderNameAndBadge(String textToRender, int role, PointsTagRenderListener.RenderData data, double offset) {
        ResourceLocation badge = null;
        if(role == 1) badge = BADGE_USER;
        else if(role == 2) badge = BADGE_TRANSLATOR;
        else if(role == 3) badge = BADGE_DEV;
        double tagY = data.y + data.player.height + 0.5 + offset;
        FontRenderer fontRenderer = data.renderer.getFontRendererFromRenderManager();
        RenderManager renderer = data.renderer.getRenderManager();
        int textSemiWidth = fontRenderer.getStringWidth(textToRender) / 2;

        if(badge != null)
        renderBadge(badge, (float)data.x, (float)tagY, (float)data.z, renderer, textSemiWidth);

        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) data.x, (float) tagY, (float) data.z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-renderer.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderer.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int i = 0;

        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(-textSemiWidth - 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(-textSemiWidth - 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(textSemiWidth + 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(textSemiWidth + 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontRenderer.drawString(textToRender, -textSemiWidth, 0, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRenderer.drawString(textToRender, -textSemiWidth, 0, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void renderBadge(ResourceLocation badge, float x, float y, float z, RenderManager renderer, int width) {
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
        double factor = 8D / 255D;
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
}
