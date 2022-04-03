package com.xxy.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
* @param:
* @return:
* @author: xxy
* @date: 2022/4/3
* @description: 全局异常处理器
*/
@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(Exception.class)
    public Map globalExceptionController(Exception e) {
        Map<String,Object> map = new HashMap<>();
        String message = e.getMessage();
        if (message == null || message.equals("")) {
            message = "服务器内部出错";
        }
        map.put("code","5001");
        map.put("message",message);
        return map;
    }

}
