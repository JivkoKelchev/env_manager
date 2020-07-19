package com.jivko.env_manager.Services;

import java.util.HashMap;

public class ServiceContainer {
    private HashMap<String, Service> container;

    public ServiceContainer() {
        this.container = new HashMap<String, Service>();
        this.containerDependencyConfiguration();
    }

    public Service getService(String serviceName) {
        return this.container.get(serviceName);
    }


    private void containerDependencyConfiguration() {
        XammpControllerService xammpControllerService = new XammpControllerService();
        ModelService modelService = new ModelService();
        DirectoryService directoryService = new DirectoryService();

        this.container.put("model", modelService);
        this.container.put("control", xammpControllerService);
        this.container.put("dir", directoryService);

    }


}
