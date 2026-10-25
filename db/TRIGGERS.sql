DELIMITER $$
CREATE TRIGGER recibirFecha
BEFORE INSERT
ON proyectos
FOR EACH ROW
BEGIN
	IF (NEW.fecha_fin < NEW.fecha_inicio) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "La fecha de finalización debe ser superior a la fecha inicio";
	ELSE 
		INSERT INTO Proyectos(nombre, descripcion, fecha_inicio, fecha_fin, estado)
        VALUES (NEW.nombre, NEW.descripcion, NEW.fecha_inicio, NEW.estado);
	END IF;
END $$
DELIMITER ;