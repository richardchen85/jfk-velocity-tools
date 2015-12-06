package com.baidu.fis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cdrd-clq on 2015/12/2.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/page")
    public String velocity() {
        return "page/index";
    }
}
