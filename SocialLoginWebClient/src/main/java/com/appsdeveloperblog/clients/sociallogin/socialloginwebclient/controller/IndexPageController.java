package com.appsdeveloperblog.clients.sociallogin.socialloginwebclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : zikoz
 * @created : 24 Jul, 2021
 */

@Controller
public class IndexPageController {

    //@GetMapping(path ="/")  ---todo not protected
    @GetMapping
    public String displayIndexPage(Model model){
        return "index";
    }
}
