# Digital Wallet Api

## Building the Api
Run the following maven command
- `mvn clean install`

This will create `digital-wallet-api-0.0.1-SNAPSHOT.jar`
- `application*.yml` are included in `digital-wallet-api-0.0.1-SNAPSHOT.jar`



## Launching the Api
You can launch the application using
 ```
java 
-Dlogging.root=${logging.root}
-Dspring.profiles.active=${spring.profiles.active}
-jar digital-wallet-api-{version}.jar
 ```

`${logging.root}` is the root location of the log files
`${spring.profiles.active}` are the launch profiles  
`${version}` is the version of the artifact

### Example
```
java
-Dlogging.root=${user.home}/applog
-Dspring.profiles.active=dev
-jar digital-wallet-api-0.0.1-SNAPSHOT.jar
 
 ```

## Validate Application Deployment
Search for the string `Tomcat started on port(s):` in the log file `${logging.root}/digital-wallet-api/digital-wallet-api.log`

## Check the Api
This api configured to run on port 9095 at application.properties file. Check the operations from swagger

### Example on local
http://localhost:9095/swagger-ui/

