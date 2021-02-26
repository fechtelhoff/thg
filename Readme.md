# The Holy Graal

Beispielprojekt für die Nutzung von GraalVM.

Graal = General Recursive Applicative and Algorithmic Language

### Modulübersicht

|  #  | Modul | Name | Beschreibung |
| --- | ----- | ---- | ------------ |
| 1 | thg-api | API | tbd |
| 2 | thg-core | Core | tbd |
| 4 | thg-cli | CLI | tbd |
| 5 | thg-gui-shared | GUI Shared | tbd |
| 6 | thg-gui-swing | GUI Swing | tbd |
| 7 | thg-gui-javafx | GUI JavaFX | tbd |
| 8 | thg-doc | Documentation | tbd |
| 9 | thg-assembly | Assembly | tbd |

---
### Links
- [**GraalVM**](https://www.graalvm.org/)
  - Quick References
    - [GraalVM Quick Reference](https://medium.com/graalvm/graalvm-quick-reference-b8d1dfe24241)
    - [GraalVM Native Image Quick Reference](https://medium.com/graalvm/graalvm-native-image-quick-reference-4ceb84560fd8)
  - [Building Cross Platform Native Images With GraalVM](https://blogs.oracle.com/developers/building-cross-platform-native-images-with-graalvm)
  - [Cross-Platform Development in Java with Gluon and GraalVM ](https://foojay.io/today/cross-platform-development-in-java-with-gluon-and-graalvm/)
  - [Fix GraalVM Native Image Build Issues](https://simply-how.com/fix-graalvm-native-image-compilation-issues)
- [**JCommander** - CLI Framework](http://jcommander.org/) (**_Anmerkung_**: Scheint nicht mit GraalVM zu funktionieren.)
- [**PicoCli** - CLI Framework](https://github.com/remkop/picocli)
- **Swing**
    - [Oracle Tutorial - Swing GUI](https://docs.oracle.com/javase/tutorial/uiswing/index.html)
    - [AssertJ Swing - Swing UI Testing Framework](https://joel-costigliola.github.io/assertj/assertj-swing.html)
- [**JavaFX**](https://openjfx.io/)
    - [Gluon](https://gluonhq.com/)
    - [JavaFX in the new era of GraalVM](https://gluonhq.com/javafx-in-the-new-era-of-graalvm/)
    - [TestFX - JavaFX UI Testing Framework](https://github.com/TestFX/TestFX)
- [**Blindtext Generator**](https://www.blindtextgenerator.de/)

---
### Build commands

|  #  | Befehl | Zeit | Bemerkung |
| --- | ----- | ---- | --------- |
| 0 | setEnv.cmd | - | |
| 0 | setJava.cmd | - | |
| 0 | mvn -version | - | optional |
| 0 | mvn verify -PcheckVersions | - | optional |
| 1 | mvn clean | 0.533 s | |
| 2 | mvn install -DskipTests | 12.672 s | | 
| 3 | mvn test | 11.183 s |  |
| 4 | mvn test -PrunIntTests | 5.176 s | optional | 
| 5 | mvn test -PrunGuiTest | 49.692 s | optional |
| 6 | mvn install -DskipTests -PcreateNativeImages | 05:35 min | | 
| 7 | mvn -f thg-doc install -PcreateDocumentation | 20.231 s |  |
| 8 | mvn test -DskipTests -PcreateLicenses | 10.099 s |  |
| 9 | mvn -f thg-assembly install -PcreateAssembly | 3.846 s | | 

Die finale ZIP-Datei liegt im Verzeichnis `thg-assembly/target`.

---
### Useful GraalVM commands
```
java -agentlib:native-image-agent=config-output-dir=META-INF\native-image -jar <JAR_FILE>

native-image-configure generate --input-dir=/path/to/config-dir-0/ --input-dir=/path/to/config-dir-1/ --output-dir=/path/to/merged-config-dir/
```
