package com.aironman.demo.springcloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// this should be the same than application.yml spring.application.name
@FeignClient("demo-kafka-elastic")
public interface TestFeign {
    
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String doAlive();
}
