package com.alibo.week07.controller;

import com.alibo.week07.servcie.IWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: alibobo
 * create: 2020-12-01 21:37
 * description: 写
 **/
@RestController
public class WriteController {

    @Autowired
    private IWriteService writeService;

    @RequestMapping("write")
    public Object write(String... data)  {
        return writeService.write(data);
    }
}
