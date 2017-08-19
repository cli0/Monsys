package com.anomalydetection.storm.logprocessing.example;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class KeywordBolt extends BaseRichBolt {

	  private static final long serialVersionUID = 1L;
	  private KeywordExtractor keywordGenerator = null;
	  public OutputCollector collector;

	  public KeywordBolt() {
	  }

	  public void declareOutputFields(OutputFieldsDeclarer declarer) {
	    declarer.declare(new Fields("ip", "dateTime", "request", "response", "bytesSent", "referrer", "useragent", "country", "browser", "os", "latitude", "longitude", "keyword"));
	  }

	  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
	    this.collector = collector;
	    this.keywordGenerator = new KeywordExtractor();

	  }

	  public void execute(Tuple input) {

	    String referrer = input.getStringByField("referrer").toString();
	    // Call the getKeyword(String referrer) method
	    // of the KeywordGenerator class to
	    // extract the keyword.
	    Object keyword = keywordGenerator.getKeyword(referrer);
	    // emits all the field emitted by previous bolt + 
	    // the keyword
	    collector.emit(new Values(input.getString(0), input.getString(1), input.getString(2), input.getString(3), input.getString(4), input.getString(5), input.getString(6), input.getString(7), input.getString(8), input.getString(9), input.getString(10), input.getString(11), keyword));

	  }
	}
