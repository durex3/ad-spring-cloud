package com.durex.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.durex.ad.mysql.dto.MySqlRowData;
import com.durex.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author gelong
 * @date 2020/1/7 21:43
 */
@Slf4j
@Component
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sender(MySqlRowData rowData) {
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    private void processMysqlRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(message.toString(), MySqlRowData.class);
            System.out.println("kafka processMysqlRowData: " + JSON.toJSONString(rowData));
        }
    }
}
