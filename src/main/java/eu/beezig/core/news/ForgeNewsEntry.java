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

package eu.beezig.core.news;

import eu.beezig.forge.ForgeMessage;

import java.util.Date;
import java.util.Map;

public class ForgeNewsEntry {
    public String author, title;
    public Date pubDate;
    public String content, link;
    public Map<String, Object> extra;
    public boolean persistent; // Persistent news will be pinned and always shown

    // BeezigForge render stuff
    private int x, y, width, height;
    private String clickHere;
    private boolean shown;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getChatComponent() {
        if(clickHere == null) clickHere = "§b[" + ForgeMessage.translate("btn.news.more.name") +  "]";
        return clickHere;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
