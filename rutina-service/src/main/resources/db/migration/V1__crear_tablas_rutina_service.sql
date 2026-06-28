CREATE TABLE ejercicios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    grupo_muscular VARCHAR(50) NOT NULL,
    dificultad VARCHAR(20) NOT NULL,
    video_url VARCHAR(255),
    estado BOOLEAN NOT NULL
);

CREATE TABLE rutinas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_socio INT NOT NULL,
    id_entrenador INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    fecha_asignacion DATE NOT NULL,
    estado BOOLEAN NOT NULL
);

CREATE TABLE rutina_ejercicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_rutina INT NOT NULL,
    id_ejercicio INT NOT NULL,
    series INT NOT NULL,
    repeticiones INT NOT NULL,
    descanso_segundos INT NOT NULL,
    orden INT NOT NULL,
    observaciones VARCHAR(255),
    CONSTRAINT fk_rutina_ejercicio_rutina FOREIGN KEY (id_rutina) REFERENCES rutinas(id),
    CONSTRAINT fk_rutina_ejercicio_ejercicio FOREIGN KEY (id_ejercicio) REFERENCES ejercicios(id)
);

INSERT INTO ejercicios (nombre, descripcion, grupo_muscular, dificultad, video_url, estado)
VALUES ('Sentadilla', 'Ejercicio de fuerza para tren inferior', 'Piernas', 'MEDIA', NULL, true);

INSERT INTO rutinas (id_socio, id_entrenador, nombre, descripcion, fecha_asignacion, estado)
VALUES (1, 1, 'Rutina de iniciacion', 'Rutina basica de 1 dia enfocada en tren inferior', CURDATE(), true);

INSERT INTO rutina_ejercicio (id_rutina, id_ejercicio, series, repeticiones, descanso_segundos, orden, observaciones)
VALUES (1, 1, 3, 12, 60, 1, 'Mantener la espalda recta durante el movimiento');
