#!/bin/bash
java --module-path "c:\Software\Java\15\JavaFX-SDK-15.0.1\lib" --add-modules javafx.controls,javafx.fxml -jar thg-gui-javafx-${project.version}.jar
exit
