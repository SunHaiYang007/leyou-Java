package com.ly.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by HXin on 2019/4/13.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LySearch  {
    public static void main(String[] args) {
        SpringApplication.run(LySearch.class);
    }
}
