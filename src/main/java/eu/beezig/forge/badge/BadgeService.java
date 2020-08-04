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

import eu.beezig.forge.BeezigForgeMod;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BadgeService {
    private static Map<Integer, BadgeRenderer> badges = new ConcurrentHashMap<>();
    private static ResourceLocation fallback = new ResourceLocation("beezig/badges/user-256.png");
    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    private static void loadDefaults() {
        badges.put(100, BadgeRenderer.staticTexture(new ResourceLocation("beezig/badges/developer-256.png")));
        badges.put(51, BadgeRenderer.staticTexture(new ResourceLocation("beezig/badges/contributor-256.png")));
        badges.put(50, BadgeRenderer.staticTexture(new ResourceLocation("beezig/badges/translator-256.png")));
        badges.put(0, BadgeRenderer.staticTexture(fallback));
    }

    public static BadgeRenderer getBadge(int role) {
        if(badges.size() == 0) loadDefaults();
        if(role == -1) return null;
        BadgeRenderer res = badges.get(role);
        if(res == null) {
            badges.put(role, res = BadgeRenderer.dynamicTexture(new ResourceLocation("beezig/badges/role-" + role + ".png"), fallback));
            downloadBadge(role, res);
            return res;
        }
        return res;
    }

    private static void downloadBadge(int role, BadgeRenderer apply) {
        executor.execute(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("https://static.beezig.eu/badges/" + role + ".png");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "BeezigForge/" + BeezigForgeMod.VERSION);
                BufferedImage img = ImageIO.read(connection.getInputStream());
                apply.setCachedImage(img);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
            }
        });
    }
}
