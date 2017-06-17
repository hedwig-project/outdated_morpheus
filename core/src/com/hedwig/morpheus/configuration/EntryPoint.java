package com.hedwig.morpheus.configuration;

import com.hedwig.morpheus.business.Morpheus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Created by hugo. All rights reserved.
 */

// TODO : Make it a configuration file, providing beans

@Component
@ComponentScan("com.hedwig.morpheus")
public class EntryPoint implements ApplicationRunner {

    private final Morpheus morpheus;

    @Autowired
    public EntryPoint(Morpheus morpheus) {
        this.morpheus = morpheus;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        assert morpheus != null;

        morpheus.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class, args);
    }
}
