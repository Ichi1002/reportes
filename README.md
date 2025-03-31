# reportes

Api para Reportes

Esta api permite la generacion de estudiante, creacion de reportes en pdf

Para correr el proyecto es necesario tener docker instalado

Hacer un pull del repositorio sobre una carpeta del sistema

Luego ejecutar el siguiente comando
para construir el jar
.\gradlew build -x test

En la misma carpeta correr el siguiente comando
para crear la imagen de docker

docker build -t spring/reportes .

Luego ejecutar
docker-compuse up 

Y se levanta la base de datos y el proyecto

Para la configuracion inicial de la base de datos es necesario correr el loadInit.sql

Finalmente se añade un archivo con los curls y su explicacion para ser usados