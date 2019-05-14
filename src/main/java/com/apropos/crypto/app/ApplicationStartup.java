package com.apropos.crypto.app;

import com.apropos.classes.coinbaseProBackgroundTasks1;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


    @Component
    public class ApplicationStartup
            implements ApplicationListener<ApplicationReadyEvent> {

        /**
         * This event is executed as late as conceivably possible to indicate that
         * the application is ready to service requests.
         */
        @Override
        public void onApplicationEvent(final ApplicationReadyEvent event) {

          //  coinbaseProBackgroundTasks1.start("https://api.coinbase.com/v2");
        }

    }

