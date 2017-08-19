package com.anomalydetection.storm.logprocessing.example;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogSplitter {

	  public Map<String,Object> logSplitter(String apacheLog) {

	    String logEntryLine = apacheLog;
	    // Regex pattern to split fetch
	    // the different properties from log lines.
	    String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w-:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";
	    Pattern p = Pattern.compile(logEntryPattern);
	    Matcher matcher = p.matcher(logEntryLine);
	    Map<String,Object> logMap = new HashMap<String, Object>();
	    if (!matcher.matches() || 9 != matcher.groupCount()) {
	      //System.err.println("Bad log entry (or problem with RE?):");
	      //System.err.println(logEntryLine);
	      return logMap;
	    }
	    // set the ip, dateTime, request, etc into map.
	    logMap.put("ip", matcher.group(1));
	    logMap.put("dateTime", matcher.group(4));
	    logMap.put("request", matcher.group(5));
	    logMap.put("response", matcher.group(6));
	    logMap.put("bytesSent", matcher.group(7));
	    logMap.put("referrer", matcher.group(8));
	    logMap.put("useragent", matcher.group(9));
	    return logMap;
	  }
}
