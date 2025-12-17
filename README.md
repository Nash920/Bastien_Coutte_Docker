1. Récupération du projet

Clonage du dépôt d’origine :
git clone https://github.com/charroux/ingnum.git
cd ingnum
Création d’un dépôt Git personnel sur GitHub, puis changement du dépôt distant :
git remote remove origin
git remote add origin https://github.com/Nash920/Bastien_Coutte_Docker.git
git push -u origin main

2. Prérequis

Java JDK 21
Git
Docker Desktop
Système d’exploitation : Windows
Vérification des installations :
java -version
git --version
docker --version

3. Compilation et exécution sans Docker

Le projet utilise Gradle Wrapper.
Se placer dans le dossier du service :
cd RentalService
Compilation du projet
Sous Windows :
.\gradlew.bat build
Cette commande génère le fichier JAR dans le dossier build/libs.
Exécution de l’application
java -jar build\libs\RentalService-0.0.1-SNAPSHOT.jar
Vérification
Dans un navigateur web, accéder à l’adresse suivante :
http://localhost:8080/bonjour
Le message bonjour confirme que l’application fonctionne correctement.

4. Dockerisation de l’application
   
Création du Dockerfile
Un fichier nommé Dockerfile (sans extension) est créé dans le dossier RentalService avec le contenu suivant :
FROM eclipse-temurin:21
VOLUME /tmp
EXPOSE 8080
ADD ./build/libs/RentalService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
Construction de l’image Docker
Toujours dans le dossier RentalService :
docker build -t rentalservice:1.0 .
Cette commande crée une image Docker nommée rentalservice avec le tag 1.0.
Exécution du conteneur Docker
docker run -p 8080:8080 rentalservice:1.0
Vérification
Dans le navigateur :
http://localhost:8080/bonjour
Le message bonjour confirme que l’application fonctionne correctement dans un conteneur Docker.

6. Publication de l’image sur Docker Hub

Connexion à Docker Hub :
docker login
Tag de l’image avec le nom du compte Docker Hub :
docker tag rentalservice:1.0 <VOTRE_USER_DOCKERHUB>/rentalservice:1.0


Publication de l’image :

docker push <VOTRE_USER_DOCKERHUB>/rentalservice:1.0
