package ru.osipovmaksim.BurgerApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("login").setViewName("login");
        registry.addViewController("register").setViewName("register");
        registry.addViewController("api/admin").setViewName("admin");
        registry.addViewController("api/user").setViewName("user2");
        registry.addViewController("").setViewName("index");
    }
}
