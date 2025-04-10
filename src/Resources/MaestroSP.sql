CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

CREATE OR REPLACE PROCEDURE INSERTAR_MAESTRO(
    p_consecutivoDeFactura Maestro.consecutivoDeFactura%TYPE,
    p_cedulaJuridica Maestro.cedulaJuridica%TYPE,
    p_clienteId Cliente.id%TYPE
) AS
BEGIN
    INSERT INTO Maestro VALUES (p_consecutivoDeFactura, p_cedulaJuridica, p_clienteId);
END INSERTAR_MAESTRO;
/

CREATE OR REPLACE PROCEDURE MODIFICAR_MAESTRO(
    p_consecutivoDeFactura Maestro.consecutivoDeFactura%TYPE,
    p_cedulaJuridica Maestro.cedulaJuridica%TYPE,
    p_clienteId Cliente.id%TYPE
) AS
BEGIN
    UPDATE Maestro SET 
        cedulaJuridica = p_cedulaJuridica,
        clienteId = p_clienteId
    WHERE consecutivoDeFactura = p_consecutivoDeFactura;
END MODIFICAR_MAESTRO;
/

CREATE OR REPLACE PROCEDURE ELIMINAR_MAESTRO(p_consecutivoDeFactura Maestro.consecutivoDeFactura%TYPE) AS
BEGIN
    DELETE FROM Maestro WHERE consecutivoDeFactura = p_consecutivoDeFactura;
END ELIMINAR_MAESTRO;
/
    
CREATE OR REPLACE FUNCTION BUSCAR_MAESTRO_POR_ID(p_consecutivoDeFactura Maestro.consecutivoDeFactura%TYPE)
RETURN Types.ref_cursor
AS
    maestro_cursor types.ref_cursor;
BEGIN
    OPEN maestro_cursor FOR
    SELECT * FROM Maestro WHERE consecutivoDeFactura = p_consecutivoDeFactura;
RETURN maestro_cursor;
END;
/

CREATE OR REPLACE FUNCTION LISTAR_MAESTRO
RETURN Types.ref_cursor
AS
    maestro_cursor types.ref_cursor;
BEGIN
    OPEN maestro_cursor FOR
    SELECT * FROM Maestro;
RETURN maestro_cursor;
END;
/