DELIMITER $$
CREATE TRIGGER trg_validar_Fecha_inicio_finalizacion
BEFORE INSERT
ON proyectos
FOR EACH ROW
BEGIN
	IF (NEW.fecha_fin < NEW.fecha_inicio) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "La fecha de finalización debe ser superior a la fecha inicio";
	END IF;
END $$
DELIMITER ;