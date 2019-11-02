package com.curtjenk.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfiguration {
// @Bean
// public WebMvcConfigurer corsConfigurer() {
// return new WebMvcConfigurer() {
// @Override
// public void addCorsMappings(CorsRegistry registry) {
// System.out.println(" --------- configure cors ---------------");
// registry.addMapping("/**").allowedOrigins("http://xxxlocalhost:8080");
// }
// };
// }
// }