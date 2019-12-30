package com.durex.ad.runner;

import com.durex.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gelong
 * @date 2019/12/30 20:12
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    @Resource
    private BinlogClient client;

    @Override
    public void run(String... args) throws Exception {
        log.info("coming in BinlogRunner");
        client.connect();
    }
}
