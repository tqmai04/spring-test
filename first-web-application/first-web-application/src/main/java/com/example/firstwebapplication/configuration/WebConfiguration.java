package com.example.firstwebapplication.configuration;


import com.example.firstwebapplication.converters.RdfModelConverter;
import com.example.firstwebapplication.converters.StringConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@Configuration
@EnableWebMvc
@ComponentScan("com.example.firstwebapplication.controller")
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new RdfModelConverter());
        converters.add(new StringConverter());
        //converters.add(new MappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}

