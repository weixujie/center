package net.tiangu.center;

import com.jfinal.config.*;
import net.tiangu.center.commons.CenterConstants;
import net.tiangu.center.controller.rest.FlowRest;

public class Config
        extends JFinalConfig {
    public void configConstant(Constants me) {
        me.setDevMode(CenterConstants.develop);
    }

    public void configRoute(Routes me) {
        me.add("/rest/flow", FlowRest.class);
    }

    public void configPlugin(Plugins me) {
    }

    public void configInterceptor(Interceptors me) {
    }

    public void configHandler(Handlers me) {
    }
}
