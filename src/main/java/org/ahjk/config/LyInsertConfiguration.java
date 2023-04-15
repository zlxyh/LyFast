package org.ahjk.config;


import org.ahjk.properties.LyProperties;
import org.ahjk.util.LyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({LyProperties.class})
public class LyInsertConfiguration {

    @Autowired
    private LyProperties properties;


    @Bean
    public LyUtils lyUtils() {
        System.out.println("LyFast --> 初始化开始！");
        return new LyUtils(properties);
    }

}
