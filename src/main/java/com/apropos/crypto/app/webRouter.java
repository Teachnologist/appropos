package com.apropos.crypto.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class webRouter {
    private String access_code = null;

    @RequestMapping(value = "/")
    public String index(Model model){

        if(access_code != null){
            model.addAttribute("test","This is the authenticated page");
            return "authenticated/index";
        }

        model.addAttribute("test","This is the public page");
        return "public/index";
    }
}
