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

DELIMITER $$
CREATE TRIGGER tgr_eliminar_empleado_sin_asignacion
BEFORE DELETE
ON Empleados
FOR EACH ROW
BEGIN
	DECLARE cantidad_proyectos_activos int;
    
	/* Ver si tiene asignaciones activas*/
	SELECT COUNT(*) INTO cantidad_proyectos_activos
    FROM Empleado_Proyecto
    WHERE empleado_id = OLD.id;
    
    IF (cantidad_proyectos_activos > 0) THEN 
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Error: No se puede eliminar al empleado porque está asiganado a uno o más proyectos";
	END IF;
END $$
DELIMITER ;