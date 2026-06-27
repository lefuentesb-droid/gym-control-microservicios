CREATE TABLE metodos_pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(150) NOT NULL,
    activo BOOLEAN NOT NULL
);

CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    socio_id INT NOT NULL,
    metodo_pago_id INT NOT NULL,
    monto INT NOT NULL,
    fecha_pago DATE NOT NULL,
    estado VARCHAR(30) NOT NULL
);

INSERT INTO metodos_pago (nombre, descripcion, activo)
VALUES ('Efectivo', 'Pago realizado en efectivo', true);

INSERT INTO pagos (socio_id, metodo_pago_id, monto, fecha_pago, estado)
VALUES (1, 1, 25000, '2026-06-20', 'PAGADO');


