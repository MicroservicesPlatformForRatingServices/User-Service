package com.project.user.service.UserService.configurations;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BasicConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplateBean(){
        return new RestTemplate();
    }

}
