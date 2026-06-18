CREATE TABLE socios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(12) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    estado BOOLEAN NOT NULL,
    fecha_registro DATE NOT NULL
);

INSERT INTO socios (rut,nombre,apellido,correo,telefono,direccion,fecha_nacimiento,estado,fecha_registro)
VALUES ('12345678-9','leo','fuentes','leo@gmail.com','912345678','Maipu','2000-05-10',true,CURDATE());

