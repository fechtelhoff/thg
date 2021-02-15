mkdir src\main\resources\META-INF\native-image
java -agentlib:native-image-agent=config-output-dir=src\main\resources\META-INF\native-image --module-path c:\Software\Java\15\JavaFX-SDK-15.0.1\lib --add-modules javafx.controls,javafx.fxml -jar target\thg-gui-javafx-1.0-SNAPSHOT-jar-with-dependencies.jar
