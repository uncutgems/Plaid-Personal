package com.application.mintplaid.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Getter
@Component
public class Constant {
    // Sandbox Environment
    public static final String localDevelopment = "http://localhost:8080";

    // Production Environment
    public static final String developmentEnv = "https://";

    public static final String appName = "PlaidPersonal";
    public static final List<String> countries = Arrays.asList("US", "CA");
    public static final List<String> products = List.of("transactions");

}

