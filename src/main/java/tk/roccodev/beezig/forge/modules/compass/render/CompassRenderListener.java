package tk.roccodev.beezig.forge.modules.compass.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import tk.roccodev.beezig.forge.ActiveGame;

import java.awt.*;


public class CompassRenderListener {

    private int offsetAll = 0;
    private int offY = 0;

    private int centerX = 0;
    public static int width = 184;
    public static int height = 20;
    private int colorMarker = 0xFFFFFF;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post evt) {
        if (evt.type == RenderGameOverlayEvent.ElementType.ALL) {
            if (!ActiveGame.current().equalsIgnoreCase("bed")) return;
            if (Minecraft.getMinecraft().theWorld == null) return;
            drawCompass(new ScaledResolution(mc).getScaledWidth());
        }
    }

    private int normalize(int direction) {
        if (direction > 360) {
            direction %= 360;
        }
        while (direction < 0) {
            direction += 360;
        }
        return direction;
    }

    private void renderMarker() {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        GlStateManager.color((colorMarker >> 16 & 0xFF) / 255.0F, (colorMarker >> 8 & 0xFF) / 255.0F, (colorMarker & 0xFF) / 255.0F, 1.0F);

        worldrenderer.begin(6, DefaultVertexFormats.POSITION);
        worldrenderer.pos(centerX, offY + 3, 0.0D).endVertex();
        worldrenderer.pos(centerX + 3, offY, 0.0D).endVertex();
        worldrenderer.pos(centerX - 3, offY, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private static int cwidth = 500;
    private static int colorDirection = 0;
    private Minecraft mc = Minecraft.getMinecraft();
    private FontRenderer fr;

    public CompassRenderListener() {
        fr = mc.fontRendererObj;
    }

    private void drawCompass(int screenWidth) {
        int direction = normalize((int) this.mc.thePlayer.rotationYaw);
        offsetAll = (cwidth * direction / 360);
        int offX = 0;
        centerX = (screenWidth / 2 + offX);

        int tintMarker = 0;
        if (tintMarker != 0) {
            colorMarker = Color.HSBtoRGB(tintMarker / 100.0F, 1.0F, 1.0F);
        } else {
            colorMarker = -1;
        }
        int tintDirection = 0;
        if (tintDirection != 0) {
            colorDirection = Color.HSBtoRGB(tintDirection / 100.0F, 1.0F, 1.0F);
        } else {
            colorDirection = -1;
        }

        renderMarker();

        mc.theWorld.loadedEntityList.forEach((e) -> {
            if(e.hasCustomName()) return;
            if(!(e instanceof EntityArmorStand)) return;
            if(!(e.riddenByEntity instanceof EntityGiantZombie)) return;
            EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
            EntityGiantZombie zombie = (EntityGiantZombie) e.riddenByEntity;
            int color = zombie.getHeldItem().getMetadata();
            double yaw = (Math.atan2((e.posX - pl.posX), (e.posZ - pl.posZ)) * (180.0 / Math.PI));

            drawDirection("●", (int)Math.toDegrees(Math.atan2(pl.posX - e.posX, e.posZ - pl.posZ)), 4D, color);

        });

    }


    private void drawDirection(String dir, int degrees, double scale, int color2) {
        int offset = cwidth * degrees / 360 - offsetAll;
        if (offset > cwidth / 2) {
            offset -= cwidth;
        }
        if (offset < -cwidth / 2) {
            offset += cwidth;
        }
        double opacity = 1.0D - Math.abs(offset) / (width / 2.0D);
        if (opacity > 0.1D) {
            int defcolor = colorDirection & Colors.colors[color2];
            int color = defcolor | (int) (opacity * 255.0D) << 24;
            int posX = centerX + offset - (int) (this.fr.getStringWidth(dir) * scale / 2.0D);
            int posY = offY + height / 2 - (int) (this.fr.FONT_HEIGHT * scale / 2.0D);

            GL11.glEnable(3042);
            GL11.glPushMatrix();
            GL11.glTranslated(-posX * (scale - 1.0D), -posY * (scale - 1.0D), 0.0D);
            GL11.glScaled(scale, scale, 1.0D);


            this.fr.drawStringWithShadow(dir, posX, posY, color);

            GL11.glPopMatrix();
            GL11.glDisable(3042);
        }
    }

    private static class Colors {
        public static int[] colors = new int[]{0xFFFFFF, 0xFFAA00, 0xFF55FF, 0x55FFFF, 0xFFFF55,
            0x55FF55, 0xFF5555, 0x555555, 0xAAAAAA, 0x00AAAA, 0xAA00AA,
            0x0000AA, 0x4F321F, 0x00AA00, 0xAA0000, 0x000000 };
    }




}
