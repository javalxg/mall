package com.wondersgroup.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/230:44
 */
@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping()
    public String  test(){
        return "test-helloworld";
    }
}
