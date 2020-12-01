package com.xzh.user.controller;

import com.xzh.user.feign.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 向振华
 * @date 2020/12/01 12:00
 */
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/hi")
    public String hi() {
        return "Hello World! User";
    }

    @GetMapping("/product")
    public String product() {
        return productClient.hi();
    }
}