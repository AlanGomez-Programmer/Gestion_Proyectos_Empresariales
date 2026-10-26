CREATE DATABASE IF NOT EXISTS proyectos_empresariales;
USE proyectos_empresariales;

CREATE TABLE Empleados (
    id INT AUTO_INCREMENT,
    nombre VARCHAR(75) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    cargo VARCHAR(75) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Proyectos (
    id INT AUTO_INCREMENT,
    nombre VARCHAR(75) NOT NULL UNIQUE,
    descripcion VARCHAR(200) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado ENUM("PLANIFICADO", "EN_CURSO", "FINALIZADO") NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Empleado_Proyecto (
    empleado_id INT NOT NULL,
    proyecto_id INT NOT NULL,
    rol VARCHAR(50) NOT NULL,
    fecha_asignacion DATE NOT NULL,
    horas_semanales INT NOT NULL CHECK(horas_semanales > 0 AND horas_semanales < 40),
    FOREIGN KEY (empleado_id) REFERENCES Empleados(id),
    FOREIGN KEY (proyecto_id) REFERENCES Proyectos(id)
);