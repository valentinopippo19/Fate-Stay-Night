# Cómo ejecutar y entregar el proyecto

## Ejecutar sin instalar Java

En Windows, descomprimí el proyecto y hacé doble clic en:

`launcher/run.bat`

El lanzador busca un runtime incluido, después intenta usar Java 21+ del sistema y, si no existe, descarga automáticamente un **Eclipse Temurin JRE 21** y ejecuta el JAR. No necesita Maven para ejecutar.

En Linux/macOS:

`./launcher/run.sh`

El mismo concepto se aplica: usa Java 21+ si existe y, si no, descarga un JRE 21 apropiado.

## Ejecutar desde Visual Studio Code / IntelliJ / Eclipse

La opción recomendada para presentar el trabajo es ejecutar `dist/fate-algorithm-war.jar` mediante el launcher. El panel web se sirve desde el propio JAR, por lo que ya no depende de la carpeta `src/main/resources/web` ni del directorio actual.

El servidor intenta `localhost:8080` y, si el puerto está ocupado, busca automáticamente el siguiente puerto disponible hasta `8279` y abre ese puerto.

## Compilar

Se requiere JDK 21 para compilar. Maven sigue siendo compatible con el proyecto mediante `pom.xml`, pero el artefacto ejecutable no necesita Maven ni Java instalado en la máquina del usuario final.
