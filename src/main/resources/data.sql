INSERT IGNORE INTO categorias (id, nombre, descripcion) VALUES
(1, 'Lácteos', 'Productos derivados de la leche'),
(2, 'Bebidas', 'Jugos, gaseosas y agua'),
(3, 'Aseo', 'Productos de limpieza y aseo personal'),
(4, 'Snacks', 'Mecatos y pasabocas');

INSERT IGNORE INTO productos (id, nombre, codigo_barras, precio, stock, activo, categoria_id) VALUES
(1, 'Leche Entera 1L',      '7702001001001', 3500.0,  50, true, 1),
(2, 'Yogur Fresa 200g',     '7702001001002', 2800.0,  30, true, 1),
(3, 'Jugo de Naranja 1L',   '7702001002001', 4200.0,  40, true, 2),
(4, 'Agua Mineral 600ml',   '7702001002002', 1500.0, 100, true, 2),
(5, 'Jabón de Manos 250ml', '7702001003001', 6000.0,  20, true, 3),
(6, 'Papas Fritas 100g',    '7702001004001', 3000.0,  60, true, 4),
(7, 'Producto Inactivo',    '7702001000001',  500.0,   0, false, 4);

INSERT IGNORE INTO proveedores (id, nombre, nit, telefono, direccion) VALUES
(1, 'Lácteos del Valle S.A.',   '900100200-1', '3001234567', 'Calle 10 # 20-30, Cali'),
(2, 'Distribuidora BebiMax',    '800300400-2', '3107654321', 'Carrera 5 # 15-40, Medellín'),
(3, 'Proveedora CleanPro',      '901500600-3', '3205551234', 'Av. 68 # 22-10, Bogotá');

INSERT IGNORE INTO producto_proveedor (producto_id, proveedor_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 3),
(6, 2);

INSERT IGNORE INTO empleados (id, cedula, nombre, cargo, fecha_ingreso, salario) VALUES
(1, '1001001001', 'Carlos Ramírez',   'ADMINISTRADOR', '2022-01-15', 3500000.0),
(2, '1002002002', 'Laura Gómez',      'CAJERO',        '2023-03-20', 1800000.0),
(3, '1003003003', 'Andrés Martínez',  'AUXILIAR',      '2023-06-01', 1300000.0),
(4, '1004004004', 'Sofía Torres',     'CAJERO',        '2024-01-10', 1800000.0);
