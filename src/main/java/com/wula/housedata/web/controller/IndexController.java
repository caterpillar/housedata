package com.wula.housedata.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lishaohua on 2017/6/29.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping()
    public String index() {
        return "redirect:/home/index";
    }
}
