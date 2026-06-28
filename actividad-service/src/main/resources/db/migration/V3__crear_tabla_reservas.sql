CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_reserva DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    id_clase INT NOT NULL,
    id_socio INT NOT NULL,
    CONSTRAINT fk_reserva_clase
        FOREIGN KEY (id_clase)
        REFERENCES clases(id)
);