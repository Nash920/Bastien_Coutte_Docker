TP Docker – Deux microservices (Java + PHP)
Ce projet contient deux microservices :
Microservice Java (Spring Boot) : retourne le message bonjour.
Microservice PHP : retourne mon prénom.
Les deux services sont dockerisés et publiés sur Docker Hub.

1. Pré-requis

Avant de commencer, installer :
Java JDK 21
Git
Docker Desktop (Windows)
Gradle Wrapper (inclus dans le projet)
Vérification :
java -version
git --version
docker --version

2. Récupération du projet d’origine

Clonage du dépôt fourni :
git clone https://github.com/charroux/ingnum.git
cd ingnum

3. Création de mon dépôt GitHub et configuration

Le dépôt local est associé à mon propre dépôt GitHub :
git remote remove origin
git remote add origin https://github.com/Nash920/Bastien_Coutte_Docker.git
git push -u origin main

4. Microservice Java (Spring Boot)
4.1. Compilation sans Docker

Aller dans le dossier :
cd RentalService
Compiler :

.\gradlew.bat build
Un fichier JAR est généré dans :
build/libs/RentalService-0.0.1-SNAPSHOT.jar

4.2. Exécution sans Docker

java -jar build\libs\RentalService-0.0.1-SNAPSHOT.jar
Test dans le navigateur :
http://localhost:8080/bonjour

4.3. Dockerisation du microservice Java

Créer un fichier Dockerfile dans RentalService :
FROM eclipse-temurin:21
VOLUME /tmp
EXPOSE 8080
ADD ./build/libs/RentalService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

4.4. Construction de l’image Docker
docker build -t rentalservice:1.0 .

4.5. Exécution du conteneur Docker
docker run -p 8080:8080 rentalservice:1.0


Test :
http://localhost:8080/bonjour

4.6. Publication de l’image Docker (Docker Hub)

Connexion :
docker login
Tag :
docker tag rentalservice:1.0 nash920/rentalservice:1.0
Push :
docker push nash920/rentalservice:1.0
Lien Docker Hub :
https://hub.docker.com/r/nash920/rentalservice

5. Microservice PHP

Un second microservice simple est ajouté dans le projet.

5.1. Création du dossier

À la racine du projet :
mkdir PhpService
cd PhpService

5.2. Création du fichier PHP

Créer index.php :
<?php
echo "Bastien";
Ce service retourne mon prénom via GET.

5.3. Dockerfile pour PHP

Créer Dockerfile :
FROM php:8.2-apache
COPY . /var/www/html/
EXPOSE 80

5.4. Construction de l’image Docker PHP
docker build -t phpservice:1.0 .

5.5. Exécution du microservice PHP en Docker
docker run -p 8081:80 phpservice:1.0

Test dans le navigateur :
http://localhost:8081
Contenu attendu :
Bastien

5.6. Publication de l’image sur Docker Hub

Tag :
docker tag phpservice:1.0 nash920/phpservice:1.0

Push :
docker push nash920/phpservice:1.0
Lien Docker Hub :
https://hub.docker.com/r/nash920/phpservice

6. Mise à jour du dépôt GitHub

Retour à la racine du projet :
cd ..
git add .
git commit -m "Ajout du microservice PHP et des Dockerfiles"
git push

7. Résumé du TP

Ce TP m’a permis de :
gérer un projet avec Git et GitHub
compiler un microservice Java (Spring Boot) avec Gradle
exécuter une application Java sans Docker
dockeriser une application Java via un Dockerfile
créer un second microservice en PHP
dockeriser le service PHP
construire et exécuter deux images Docker
publier deux images Docker sur Docker Hub
créer un README complet
maintenir un dépôt Git propre et fonctionnel
Images Docker publiées :
Java : https://hub.docker.com/r/nash920/rentalservice
PHP : https://hub.docker.com/r/nash920/phpservice


Docker Compose

Un fichier docker-compose.yml a été ajouté à la racine du projet afin de builder et lancer les deux microservices (Java et PHP).
Contenu du fichier docker-compose.yml
services:
  java-service:
    build:
      context: ./RentalService
      dockerfile: Dockerfile
    container_name: java-service
    ports:
      - "8080:8080"

  php-service:
    build:
      context: ./PhpService
      dockerfile: Dockerfile
    container_name: php-service
    ports:
      - "8081:80"

Lancement des deux services
docker compose up --build
Les deux conteneurs sont alors accessibles :

Microservice Java :
http://localhost:8080/bonjour

Microservice PHP :
http://localhost:8081/

Communication entre microservices
Le microservice Java a été modifié pour communiquer avec le microservice PHP.
Un nouvel endpoint a été ajouté :

GET http://localhost:8080/bonjour-php

Ce point d’entrée appelle automatiquement le microservice PHP via l’URL interne Docker :
http://php-service
Code ajouté dans BonjourController.java :

@GetMapping("/bonjour-php")
public String bonjourDepuisPhp() {
    String prenom = restTemplate.getForObject("http://php-service", String.class);
    return "bonjour " + prenom;
}


Résultat dans le navigateur :

bonjour Bastien

→ Cela prouve que les deux microservices communiquent correctement via Docker.

