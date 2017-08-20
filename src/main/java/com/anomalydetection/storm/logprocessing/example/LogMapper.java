/*This is a custom mapper for Storm's ES native library. In case it ever supports 5.X, this can be used as a template.
 
  
package com.anomalydetection.storm.logprocessing.example;
 


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.storm.tuple.ITuple;
import org.apache.storm.elasticsearch.common.EsTupleMapper;

public class LogMapper implements EsTupleMapper{

	private static final long serialVersionUID = 1L;

	@Override
	public String getId(ITuple tuple) {
		return UUID.randomUUID().toString();
	}

	@Override
	public String getIndex(ITuple tuple) {
		String index = "logs";
		return index;
	}

	@Override
	public String getSource(ITuple tuple) {
		String source ="";
		try {
			source = new JSONObject().put("ip", tuple.getValueByField("ip"))
												.put("dateTime", tuple.getValueByField("dateTime"))
												.put("request", tuple.getValueByField("request"))
												.put("response", tuple.getValueByField("response"))
												.put("bytesSent", tuple.getValueByField("bytesSent"))
												.put("referrer", tuple.getValueByField("referrer"))
												.put("useragent", tuple.getValueByField("useragent"))
												.put("country", tuple.getValueByField("country"))
												.put("browser", tuple.getValueByField("browser"))
												.put("os", tuple.getValueByField("os"))
												.put("latitude", tuple.getValueByField("latitude"))
												.put("longitude", tuple.getValueByField("longitude"))
												.put("keyword", tuple.getValueByField("keyword"))
												.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return source;
	}

	@Override
	public String getType(ITuple tuple) {
		String type = "record";
		return type;
	}

}*/