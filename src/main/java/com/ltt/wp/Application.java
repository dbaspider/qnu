package com.ltt.wp;

import com.ltt.wp.database.DbPool;
import com.ltt.wp.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        LOG.info("APPLICATION FINISHED");
    }
 
    @Override
    public void run(String... args) {
        LOG.info("begin EXECUTING : command line runner");
 
        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }

        if (!DbPool.initDb()) {
            return;
        }

        ThreadUtils.sleep(1);

        DbPool.closeDb();
        LOG.info("end EXECUTING : command line runner");
    }
}