## build 
- mvn clean package -DskipTests=true
## execute local
- supplier service (with postgres): java -jar -Dspring.profiles.active=dev ./supplier-service/target/t1-supplier-service.jar 
- customer service: java -jar -Dspring.profiles.active=dev ./customer-service/target/t1-customer-service.jar    
## deploy in docker
- bash run.sh
