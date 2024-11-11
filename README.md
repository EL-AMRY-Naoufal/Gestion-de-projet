# Gestion de projet

## Installation de l'environnement de dev

### Prérequis

On utilisera docker pour assurer la compatibilité entre tous les environnements de développement.

Il est recommandé d'installer [Docker Desktop](https://docs.docker.com/desktop/) pour plus de facilités.

- [Docker v27+](https://docs.docker.com/get-started/get-docker/) 
- [Docker-Compose v2.29+](https://docs.docker.com/compose/install/)

### Configuration de la base de données

Avant de la créer, il faut assigner un nom d'utilisateur et un mot de passe pour l'utilisateur root de la base de données. Pour renseigner ceux-ci, il faut copier-coller le fichier *.env.example* et enlever l'extension *.example* du fichier coller. 

Vous avez à présent le fichier *.env* qui définit les variables d'environnement, dont les identifiants de l'utilisateur root. Renseignez donc les identifiants voulus avant de passer à l'étape suivante.

### Utiliser les conteneurs Docker

L'application est divisée en 3 conteneurs :
- Un conteneur pour le serveur du frontend;
- Un conteneur pour le serveur de l'API (backend);
- Un conteneur pour l'hébergement de la base de données.

Ces conteneurs sont gérés par docker-compose via le fichier *docker-compose.yaml* qui contient donc la configuration pour chaque conteneur. Rendez-vous donc dans le répertoire racine du projet.


Si vous souhaitez lancer tout le projet, utilisez simplement la commande suivante :

```bash
docker compose up
```

Si vous ne souhaitez lancer que le back-end (base de données incluse), exécutez la commande :

Cette commande lance la compilation du backend et exécute la base de données.

```bash
docker compose up db backend
```

Et si vous ne souhaitez lancer que le front-end exécuter la commande :

Cette commande exécute *npm install* puis *ng serve*.

```bash
docker compose up frontend
```

Pour démonter tous les conteneurs utilisez la commande :

```bash
docker compose down
```

Si vous avez un problème d'actualisation des données, vous pouvez tenter la commande c-dessous pour rebuild tous les containers.

```bash
docker compose build
```


Il est recommandé d'utiliser les éditeurs et IDE qui permettent de gérer facilement la contenerisation. Voici quelques articles d'aide à propos de Visual Studio Code et IntelliJ :

- [IJ : Run and debug a Spring Boot application using Docker Compose](https://www.jetbrains.com/help/idea/run-and-debug-a-spring-boot-application-using-docker-compose.html)
- [VSCode : Developing inside a Container](https://code.visualstudio.com/docs/devcontainers/containers)

#### Testing

Comming soon

### Backend (API)

Tout le contenu du serveur backend est contenu dans le repertoire */projet-backend*.

### Frontend (APP)

Tout le contenu du serveur backend est contenu dans le repertoire */projet-frontend*.
