# demo-kafka-elastic

In a system that implements the CQRS pattern (Command query responsibility segregation) this project would implement the query part, at least, as I understand it would be to do this part, adding an event consumer with Kafka in which I receive the event to create a fast and scalable reading table, in this case with elastic search, but it could be any other that allows free and scalable readings, such as couchdb. By using spring-boot as a framework glue, it is possible to change the database with very little effort.


receiving data from kafka topic, saving into ES.

TODO

  Tests! DONE 

  Docker file DONE 

  Docker-compose DONE

  Saving in ES 2.4.1 using property file. DONE 

  Integrate with Eureka DONE. 

  Update docker-compose file to use an integrated eureka server. Not yet. 

  Actually application.yml poins to localhost. Not yet. 

  Integrate with spring-cloud-config server.Not yet. 

  Doing something cool with Kibana. Not yet


Interesting links

  http://www.baeldung.com/dockerizing-spring-boot-application
  
  https://hub.docker.com/r/hyness/spring-cloud-config-server/
  
  https://hub.docker.com/r/hyness/spring-cloud-config-server/
  
  https://github.com/Nasruddin/elasticsearch-spring-boot-spring-data

plugin install mobz/elasticsearch-head
