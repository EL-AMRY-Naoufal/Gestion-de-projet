# Argument pour spécifier la version de Node.js
ARG NODE_VERSION=22.4
FROM node:${NODE_VERSION}-alpine AS base
EXPOSE 4200
EXPOSE 4000

# Définir le répertoire de travail pour le projet
WORKDIR /usr/src/app

# Installer Angular CLI globalement pour le développement
RUN npm install -g @angular/cli --loglevel verbose

# Copie commune du fichier package.json et package-lock.json pour gérer les dépendances
COPY package*.json ./

# Définir le stage pour le développement
FROM base AS development

# Commande pour démarrer le serveur Angular pour le développement
# RUN ["ng", "serve", "--host", "0.0.0.0", "--port", "4200"]

CMD npm install && ng serve --host 0.0.0.0 --port 4200

# Définir le stage pour la production
FROM base AS production

# Copier le reste du code de l'application
COPY . .

# Installer les dépendances de production uniquement et construire l'application Angular en mode production
RUN npm ci --verbose && npm run build -- --configuration prod

# Nettoyer les dépendances de développement pour alléger l'image
RUN npm ci --omit=dev --verbose

# Définir l'environnement de production
ENV NODE_ENV=production

# Utiliser l'utilisateur non-root pour la sécurité
RUN chown -R node:node /usr/src/app
USER node

# Commande pour démarrer l'application en mode production (remplacez selon votre besoin)
CMD ["npm", "run", "serve:ssr:Projet"]