#Pr�requis:

  -Mysql
  -Tomcat 6

#Proc�dure d'installation:

  Etape 1: Installation de la base de donn�
  Depuis mysql, executer dans l'ordre:
    1. creation_base.sql
    2. creation_user.sql
    3. insert_data.sql
  Les fichiers n�cessaires se trouvent dans le r�pertoire Base � cot� de ce petit fichier readme

  Etape 2: D�ploiement de l'application web
    -copier RED.war dans le dossier webapps de Tomcat
	 note: ne pas se pr�ocuper du context.xml, il se trouve que tomcat est malin
#Lancement de l'application:

Acc�der � http://[adresse de tomcat]/RED depuis votre navigateur pour avoir acc�s � l'application
Exemple avec tomcat sur localhost: http://localhost:8080/RED