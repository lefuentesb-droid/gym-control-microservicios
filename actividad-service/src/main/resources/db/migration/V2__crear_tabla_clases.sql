CREATE TABLE clases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_entrenador INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    cupo_maximo INT NOT NULL,
    duracion_minutos INT NOT NULL,
    horario VARCHAR(20) NOT NULL,
    dias_semana VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT fk_clase_entrenador
        FOREIGN KEY (id_entrenador)
        REFERENCES entrenadores(id)
);