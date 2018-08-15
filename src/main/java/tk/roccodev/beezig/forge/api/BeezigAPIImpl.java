package tk.roccodev.beezig.forge.api;


import tk.roccodev.beezig.forge.init.ClassUtils;

public interface BeezigAPIImpl {

    public boolean isStaffMember();
    


    public static BeezigAPIImpl fromObject(final Object from) {
        return new BeezigAPIImpl() {
            @Override
            public boolean isStaffMember() {

               return (boolean) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "isStaffMember"));

            }
        };
    }

}
