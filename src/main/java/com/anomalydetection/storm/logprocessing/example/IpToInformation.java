package com.anomalydetection.storm.logprocessing.example;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

public class IpToInformation {

	  private static LookupService cl = null;

	  /**
	   * A parameterized constructor which would take 
	   * the location of the GeoLiteCity.dat file as input.
	   * 
	   * @param pathTOGeoLiteCityFile
	   */
	  public IpToInformation(String pathTOGeoLiteCityFile) {
	    try {
	      cl = new LookupService(pathTOGeoLiteCityFile,
	          LookupService.GEOIP_MEMORY_CACHE);
	    } catch (Exception exception) {
	      throw new RuntimeException(
	          "Error occurred while initializing IpToInformation class: ");
	    }
	  }

	  /**
	   * These methods takes the IP address of the input and
	   * provides information about the location.
	   * 
	   * @param ip
	   * @return
	   */
	  public String ipToCountry (String ip) {
	    Location location = cl.getLocation(ip);
	    if (location == null) {
	      return "NA";
	    }
	    if (location.countryName == null) {
	      return "NA";
	    }
	    return location.countryName;
	  }
	  
	  public String ipToLatitude (String ip) {
		    Location location = cl.getLocation(ip);
		    if (location == null) {
		      return "NA";
		    }
		    if (Double.isNaN(location.latitude)) {
		      return "NA";
		    }
		    return location.latitude + "" ;
	  }
	  
	  
	  public String ipToLongitude (String ip) {
		    Location location = cl.getLocation(ip);
		    if (location == null) {
		      return "NA";
		    }
		    if (Double.isNaN(location.longitude)) {
		      return "NA";
		    }
		    return location.longitude + "";
	  }
	  
	  
	}
