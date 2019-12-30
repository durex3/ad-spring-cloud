package com.durex.ad.mysql;

import com.durex.ad.mysql.listener.AggregationListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author gelong
 * @date 2019/12/30 20:00
 */
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;
    @Resource
    private BinlogConfig config;
    @Resource
    private AggregationListener aggregationListener;

    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (StringUtils.isNotEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            client.registerEventListener(aggregationListener);
            try {
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql done");
            } catch (IOException e) {
                log.error("connecting to fail");
            }
        }).start();
    }

    public void close() {
        try {
            client.disconnect();
        } catch (IOException e) {
            log.error("disconnect to fail");
        }
    }
}
