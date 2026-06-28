CREATE TABLE entrenadores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    especialidad VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    estado BOOLEAN NOT NULL,
    fecha_registro DATE NOT NULL
);

INSERT INTO entrenadores (nombre, apellido, especialidad, correo, telefono, estado, fecha_registro)
VALUES ('Carlos', 'Rojas', 'Musculación', 'carlos.rojas@gym.cl', '912345678', true, CURRENT_DATE);