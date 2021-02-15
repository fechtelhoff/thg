# The Holy Graal - JavaFX GUI

Damit die JavaFX App nicht nur über Maven gestartet werden kann (über das entsprechende Maven-Plugin via `mvn javafx:run`),
sondern ebenfalls über den „Start-Knopf" (grünes Dreieck, Run Configuration) der IDE,
muss das Java Platform Module System (JPMS) verwendet werden.

Eindeutiges Zeichen hierfür ist die Existenz der Datei `module-info.java`
welche insbesondere bei Erweiterungen des Projektes (Nutzung von weiteren JavaFX-Dependencies) 
entsprechend angepasst werden muss.

### Links

- [IntelliJ IDEA and JavaFX - Trisha Gee](https://blog.jetbrains.com/idea/2021/01/intellij-idea-and-javafx/)
  - [Source Code on Github](https://github.com/trishagee/javafx-maven)
- [Gluon Client plugin for Maven](https://github.com/gluonhq/client-maven-plugin)
  - [The Gluon Client plugin for Maven (Documentation)](https://docs.gluonhq.com/#_the_gluon_client_plugin_for_maven)
