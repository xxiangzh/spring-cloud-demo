package com.xzh.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 向振华
 * @date 2020/12/01 12:00
 */
@FeignClient(name = "order-service", path = "/order-service")
public interface OrderClient {

    @GetMapping("/hi")
    String hi();
}