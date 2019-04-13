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

package eu.beezig.forge.api;


import eu.beezig.forge.init.ClassUtils;

import java.util.List;

public interface BeezigAPIImpl {

    boolean isStaffMember();
    boolean onPacketReceived(int packetId, String data);
    void saveConfigData(Object[] data);
    String getCAITeam();
    boolean getSettingValue(String setting);
    String getRankString(String title, String mode);
    String getTIMVRank(String title, long points);
    String getBedwarsMode();
    String getConfigPath();
    void sendTutorial(String key);
    void setTIMVMessages(List<String> messages);
    List<String> getTIMVMessages();
    void setSetting(String setting, boolean value);
    boolean isHive();

    static BeezigAPIImpl fromObject(final Object from) {
        return new BeezigAPIImpl() {
            @Override
            public boolean isStaffMember() {

               return (boolean) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "isStaffMember"));

            }

            @Override
            public boolean onPacketReceived(int packetId, String data) {
                return (boolean) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "onPacketReceived", int.class, String.class), packetId, data);
            }

            @Override
            public void saveConfigData(Object[] data) {
                ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "saveConfigData", Object.class), new ArrayContainer(data));
            }

            @Override
            public String getCAITeam() {
                return (String) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "getCAITeam"));
            }

            @Override
            public boolean getSettingValue(String setting) {
                return (boolean) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "getSettingValue", String.class), setting);
            }

            @Override
            public String getRankString(String title, String mode) {
                return (String) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "getRankString", String.class, String.class), title, mode);
            }

            @Override
            public String getTIMVRank(String title, long points) {
                return (String) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "getTIMVRank", String.class, long.class), title, points);
            }

            @Override
            public String getBedwarsMode() {
                return (String) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "getBedwarsMode"));
            }

            @Override
            public String getConfigPath() {
                return (String) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "getConfigPath"));
            }

            @Override
            public void sendTutorial(String key) {
                ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "sendTutorial", String.class), key);
            }

            @Override
            public void setTIMVMessages(List<String> messages) {
                ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "setTIMVMessages", List.class), messages);
            }

            @Override
            public List<String> getTIMVMessages() {
                return (List<String>) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "getTIMVMessages"));
            }

            @Override
            public void setSetting(String setting, boolean value) {
                ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "setSetting", String.class, boolean.class), setting, value);
            }

            @Override
            public boolean isHive() {
                return (boolean) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "isHive"));
            }
        };
    }

}
