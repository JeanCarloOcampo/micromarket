CREATE DATABASE IF NOT EXISTS micromarket_db;
USE micromarket_db;

-- Módulo I
CREATE TABLE IF NOT EXISTS categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    codigo_barras VARCHAR(255) NOT NULL UNIQUE,
    precio DOUBLE NOT NULL,
    stock INT DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

-- Módulo II
CREATE TABLE IF NOT EXISTS proveedores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    nit VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(255),
    direccion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS producto_proveedor (
    producto_id BIGINT NOT NULL,
    proveedor_id BIGINT NOT NULL,
    PRIMARY KEY (producto_id, proveedor_id),
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
);

-- Módulo III
CREATE TABLE IF NOT EXISTS empleados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    cargo VARCHAR(255) NOT NULL,
    fecha_ingreso DATE NOT NULL,
    salario DOUBLE NOT NULL
);

-- Módulo IV
CREATE TABLE IF NOT EXISTS ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta DATETIME,
    subtotal DOUBLE,
    iva DOUBLE,
    total DOUBLE,
    empleado_id BIGINT NOT NULL,
    FOREIGN KEY (empleado_id) REFERENCES empleados(id)
);

CREATE TABLE IF NOT EXISTS detalle_venta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE,
    subtotal_linea DOUBLE,
    venta_id BIGINT,
    producto_id BIGINT,
    FOREIGN KEY (venta_id) REFERENCES ventas(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);