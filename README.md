# demo-kafka-elastic

In a system that implements the CQRS pattern (Command query responsibility segregation) this project would implement the query part, at least, as I understand it would be to do this part, adding an event consumer with Kafka in which I receive the event to create a fast and scalable reading table, in this case with elastic search, but it could be any other that allows free and scalable readings, such as couchdb. By using spring-boot as a framework glue, it is possible to change the database with very little effort.


receiving data from kafka topic, saving into ES.

TODO

Tests! DONE <br \r>
Docker file DONE <br \r>
Docker-compose DONE <br \r>
Saving in ES 2.4.1 using property file. DONE <br \r>
Integrate with Eureka DONE. <br \r>
Update docker-compose file to use an integrated eureka server. <br \r>
Actually application.yml poins to localhost. In progress <br \r>
Integrate with spring-cloud-config server.Not yet. <br \r>
Doing something cool with Kibana. Not yet<br \r>
<br \r>
See<br \r>

http://www.baeldung.com/dockerizing-spring-boot-application
<br \r>
https://hub.docker.com/r/hyness/spring-cloud-config-server/
<br \r>
https://hub.docker.com/r/hyness/spring-cloud-config-server/
<br \r>
https://github.com/Nasruddin/elasticsearch-spring-boot-spring-data
<br \r>

plugin install mobz/elasticsearch-head
<br \r>
