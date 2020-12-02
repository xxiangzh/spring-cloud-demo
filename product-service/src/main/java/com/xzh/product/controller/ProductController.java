package com.xzh.product.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 向振华
 * @date 2020/12/01 12:00
 */
@RestController
@RequestMapping
public class ProductController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hi")
    public String hi() {
        return "Hello World! Product " + port;
    }
}