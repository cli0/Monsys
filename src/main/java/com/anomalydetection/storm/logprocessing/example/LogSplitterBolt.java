package com.anomalydetection.storm.logprocessing.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class LogSplitterBolt extends BaseBasicBolt {

	  private static final long serialVersionUID = 1L;
	  // Create the instance of the ApacheLogSplitter class.
	  private static final LogSplitter apacheLogSplitter = new LogSplitter();
	  private static final List<String> LOG_ELEMENTS = new ArrayList<String>();
	  static {
	    LOG_ELEMENTS.add("ip");
	    LOG_ELEMENTS.add("dateTime");
	    LOG_ELEMENTS.add("request");
	    LOG_ELEMENTS.add("response");
	    LOG_ELEMENTS.add("bytesSent");
	    LOG_ELEMENTS.add("referrer");
	    LOG_ELEMENTS.add("useragent");
	  }

	  public void execute(Tuple input, BasicOutputCollector collector) {
	    // Get the Apache log from the tuple
	    String log = input.getString(0);

	    if (StringUtils.isBlank(log)) {
	      // Ignore blank lines
	      return;
	    }
	    // Call the logSplitter(String apachelog) method // of the ApacheLogSplitter class.
	    Map<String, Object> logMap = apacheLogSplitter.logSplitter(log);
	    List<Object> logdata = new ArrayList<Object>();
	    for (String element : LOG_ELEMENTS) {
	      logdata.add(logMap.get(element));
	    }
	    // emits set of fields (ip, referrer, user-agent,// bytesSent, and so on.)
	    collector.emit(logdata);

	  }
	  public void declareOutputFields(OutputFieldsDeclarer declarer) {
	    // Specify the name of output fields.
	    declarer.declare(new Fields("ip", "dateTime", "request", "response", "bytesSent", "referrer", "useragent"));
	  }
	}