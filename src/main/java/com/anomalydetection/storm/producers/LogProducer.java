package com.anomalydetection.storm.producers;

import org.apache.storm.*;
import org.apache.storm.kafka.*;
import org.apache.kafka.clients.producer.*;

import java.io.*;
import java.util.Properties;

public class LogProducer {

    public static void main(String[] args){

        //Configuration for connecting to Kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.0.4.70:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("request.required.acks", "1");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        try {
            FileInputStream fstream = new FileInputStream("./src/main/resources/apache_test.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            /* read log line by line */
            while ((strLine = br.readLine()) != null) {
                //instead of KeyedMessage, the new Kafka API requires ProducerRecord
                ProducerRecord<String, String> data = new ProducerRecord<String, String>("apache_test", strLine);
                producer.send(data);
                System.out.println("Logs were successfully written in Kafka.");
            }
            br.close();
            fstream.close();
        }catch (Exception e) {
            throw new RuntimeException("Error occurred while persisting records : ");
        }
        
        // close the producer
        producer.close();
    }
}


