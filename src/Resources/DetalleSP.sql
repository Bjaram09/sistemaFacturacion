CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

CREATE OR REPLACE PROCEDURE INSERTAR_DETALLE(
    p_id Detalle.id%TYPE,
    p_productoId Producto.id%TYPE,
    p_cantidad Detalle.cantidad%TYPE,
    p_facturaId Factura.id%TYPE
) AS
BEGIN
    INSERT INTO Detalle VALUES (p_id, p_productoId, p_cantidad, p_facturaId);
END INSERTAR_DETALLE;
/

CREATE OR REPLACE PROCEDURE MODIFICAR_DETALLE(
    p_id Detalle.id%TYPE,
    p_productoId Producto.id%TYPE,
    p_cantidad Detalle.cantidad%TYPE,
    p_facturaId Factura.id%TYPE
) AS
BEGIN
    UPDATE Detalle SET 
        productoId = p_productoId,
        cantidad = p_cantidad,
        facturaId = p_facturaId
    WHERE id = p_id;
END MODIFICAR_DETALLE;
/

CREATE OR REPLACE PROCEDURE ELIMINAR_DETALLE(p_id Detalle.id%TYPE) AS
BEGIN
    DELETE FROM Detalle WHERE id = p_id;
END ELIMINAR_DETALLE;
/

CREATE OR REPLACE FUNCTION BUSCAR_DETALLE_POR_ID(p_id Detalle.id%TYPE)
RETURN Types.ref_cursor
AS
    detalle_cursor types.ref_cursor;
BEGIN
    OPEN detalle_cursor FOR
    SELECT * FROM Detalle WHERE id = p_id;
RETURN detalle_cursor;
END;
/

CREATE OR REPLACE FUNCTION LISTAR_DETALLE
RETURN Types.ref_cursor
AS
    detalle_cursor types.ref_cursor;
BEGIN
    OPEN detalle_cursor FOR
    SELECT * FROM Detalle;
RETURN detalle_cursor;
END;
/
