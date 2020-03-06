package com.jyun.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
public class ConsumerController {

    private static final Logger logger = Logger.getLogger("com.jyun.consumer.controller.ConsumerController");

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/index")
    public String consumer(){

        return restTemplate.getForObject("http://provider/index", String.class);
    }

    @GetMapping("/info")
    public void log(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider");
        logger.info(serviceInstance.getServiceId()+"  "+serviceInstance.getHost()+" "+serviceInstance.getPort());
    }

}
