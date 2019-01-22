package eu.beezig.forge.gui.briefing.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageDownloader {

    private HashMap<String, ResourceLocation> loadedImages = new HashMap<>();
    private List<String> loadingImages = new ArrayList<>();
    public static ImageDownloader instance = new ImageDownloader();

    public void drawImageUrl(String url, String fileName, final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight, int limit) {
        if (url != null) {
            ResourceLocation resourceLocation = this.loadedImages.get(fileName);
            if (resourceLocation == null) {
                resourceLocation = this.downloadImageFromURL(fileName, url);
                this.loadedImages.put(fileName, resourceLocation);
            }
            if(loadingImages.contains(url)) {
                resourceLocation = new ResourceLocation("beezigforge/gui/steve.png");
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
            if(limit < y)
            TabRenderUtils.getInstance().drawTexture(x, y, imageWidth, imageHeight, maxWidth, maxHeight);
        }
    }

    private ResourceLocation downloadImageFromURL(final String location, final String url) {
        final ResourceLocation resourceLocation = new ResourceLocation("bforgecache/" + location);
        this.loadingImages.add(url);
        new Thread(() -> {
            try {
                if (url != null && !url.isEmpty()) {
                    final URL u = new URL(url);
                    final HttpURLConnection connection = (HttpURLConnection)u.openConnection();
                    connection.setRequestProperty("User-Agent", "BeezigForge");
                    final BufferedImage read = ImageIO.read(connection.getInputStream());
                    if (read != null) {
                        Minecraft.getMinecraft().addScheduledTask(() -> {
                            final DynamicTexture texture = new DynamicTexture(read);
                            Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, texture);
                            ImageDownloader.this.loadingImages.remove(url);
                        });
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return resourceLocation;
    }
}
