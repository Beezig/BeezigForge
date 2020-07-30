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
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;

public class BadgeRenderer {
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
