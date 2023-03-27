# AREP_Lab5

En este taller utilizando docker para crear contenedores e imagenes y luego haciendo uso de AWS se creo un servicio el cual a traves de un balanceador de carga
se accede a 3 diferentes API para prestar un servicio de manera continua y no sobrecargado, donde cada una de las API accede a una base de datos MongoDB.

## Empezando

Con el paso a paso se obtendra una copia de esta aplicacion y podra ser utilizada para acceder a distintos recursos guardados de forma local

### Pre requisitos

1. Git: Descarga y administracion versiones repositorio.
2. Java: Lenguaje utilizado.
3. Maven: Controlador del proyecto.

### Arquitectura

![image](https://user-images.githubusercontent.com/99369778/227954705-9232e6fa-db62-4bf8-8883-a652400ff6ae.png)

Tenemos en docker cada contenedor donde tenemos 3 instancias que realizan las operaciones con la base de datos, una instancia de la base de datos y otra
en la cual se encuentra el balanceador de carga. Luego cada una de estas instancias fue subida a un servicio de EC2 en AWS donde puede ser consultada la aplicación.

### Diseño de clases

Se tiene el balanceador de carga que distribuye entre las 3 instancias las peticiones.
Por medio de Spark se realizan operaciones con la base de datos en la clase SparkWebServer. Donde es MongoServiceImpl quien se encarga de crear la conexion
con la base de datos y realizar las acciones de los logins para retornarselas a el WebServer.

### Installing

Clonamos el repositorio

```
git clone https://github.com/cisfjulian/AREP_Lab5.git
```

Ahora haremos la creacion de imagenes en Docker.

```
docker-compose up -d
```
![image](https://user-images.githubusercontent.com/99369778/227961899-9d3e142a-cdd7-40c3-9893-48d52ba0ca9d.png)

Luego subiremos cada imagen a DockerHub para ser usadas luego en la instancia EC2.

```
docker tag {imagen} {usuario}/{repositorioDocker}
```

![image](https://user-images.githubusercontent.com/99369778/227962094-4d521281-a6d0-45ce-b249-b4ccc4aaad53.png)


Y luego haremos push al repositorio

```
docker push {usuario}/{repositorioDocker}:latest
```
Ahora correremos estas imagenes en la instancia EC2 de AWS en contenedores.

```
docker run -d -p {puerto}:6000 --name {imagen} {usuario}/{repositorioDocker}
```

![image](https://user-images.githubusercontent.com/99369778/227964201-6cf47c39-d399-4299-94b6-0ceb841cb43f.png)

Y configuraremos las reglas de entrada en la instancia EC2 para poder acceder a los servicios.

![image](https://user-images.githubusercontent.com/99369778/227964739-ef83053e-81c9-440b-8769-e7bc08d9bb93.png)


## Test

Pruebas que la aplicacion funciona accediendo a los distintos archivos. Con la URL(para este caso especifico)

ec2-100-26-234-153.compute-1.amazonaws.com;45000

![image](https://user-images.githubusercontent.com/99369778/227960100-567c17a5-2ee0-415f-8a3c-b97fab440046.png)

![image](https://user-images.githubusercontent.com/99369778/227960127-7b9bef7b-d02f-4dcb-aa43-1380c5a8091a.png)

## Construido con

* Maven - Manejo de dependencias y proyecto en general.

## Version

1.0

## Autor

* **Julian Largo**



