mkdir src\main\resources\META-INF\native-image
java -agentlib:native-image-agent=config-output-dir=src\main\resources\META-INF\native-image -jar target\thg-cli-1.0-SNAPSHOT-jar-with-dependencies.jar
