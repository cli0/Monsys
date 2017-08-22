package com.anomalydetection.storm.logprocessing.example;

import com.anomalydetection.storm.logprocessing.example.Bolts.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.elasticsearch.storm.EsBolt;

public class LogProcessingTopology {
	  public static void main(String[] args) throws Exception {

	    // zookeeper hosts for the Kafka cluster
	    ZkHosts zkHosts = new ZkHosts("10.0.4.70:2181");

	    // Create the KafkaSpout configuartion
	    // Second argument is the topic name
	    // Third argument is the zookeeper root for Kafka
	    // Fourth argument is consumer group id
	    SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, "apachelogs", "", "0");

	    // Specify that the kafka messages are String
	    kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

	    // Now we create the topology
	    TopologyBuilder builder = new TopologyBuilder();
	    
	    
	    Map config = new HashMap();
	    config.put("es.index.autocreate", true);
	    //config.put("es.resource", "apache_logs/record");
	    config.put("es.nodes", "10.0.4.70");
	    config.put("es.storm.bolt.write.ack", false);
	    config.put("es.storm.bolt.flush.entries.size", 1000);
	    config.put("es.storm.bolt.tick.tuple.flush", 2);

	    // set the kafka spout class
	    builder.setSpout("KafkaSpout", new KafkaSpout(kafkaConfig), 1);
	    builder.setBolt("LogSplitter", new LogSplitterBolt(), 1).globalGrouping("KafkaSpout");
	    builder.setBolt("IpToInformation", new UserInformationBolt("./src/main/resources/GeoLiteCity.dat"), 1)
	        .globalGrouping("LogSplitter");
	    builder.setBolt("Keyword", new KeywordBolt(), 1).globalGrouping("IpToInformation");
	    //builder.setBolt("ESpersistence", new EsBolt("apache_logs/record", config), 1).globalGrouping("Keyword");
	    builder.setBolt("Printer", new PrinterBolt(), 1).globalGrouping("Keyword"); //<-- testing bolt, keep it for a while

	    /*if (args != null && args.length > 0) {
	      // Run the topology on remote cluster.
	      Config conf = new Config();
	      conf.setNumWorkers(4);
	      try {
	        StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
	      } catch (AlreadyAliveException alreadyAliveException) {
	        System.out.println(alreadyAliveException);
	      } catch (InvalidTopologyException invalidTopologyException) {
	        System.out.println(invalidTopologyException);
	      }
	    } else {*/
	      // create an instance of the LocalCluster class
	      // for executing the topology in the local mode.
	      LocalCluster cluster = new LocalCluster();
	      Config conf = new Config();

	      // Submit topology for execution
	      cluster.submitTopology("KafkaTopology", conf, builder.createTopology());

	      try {
	        // Wait for some time before exiting
	        System.out.println("**********************Waiting to consume from kafka");
	        Thread.sleep(10000);

	      } catch (Exception exception) {
	        System.out.println("******************Thread interrupted exception : " + exception);
	      }

	      // kill KafkaTopology
	      cluster.killTopology("KafkaToplogy");

	      // shut down the storm test cluster
	      cluster.shutdown();

	    

	  }
	}
