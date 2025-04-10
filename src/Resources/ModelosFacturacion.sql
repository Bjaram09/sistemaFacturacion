CREATE TABLE Cliente (
    id VARCHAR2(50) PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    primerApellido VARCHAR2(100) NOT NULL,
    segundoApellido VARCHAR2(100),
    genero CHAR(1) CHECK (genero IN ('M', 'F', 'O')),
    direccion VARCHAR2(255),
    edad NUMBER(3) CHECK (edad >= 0),
    correoElectronico VARCHAR2(150) UNIQUE,
    telefonoCasa VARCHAR2(20),
    telefonoCelular VARCHAR2(20)
);

CREATE TABLE Producto (
    id VARCHAR2(50) PRIMARY KEY,
    descripcion VARCHAR2(255) NOT NULL,
    minCantidad NUMBER(10) CHECK (minCantidad >= 0),
    maxCantidad NUMBER(10) CHECK (maxCantidad >= 0),
    precioPorUnidad NUMBER(10,2) CHECK (precioPorUnidad >= 0)
);

CREATE TABLE Maestro (
    consecutivoDeFactura VARCHAR2(50) PRIMARY KEY,
    cedulaJuridica VARCHAR2(50) NOT NULL,
    clienteId VARCHAR2(50) NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (clienteId) REFERENCES Cliente(id)
);

CREATE TABLE Detalle (
    id VARCHAR2(50) PRIMARY KEY,
    productoId VARCHAR2(50) NOT NULL,
    cantidad NUMBER(10) CHECK (cantidad >= 0),
    facturaId VARCHAR2(50) NOT NULL,
    CONSTRAINT fk_producto FOREIGN KEY (productoId) REFERENCES Producto(id),
    CONSTRAINT fk_factura FOREIGN KEY (facturaId) REFERENCES Factura(id)
);

CREATE TABLE Factura (
    id VARCHAR2(50) PRIMARY KEY,
    maestroId VARCHAR2(50) NOT NULL,
    subtotal NUMBER(10,2) CHECK (subtotal >= 0),
    IVA NUMBER(10,2) CHECK (IVA >= 0),
    precioFinal NUMBER(10,2) CHECK (precioFinal >= 0),
    CONSTRAINT fk_maestro FOREIGN KEY (maestroId) REFERENCES Maestro(consecutivoDeFactura)
);