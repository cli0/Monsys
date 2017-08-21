package com.anomalydetection.storm.logprocessing.example.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordExtractor {
	  public String getKeyword(String referer) {

	    String[] temp;
	    Pattern pat = Pattern.compile("[?&#]q=([^&]+)");
	    Matcher m = pat.matcher(referer);
	    if (m.find()) {
	      String searchTerm = null;
	      searchTerm = m.group(1);
	      temp = searchTerm.split("\\+");
	      searchTerm = temp[0];
	      for (int i = 1; i < temp.length; i++) {
	        searchTerm = searchTerm + " " + temp[i];
	      }
	      return searchTerm;
	    } else {
	      pat = Pattern.compile("[?&#]p=([^&]+)");
	      m = pat.matcher(referer);
	      if (m.find()) {
	        String searchTerm = null;
	        searchTerm = m.group(1);
	        temp = searchTerm.split("\\+");
	        searchTerm = temp[0];
	        for (int i = 1; i < temp.length; i++) {
	          searchTerm = searchTerm + " " + temp[i];
	        }
	        return searchTerm;
	      } else {
	        //
	        pat = Pattern.compile("[?&#]query=([^&]+)");
	        m = pat.matcher(referer);
	        if (m.find()) {
	          String searchTerm = null;
	          searchTerm = m.group(1);
	          temp = searchTerm.split("\\+");
	          searchTerm = temp[0];
	          for (int i = 1; i < temp.length; i++) {
	            searchTerm = searchTerm + " " + temp[i];
	          }
	          return searchTerm;
	        }  else {
	            return "NA";
	        }
	      }
	    }
	  }
	}
