package tk.roccodev.beezig.forge.api;


import tk.roccodev.beezig.forge.init.ClassUtils;

public interface BeezigAPIImpl {

    public boolean isStaffMember();
    public boolean onPacketReceived(int packetId, String data);



    public static BeezigAPIImpl fromObject(final Object from) {
        return new BeezigAPIImpl() {
            @Override
            public boolean isStaffMember() {

               return (boolean) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "isStaffMember"));

            }

            @Override
            public boolean onPacketReceived(int packetId, String data) {
                return (boolean) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "onPacketReceived", int.class, String.class), packetId, data);
            }
        };
    }

}
