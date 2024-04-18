CREATE USER 'AdminGrupo5'@'localhost' IDENTIFIED BY 'AdminGrupo5Password';

GRANT
	SELECT, INSERT, DELETE, UPDATE, DELETE, CREATE, DROP, ALTER, INDEX, REFERENCES
ON bd2_tours_5.* TO 'AdminGrupo5'@'localhost';
