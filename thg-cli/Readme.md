# THG-CLI

Die Verwendung des Annotation Processors (s. u.) von _**Picocli**_ erzeugt automatisch bei der Kompilierung die Konfigurationsdateien für das Native-Image.   

Leider wird hierbei in der `resource-config.json` nicht die für die Ausgabe der Version benötigte Datei `project.properties` in der Datei `resource-config.json` deklariert.  

Aus diesem Grunde wurde wieder auf die manuelle Erstellung der Konfigurationsdateien mit dem nachfolgenden Befehl zurückgegriffen.
```
java -agentlib:native-image-agent=config-output-dir=src\main\resources\META-INF\native-image -jar target\thg-cli-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Konfiguration des Picocli Annotation Processors
```xml
<build>
    <plugins>
        <!-- Compiler -->
        <plugin>
                        <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <annotationProcessorPaths>
                    <path>
                        <groupId>info.picocli</groupId>
                        <artifactId>picocli-codegen</artifactId>
                        <version>${version.picocli}</version>
                    </path>
                </annotationProcessorPaths>
                <compilerArgs>
                    <arg>-Aproject=${project.groupId}/${project.artifactId}</arg>
                </compilerArgs>
            </configuration>
        </plugin>
    </plugins>
</build>
```