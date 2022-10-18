package com.kob.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description SpringSecurity的配置类
 * @date 2022/10/18 10:08:38
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * @author fckey
     * @date 2022/10/18 10:09
     * @description 设置密码加密器
     * @return org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}