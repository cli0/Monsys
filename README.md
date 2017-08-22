# Monsys

This repository contains Kafka producers and Storm pipelines for log processing.

### The following is the mapping needed for elastic

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
