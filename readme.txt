#Prérequis:

  -Mysql
  -Tomcat 6

#Procédure d'installation:

  Etape 1: Installation de la base de donné
  Depuis mysql, executer dans l'ordre:
    1. creation_base.sql
    2. creation_user.sql
    3. insert_data.sql
  Les fichiers nécessaires se trouvent dans le répertoire Base à coté de ce petit fichier readme

  Etape 2: Déploiement de l'application web
    -copier RED.war dans le dossier webapps de Tomcat
	 note: ne pas se préocuper du context.xml, il se trouve que tomcat est malin
#Lancement de l'application:

Accèder à http://[adresse de tomcat]/RED depuis votre navigateur pour avoir accès à l'application
Exemple avec tomcat sur localhost: http://localhost:8080/RED