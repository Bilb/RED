CREATE DATABASE schoolFormation;
USE schoolFormation;

CREATE TABLE LOCATION (
	ID 			INT(8)	 		NOT NULL AUTO_INCREMENT		,
	CITY 		VARCHAR(30) 	NOT NULL					,
	PRIMARY KEY (ID)	
);	

CREATE TABLE COURSE (	
	CODE 		CHAR(8) 		NOT NULL					,
	TITLE 		VARCHAR(30) 	NOT NULL					,
	PRIMARY KEY (CODE)	
);	
	
CREATE TABLE COURSE_SESSION (	
	ID 			INT(8)			NOT NULL AUTO_INCREMENT		,
	START 		DATETIME	 	NOT NULL					,
	END 		DATETIME	 	NOT NULL					,
	COURSE_CODE CHAR(8)			NOT NULL					,
	LOCATION_ID INT(8)			NOT NULL					,
	PRIMARY KEY (ID)										,
	FOREIGN KEY (COURSE_CODE) REFERENCES COURSE(CODE)		,
	FOREIGN KEY	(LOCATION_ID) REFERENCES LOCATION(ID)	
);	
	
CREATE TABLE CLIENT (	
	ID 			INT(8)			NOT NULL AUTO_INCREMENT	,
	LASTNAME	VARCHAR(20)		NOT NULL					,
	FIRSTNAME	VARCHAR(20)		NOT NULL					,
	ADDRESS		VARCHAR(40)		NOT NULL					,
	PHONE		VARCHAR(8)		NOT NULL					,
	EMAIL 		VARCHAR(30)		NOT NULL					,
	SESSION_ID	INT(8)			NOT NULL					,
	PRIMARY KEY (ID)										,
	FOREIGN KEY (SESSION_ID) REFERENCES COURSE_SESSION(ID)	
);