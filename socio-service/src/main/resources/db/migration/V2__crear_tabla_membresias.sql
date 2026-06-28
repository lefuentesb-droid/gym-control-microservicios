CREATE TABLE membresia (
    idmembresia INT AUTO_INCREMENT PRIMARY KEY,
    id_socio INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    observaciones VARCHAR(200),
    CONSTRAINT fk_membresia_socio
        FOREIGN KEY (id_socio)
        REFERENCES socios(id)
);