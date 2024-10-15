# ParcialMagneto
Este proyecto es una API desarrollada en Java con Spring Boot que permite verificar si una secuencia de ADN pertenece a un mutante. Se ha utilizado una base de datos H2 en memoria para almacenar las secuencias de ADN verificadas. Además, se ha realizado el deploy en Render utilizando Docker para la creación de un contenedor y snapshot.jar para el build del proyecto.

Tabla de contenidos
* Requisitos previos
* Instalación
* Ejecutar la aplicación localmente
* Deploy en Render
* Endpoints
* Tecnologías utilizadas
* Estructura del proyecto

# Requisitos previos
Asegúrate de tener instalados los siguientes programas en tu máquina:

* JDK 17 o superior.
* Gradle para la gestión de dependencias.
* Docker (opcional, para el despliegue con Docker).
* Git para clonar el repositorio.
# Instalación
Clonar el repositorio
Primero, clona este repositorio en tu máquina local usando Git:
git clone (https://github.com/JenJen007/ParcialMagneto/ParcialMagneto.git)
cd magnetodna

# Configurar dependencias
El proyecto utiliza Gradle para gestionar las dependencias. Puedes instalar todas las dependencias ejecutando el siguiente comando:


./gradlew build
Esto generará el archivo snapshot.jar que utilizaremos para la ejecución y el despliegue del proyecto.

# Ejecutar la aplicación localmente
# Paso 1: Compilar el proyecto
Compila el proyecto y crea el archivo .jar:

./gradlew build
# Paso 2: Ejecutar el archivo .jar
Una vez que hayas generado el archivo snapshot.jar, puedes ejecutar la aplicación con el siguiente comando:

java -jar build/libs/snapshot.jar
La aplicación estará disponible en (http://localhost:8080).

# Paso 3: Probar la API localmente
Puedes usar Postman o cURL para probar los endpoints de la API. Ejemplo de prueba con cURL para verificar si una secuencia de ADN es mutante:

``curl -X POST http://localhost:8080/api/mutant -H "Content-Type: application/json" -d '["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]'´´
# Deploy en Render
Este proyecto ha sido desplegado en Render usando Docker. Puedes acceder al endpoint público aquí:

(https://parcialmagneto.onrender.com)

# Proceso de despliegue en Render:
1. Crea una cuenta en Render.com.

2. Conecta tu repositorio de GitHub en Render.

3.Configura el despliegue:

* Usa el archivo Dockerfile para configurar el contenedor de la aplicación.
* Selecciona Java como entorno y configura el build para generar el archivo snapshot.jar.
* Define el puerto de la aplicación (8080 por defecto).
 *Render realizará el build automáticamente y ejecutará la aplicación en un contenedor Docker.

El proyecto fue desplegado con el siguiente Dockerfile:

dockerfile
Copiar código
# Establecer la imagen base de Java
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY build/libs/snapshot.jar /app/snapshot.jar


# Exponer el puerto en el que se ejecuta la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "snapshot.jar"]

# Endpoints
POST /api/mutant
Verifica si una secuencia de ADN pertenece a un mutante.

URL: /api/mutant
Método HTTP: POST
Body: Una secuencia de ADN en formato JSON (array de strings).
Ejemplo de solicitud:
``[
  "ATGCGA",
  "CAGTGC",
  "TTATGT",
  "AGAAGG",
  "CCCCTA",
  "TCACTG"
]´´
Posibles respuestas:
200 OK: La secuencia de ADN es de un mutante.
403 Forbidden: La secuencia de ADN es de un humano.
GET /api/stats
Devuelve las estadísticas de mutantes y humanos analizados.

URL: /api/stats
Método HTTP: GET
Ejemplo de respuesta:

``{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}´´
# Tecnologías utilizadas
* Java 17
* Spring Boot
* H2 Database (base de datos en memoria)
* Docker (para contenedores)
* Render (plataforma de despliegue)
* 
# Estructura del proyecto

├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───example
│   │   │           └───magnetodna
│   │   │               ├───Controllers   # Controladores REST
│   │   │               ├───Dto           # Data Transfer Objects
│   │   │               ├───Entities      # Entidades de la base de datos
│   │   │               ├───Repository    # Repositorios (interacciones con la base de datos)
│   │   │               └───Services      # Lógica de negocio
│   ├───resources
│   │   └───application.properties        # Configuración del proyecto (H2, etc.)
└───Dockerfile                            # Configuración del contenedor Docker
# Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más detalles.

