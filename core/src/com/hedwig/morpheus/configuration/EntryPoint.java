package com.hedwig.morpheus.configuration;

import com.hedwig.morpheus.business.Morpheus;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Created by hugo. All rights reserved.
 */

// TODO : Make it a configuration file, providing beans

@Component
@ComponentScan("com.hedwig.morpheus")
public class EntryPoint implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EntryPoint.class, args);
        Morpheus morpheus = context.getBean(Morpheus.class);
        morpheus.start();
    }
}
