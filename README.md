# event_deduplication

To run the application download the project and run the 

1) Download and install kafka using the following steps
      https://docs.confluent.io/platform/current/installation/installing_cp/zip-tar.html
2) create kafka topic named source-1, source-2, source-3
3) produce the meesages using producer app
4) Download and run the application
5) Hit the following url http://localhost:8080/eventlistner/startconsumer to start consumer
6) Hit the following url http://localhost:8080/eventlistner/get_top_duplicates/{num of top n duplicate events} to fetch the top n duplicates.
