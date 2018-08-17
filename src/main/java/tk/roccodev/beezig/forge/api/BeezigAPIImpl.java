package tk.roccodev.beezig.forge.api;


import tk.roccodev.beezig.forge.init.ClassUtils;

public interface BeezigAPIImpl {

    boolean isStaffMember();
    boolean onPacketReceived(int packetId, String data);
    void saveConfigData(Object[] data);
    public String getCAITeam();



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
        };
    }

}
