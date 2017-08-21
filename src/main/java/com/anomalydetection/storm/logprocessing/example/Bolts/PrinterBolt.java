package com.anomalydetection.storm.logprocessing.example.Bolts;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class PrinterBolt extends BaseBasicBolt {

	private static final long serialVersionUID = 111L;

	@Override
	  public void execute(Tuple tuple, BasicOutputCollector collector) {
	    System.out.println(tuple);
	  }

	  @Override
	  public void declareOutputFields(OutputFieldsDeclarer ofd) {
	  }

}