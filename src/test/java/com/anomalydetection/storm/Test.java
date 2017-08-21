package com.anomalydetection.storm;
//
import org.json.*;

public class Test {
	public static void main(String[] args) {
		
		String x = "dd-MM-yyyy:HH:mm:ss -0500";
	    Object timestamp = x.split("\\s+")[0].replaceFirst(":", " ");

		System.out.println(timestamp);
	}
}
