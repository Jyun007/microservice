package com.jyun.provider.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class ProviderController {

    private static final Logger logger = Logger.getLogger("com.jyun.provider.controller.ProviderController");

    @Qualifier("eurekaRegistration")
    @Autowired
    private Registration registration;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/index")
    public String index(){
        ServiceInstance instance = serviceInstance();
        logger.info("host:"+instance.getHost()+"  serviceId:"+instance.getServiceId());
        return "hello world";
    }

    public ServiceInstance serviceInstance() {
        List<ServiceInstance> list = discoveryClient.getInstances(registration.getServiceId());
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
