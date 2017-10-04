# Monsys

This repository is an example that contains Kafka producers and Storm pipelines for apache log processing. 

The example in this repo is loosely based on the code tutorial in the book "Learning Storm" by Ankit Jain and Anand Nalya. The code was adapted for compatibility with the newest versions of the platforms and expanded to include new and additional functionalities.

### The following is the mapping needed for elastic storage

```
curl -XPUT 'http://localhost:9200/apache_logs?pretty' -H 'Content-Type: application/json' -d '{
	"mappings": {
		"record": {
			"_all": {"enabled": false}, 
			"properties": { 
				"ip": {"type":"ip"}, 
				"dateTime": {"type":"date", "format": "dd-MM-yyyy HH:mm:ss"}, 
				"request": {"type": "text"}, 
				"response": {"type":"integer", "coerce": "yes"}, 
				"bytesSent": {"type":"integer", "coerce": "yes"}, 
				"referrer": {"type": "text"}, 
				"useragent": {"type":"text"}, 
				"country": {"type":"text"}, 
				"browser": {"type":"text"}, 
				"os": {"type": "text"}, 
				"geo": {"type": "geo_point"}, 
				"keyword": {"type": "keyword"}
			}
		}
	}
}'
```
