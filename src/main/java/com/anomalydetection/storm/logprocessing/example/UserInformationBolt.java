package com.anomalydetection.storm.logprocessing.example;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class UserInformationBolt extends BaseRichBolt {

	  private static final long serialVersionUID = 1L;
	  private IpToInformation ipToInformation = null;
	  private UserAgentTools userAgentTools = null;
	  public OutputCollector collector;
	  private String pathTOGeoLiteCityFile;

	  public UserInformationBolt(String pathTOGeoLiteCityFile) {
	    // set the path of the GeoLiteCity.dat file.
	    this.pathTOGeoLiteCityFile = pathTOGeoLiteCityFile;
	  }

	  public void declareOutputFields(OutputFieldsDeclarer declarer) {
	    declarer.declare(new Fields("ip", "dateTime", "request", "response",
	        "bytesSent", "referrer", "useragent", "country", "browser",
	        "os", "latitude", "longitude"));
	  }
	  public void prepare(Map stormConf, TopologyContext context,
	      OutputCollector collector) {
	    this.collector = collector;
	    this.ipToInformation = new IpToInformation(
	        this.pathTOGeoLiteCityFile);
	    this.userAgentTools = new UserAgentTools();

	  }

	  public void execute(Tuple input) {

	    String ip = input.getStringByField("ip").toString();
	    
	    Object country = ipToInformation.ipToCountry(ip);
	    Object browser = userAgentTools.getBrowser(input.getStringByField(
	        "useragent").toString())[1];
	    Object os = userAgentTools.getOS(input.getStringByField("useragent").toString())[1];
	    Object latitude = ipToInformation.ipToLatitude(ip);
	    Object longitude = ipToInformation.ipToLongitude(ip);
	    
	    collector.emit(new Values(input.getString(0), input.getString(1), input.getString(2), input.getString(3), input.getString(4), input.getString(5), input.getString(6), country, browser, os, latitude, longitude));

	  }
	}
