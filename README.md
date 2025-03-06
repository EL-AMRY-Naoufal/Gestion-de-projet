# Gestion de projet : EduPlanner

## Présentation

EduPlanner est une application destinée à la gestion des affectations horraires d'enseignants. Elle propose divers fonctionnalités dans ce but.

### Fonctionnalités 

- S'authentifier / Se déconnecter;
- Gérer les comptes (Consulter, Créer, Modifier les comptes et les enseignants);
- Gérer les modules (Consulter, Créer, Modifier l'arborescence);
- Gérer les affectation des enseignants (Consulter, Créer, Modifier les affectations des enseignants aux modules).

## Technologoies utilisées

- Angular (frontend)
- Java Spring Boot (backend)
- Postgres (base de données)
- Docker (Conteneurisation)

## Déploiement en production

À venir

## Installation de l'environnement de dev

### Prérequis

On utilisera docker pour assurer la compatibilité entre tous les environnements de développement.

- [Docker v27+](https://docs.docker.com/get-started/get-docker/) 

### Configuration de la base de données

Avant de la créer, il faut assigner un nom d'utilisateur et un mot de passe pour l'utilisateur root de la base de données. Pour renseigner ceux-ci, il faut copier-coller le fichier *.env.example* et enlever l'extension *.example* du fichier coller. 

Vous avez à présent le fichier *.env* qui définit les variables d'environnement, dont les identifiants de l'utilisateur root. Renseignez donc les identifiants voulus avant de passer à l'étape suivante.

### Utiliser les conteneurs Docker

L'application est divisée en 3 conteneurs :
- Un conteneur pour le serveur du frontend;
- Un conteneur pour le serveur de l'API (backend);
- Un conteneur pour l'hébergement de la base de données.

Ces conteneurs sont gérés par docker-compose via le fichier *docker-compose.yaml* qui contient donc la configuration pour chaque conteneur. Rendez-vous donc dans le répertoire racine du projet.


Si vous souhaitez lancer tout le projet, utilisez simplement la commande suivante (--watch pour l'actualisation en direct du frontend) :

```bash
docker compose up --watch
```

Si vous ne souhaitez lancer que le back-end (base de données incluse), exécutez la commande :

Cette commande lance la compilation du backend et exécute la base de données.

```bash
docker compose up backend
```

Et si vous ne souhaitez lancer que le front-end exécuter la commande :

Cette commande exécute *npm install* puis *ng serve*.

```bash
docker compose up frontend --watch
```

Pour démonter tous les conteneurs utilisez la commande :

```bash
docker compose down
```

Si vous avez un problème d'actualisation des données (ce qui peut arriver dans certains cas comme l'ajout de dépendances, etc...), vous pouvez tenter la commande ci-dessous pour rebuild tous les containers.

```bash
docker compose build
```

Ou si vous voulez être sûr que les conteneurs soient rebuild à chaque fois (un peu long à relancer à chaque fois)

```bash
docker compose up --build
```

Il est recommandé d'utiliser les éditeurs et IDE qui permettent de gérer facilement la contenerisation. Voici quelques articles d'aide à propos de Visual Studio Code et IntelliJ :

- [IJ : Run and debug a Spring Boot application using Docker Compose](https://www.jetbrains.com/help/idea/run-and-debug-a-spring-boot-application-using-docker-compose.html)
- [VSCode : Developing inside a Container](https://code.visualstudio.com/docs/devcontainers/containers)

#### Testing

Pour le testing vous pouvez simplement les lancer en local sur votre machine (pas de CI pour l'instant) via votre IDE par exemple.

### Backend (API)

Tout le contenu du serveur backend est contenu dans le repertoire */projet-backend*.

### Frontend (APP)

Tout le contenu du serveur backend est contenu dans le repertoire */projet-frontend*.
