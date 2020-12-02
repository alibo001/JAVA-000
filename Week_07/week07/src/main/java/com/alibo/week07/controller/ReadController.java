package com.alibo.week07.controller;

import com.alibo.week07.servcie.IReadServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: alibobo
 * create: 2020-12-01 23:06
 * description: 读controller
 **/
@RestController
public class ReadController {

    @Autowired
    private IReadServcie readServcie;

    @RequestMapping("read")
    public Object read(){
        return readServcie.read();
    }



}
