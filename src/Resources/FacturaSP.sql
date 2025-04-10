CREATE SEQUENCE factura_seq START WITH 1 INCREMENT BY 1;


CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

CREATE OR REPLACE PROCEDURE INSERTAR_FACTURA(
    p_maestroId Maestro.consecutivoDeFactura%TYPE,
    p_subtotal Factura.subtotal%TYPE,
    p_IVA Factura.IVA%TYPE,
    p_precioFinal Factura.precioFinal%TYPE,
    p_id OUT Factura.id%TYPE --Para obtener el id de la base de datos y devolverlo al codigo
) AS
BEGIN
    INSERT INTO Factura (id, maestroId, subtotal, IVA, precioFinal)
    VALUES ('F-' || factura_seq.NEXTVAL, p_maestroId, p_subtotal, p_IVA, p_precioFinal)
    RETURNING id INTO p_id;
END INSERTAR_FACTURA;
/

CREATE OR REPLACE PROCEDURE MODIFICAR_FACTURA(
    p_id Factura.id%TYPE,
    p_maestroId Maestro.consecutivoDeFactura%TYPE,
    p_subtotal Factura.subtotal%TYPE,
    p_IVA Factura.IVA%TYPE,
    p_precioFinal Factura.precioFinal%TYPE
) AS
BEGIN
    UPDATE Factura SET 
        maestroId = p_maestroId,
        subtotal = p_subtotal,
        IVA = p_IVA,
        precioFinal = p_precioFinal
    WHERE id = p_id;
END MODIFICAR_FACTURA;
/

CREATE OR REPLACE PROCEDURE ELIMINAR_FACTURA(p_id Factura.id%TYPE) AS
BEGIN
    DELETE FROM Factura WHERE id = p_id;
END ELIMINAR_FACTURA;
/
    
CREATE OR REPLACE FUNCTION BUSCAR_FACTURA_POR_ID(p_id Factura.id%TYPE) 
RETURN Types.ref_cursor 
AS
    factura_cursor types.ref_cursor;
BEGIN
    OPEN factura_cursor FOR
    SELECT * FROM Factura WHERE id = p_id;
RETURN factura_cursor;
END BUSCAR_FACTURA_POR_ID;
/

CREATE OR REPLACE FUNCTION LISTAR_FACTURA
RETURN Types.ref_cursor
AS
    factura_cursor types.ref_cursor;
BEGIN
    OPEN factura_cursor FOR
    SELECT * FROM Factura;
RETURN factura_cursor;
END LISTAR_FACTURA;
/
    
 CREATE OR REPLACE PROCEDURE OBTENER_SUBTOTAL_FACTURA(
    p_factura_id IN VARCHAR2,
    p_subtotal OUT NUMBER
) AS
BEGIN
    SELECT SUM(d.CANTIDAD * p.PRECIOPORUNIDAD)
    INTO p_subtotal
    FROM DETALLE d
    JOIN PRODUCTO p ON d.PRODUCTOID= p.ID
    WHERE d.FACTURAID= p_factura_id;
END;
/