package com.apropos.crypto.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {

    private static String host;

    private static int port;

    private static String username;

    private static String password;

    public static String getHost() {
        return host;
    }

    @Value("${spring.mail.host}")
    public void setHost(String host) {
        this.host = host;
    }

    public static Integer getPort() {
        return port;
    }

    @Value("${spring.mail.port}")
    public void setPort(Integer port) {
        this.port = port;
    }

    public static String getUsername() {
        return username;
    }

    @Value("${spring.mail.username}")
    public void setUsername(String username) {
        System.out.println("USERNAME: "+username);this.username = username;
    }

    public static String getPassword() { return password; }

    @Value("${spring.mail.password}")
    public void setPassword(String password) {
        this.password = password;
    }
}
