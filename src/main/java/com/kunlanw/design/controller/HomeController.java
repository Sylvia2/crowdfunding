package com.kunlanw.design.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }


    @RequestMapping(value = "/manage",method = RequestMethod.GET)
    public String manage(){
        return "manage";
    }

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    public String data(){
        return "data";
    }


}