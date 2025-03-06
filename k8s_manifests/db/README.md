####      DEPLOYER LA DB    #############

STEP 1 : Déployer le postgres --------------> kubectl apply -f ./
STEP 2 : Déployer le pgadmin --------------> kubectl apply -f ./
STEP 3 : Accéder à l'interface de pgadmin avec to port fowarding --------------> kubectl port-forward service/pgadmin 8080:80
STEP 4 : Se connecter a la postgres depuis l'interface de pgadmin 
    Nom de serveur : Mettez ce que vous voulez
    Hostname : postgres.default.svc.cluster.local
    Port : 5432
    Username : postgres
    Password : root

