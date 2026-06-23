CREATE TABLE metodo_pago (
    id_metodo_pago INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200),
    estado BOOLEAN NOT NULL
);

CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    monto DOUBLE NOT NULL,
    fecha_pago DATE NOT NULL,
    comprobante VARCHAR(50) NOT NULL,
    estado BOOLEAN NOT NULL,
    id_socio INT NOT NULL,
    id_membresia INT NOT NULL,
    id_metodo_pago INT NOT NULL,
    CONSTRAINT fk_pago_metodo_pago FOREIGN KEY (id_metodo_pago) REFERENCES metodo_pago(id_metodo_pago)
);

INSERT INTO metodo_pago (nombre, descripcion, estado)
VALUES ('Tarjeta de crédito', 'Pago con tarjeta de crédito Visa/Mastercard', true);

INSERT INTO pagos (monto, fecha_pago, comprobante, estado, id_socio, id_membresia, id_metodo_pago)
VALUES (29990, CURDATE(), 'CMP-0001', true, 1, 1, 1);
