package tk.roccodev.beezig.forge.api;


import tk.roccodev.beezig.forge.init.ClassUtils;

public interface BeezigAPIImpl {

    boolean isStaffMember();
    boolean onPacketReceived(int packetId, String data);
    void saveConfigData(Object[] data);
    String getCAITeam();
    boolean getSettingValue(String setting);
    String getRankString(String title, String mode);
    String getTIMVRank(String title, long points);
    String getBedwarsMode();
    void sendTutorial(String key);



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
            public void sendTutorial(String key) {
                ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "sendTutorial", String.class), key);
            }
        };
    }

}
