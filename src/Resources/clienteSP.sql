CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/
    
CREATE OR REPLACE PROCEDURE INSERTAR_CLIENTE(
    p_id Cliente.id%TYPE,
    p_nombre Cliente.nombre%TYPE,
    p_primerApellido Cliente.primerApellido%TYPE,
    p_segundoApellido Cliente.segundoApellido%TYPE,
    p_genero Cliente.genero%TYPE,
    p_direccion Cliente.direccion%TYPE,
    p_edad Cliente.edad%TYPE,
    p_correoElectronico Cliente.correoElectronico%TYPE,
    p_telefonoCasa Cliente.telefonoCasa%TYPE,
    p_telefonoCelular Cliente.telefonoCelular%TYPE
) AS
BEGIN
    INSERT INTO Cliente VALUES (p_id, p_nombre, p_primerApellido, p_segundoApellido, p_genero, p_direccion, p_edad, p_correoElectronico, p_telefonoCasa, p_telefonoCelular);
END INSERTAR_CLIENTE;
/

CREATE OR REPLACE PROCEDURE MODIFICAR_CLIENTE(
    p_id Cliente.id%TYPE,
    p_nombre Cliente.nombre%TYPE,
    p_primerApellido Cliente.primerApellido%TYPE,
    p_segundoApellido Cliente.segundoApellido%TYPE,
    p_genero Cliente.genero%TYPE,
    p_direccion Cliente.direccion%TYPE,
    p_edad Cliente.edad%TYPE,
    p_correoElectronico Cliente.correoElectronico%TYPE,
    p_telefonoCasa Cliente.telefonoCasa%TYPE,
    p_telefonoCelular Cliente.telefonoCelular%TYPE
) AS
BEGIN
    UPDATE Cliente SET 
        nombre = p_nombre,
        primerApellido = p_primerApellido,
        segundoApellido = p_segundoApellido,
        genero = p_genero,
        direccion = p_direccion,
        edad = p_edad,
        correoElectronico = p_correoElectronico,
        telefonoCasa = p_telefonoCasa,
        telefonoCelular = p_telefonoCelular
    WHERE id = p_id;
END MODIFICAR_CLIENTE;
/

CREATE OR REPLACE PROCEDURE ELIMINAR_CLIENTE(p_id Cliente.id%TYPE) AS
BEGIN
    DELETE FROM Cliente WHERE id = p_id;
END ELIMINAR_CLIENTE;
/
    
CREATE OR REPLACE FUNCTION BUSCAR_CLIENTE_POR_ID(p_id Cliente.id%TYPE)
RETURN Types.ref_cursor
AS
    cliente_cursor types.ref_cursor;
BEGIN
    OPEN cliente_cursor FOR
    SELECT * FROM Cliente WHERE id = p_id;
RETURN cliente_cursor;
END;
/

CREATE OR REPLACE FUNCTION LISTAR_CLIENTE
RETURN Types.ref_cursor
AS
    cliente_cursor types.ref_cursor;
BEGIN
    OPEN cliente_cursor FOR
    SELECT * FROM Cliente;
RETURN cliente_cursor;
END;
/