CREATE USER IF NOT EXISTS 'AdminGrupo5'@'%' IDENTIFIED BY 'AdminGrupo5Password';

CREATE DATABASE IF NOT EXISTS bd2_tours_5;

GRANT
	SELECT, INSERT, DELETE, UPDATE, DELETE, CREATE, DROP, ALTER, INDEX, REFERENCES
ON bd2_tours_5.* TO 'AdminGrupo5'@'%';
