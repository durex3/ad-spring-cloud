package com.durex.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 广告投放系统
 * @author gelong
 * @date 2019/11/5 23:30
 */
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
public class SponsorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SponsorApplication.class, args);
    }
}
