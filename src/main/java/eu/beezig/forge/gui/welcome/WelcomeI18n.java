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

package eu.beezig.forge.gui.welcome;

import eu.beezig.forge.ForgeMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WelcomeI18n {
    private static final Pattern COLOR_REGEX = Pattern.compile("\\$");

    public static String colorMessage(WelcomeGuiStep step, String key, String color, Object... fmt) {
        String translated = ForgeMessage.translate("tut." + step.getTranslationKey() + "." + key, fmt);
        Matcher matcher = COLOR_REGEX.matcher(translated);
        StringBuffer builder = new StringBuffer();
        int match = 0;
        while(matcher.find()) {
            matcher.appendReplacement(builder, (match++ % 2) == 0 ? color : "§r");
        }
        matcher.appendTail(builder);
        return builder.toString();
    }

    public static String button(WelcomeGuiStep step, String key, boolean predicate) {
        return ForgeMessage.translateOnOff("tut." + step.getTranslationKey() + "." + key, predicate);
    }

    public static String title(WelcomeGuiStep step) {
        return title(step, "§3");
    }

    public static String title(WelcomeGuiStep step, String color) {
        if("beezig".equals(step.getTranslationKey())) return color + "Beezig";
        return color + ForgeMessage.translate("tut." + step.getTranslationKey() + ".name");
    }
}
