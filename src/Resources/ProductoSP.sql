CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

CREATE OR REPLACE PROCEDURE INSERTAR_PRODUCTO(
    p_id Producto.id%TYPE,
    p_descripcion Producto.descripcion%TYPE,
    p_minCantidad Producto.minCantidad%TYPE,
    p_maxCantidad Producto.maxCantidad%TYPE,
    p_precioPorUnidad Producto.precioPorUnidad%TYPE
) AS
BEGIN
    INSERT INTO Producto VALUES (p_id, p_descripcion, p_minCantidad, p_maxCantidad, p_precioPorUnidad);
END INSERTAR_PRODUCTO;
/

CREATE OR REPLACE PROCEDURE MODIFICAR_PRODUCTO(
    p_id Producto.id%TYPE,
    p_descripcion Producto.descripcion%TYPE,
    p_minCantidad Producto.minCantidad%TYPE,
    p_maxCantidad Producto.maxCantidad%TYPE,
    p_precioPorUnidad Producto.precioPorUnidad%TYPE
) AS
BEGIN
    UPDATE Producto SET
        descripcion = p_descripcion,
        minCantidad = p_minCantidad,
        maxCantidad = p_maxCantidad,
        precioPorUnidad = p_precioPorUnidad
    WHERE id = p_id;
END MODIFICAR_PRODUCTO;
/

CREATE OR REPLACE PROCEDURE ELIMINAR_PRODUCTO(p_id Producto.id%TYPE) AS
BEGIN
    DELETE FROM Producto WHERE id = p_id;
END ELIMINAR_PRODUCTO;
/
    
CREATE OR REPLACE FUNCTION BUSCAR_PRODUCTO_POR_ID(p_id Producto.id%TYPE)
RETURN Types.ref_cursor
AS
    Producto_cursor types.ref_cursor;
BEGIN
    OPEN Producto_cursor FOR
    SELECT * FROM Producto WHERE id = p_id;
RETURN Producto_cursor;
END;
/

CREATE OR REPLACE FUNCTION LISTAR_PRODUCTO
RETURN Types.ref_cursor
AS
    Producto_cursor types.ref_cursor;
BEGIN
    OPEN PRODUCTO_cursor FOR
    SELECT * FROM Producto;
RETURN Producto_cursor;
END;
/