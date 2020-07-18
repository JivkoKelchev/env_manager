package com.jivko.env_manager.Services;

import java.util.HashMap;

public class ServiceContainer {
    private HashMap<String, Object> container;

    public ServiceContainer() {
        this.container = new HashMap<String, Object>();
        this.containerDependencyConfiguration();
    }

    public Object getService(String serviceName) {
        return this.container.get(serviceName);
    }


    private void containerDependencyConfiguration() {
        XammpControllerService xammpControllerService = new XammpControllerService();
        ModelService modelService = new ModelService();

        this.container.put("model", modelService);
        this.container.put("control", xammpControllerService);

    }


}
