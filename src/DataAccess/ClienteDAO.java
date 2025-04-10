package DataAccess;

import Models.Cliente;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ClienteDAO extends ServicioDB {
    private static final String INSERTAR_CLIENTE = "{CALL SISTEMAFACTURACION.INSERTAR_CLIENTE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_CLIENTE = "{CALL SISTEMAFACTURACION.MODIFICAR_CLIENTE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_CLIENTE = "{CALL SISTEMAFACTURACION.ELIMINAR_CLIENTE(?)}";
    private static final String LISTAR_CLIENTE = "{?=CALL SISTEMAFACTURACION.LISTAR_CLIENTE()}";
    private static final String BUSCAR_CLIENTE_POR_ID = "{?=CALL SISTEMAFACTURACION.BUSCAR_CLIENTE_POR_ID(?)}";

    public ClienteDAO() {
        super();
    }

    private void setClienteParameters(CallableStatement pstmt, Cliente cliente) throws SQLException {
        try {
            pstmt.setString(1, cliente.getId());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getPrimerApellido());
            pstmt.setString(4, cliente.getSegundoApellido());
            pstmt.setString(5, cliente.getGenero());
            pstmt.setString(6, cliente.getDireccion());
            pstmt.setInt(7, cliente.getEdad());
            pstmt.setString(8, cliente.getCorreoElectronico());
            pstmt.setString(9, cliente.getTelefonoCasa());
            pstmt.setString(10, cliente.getTelefonoCelular());
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error setting parameters for CallableStatement");
        }
    }

    public void insertarCliente(Cliente cliente) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_CLIENTE)) {
            setClienteParameters(pstmt, cliente);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la operación en el Cliente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            desconectar();
        }
    }

    public void modificarCliente(Cliente cliente) throws GlobalException, NoDataException, SQLException {
        try
        {
            conectar();
        }
        catch (ClassNotFoundException e)
        {
            throw new GlobalException("No se ha localizado el driver");
        }
        catch (SQLException e)
        {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Cliente clienteExistente;
        try {
            clienteExistente = buscarCliente(cliente.getId());
        } catch (NoDataException e) {
            throw new GlobalException("El Maestro con ID " + cliente.getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Maestro");
        }

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_CLIENTE))
        {
            setClienteParameters(pstmt, clienteExistente);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Cliente.");
            } else {
                System.out.println("Cliente modificado exitosamente con ID: " + cliente.getId());
            }
        }
        catch (SQLException e)
        {
            throw new GlobalException("Sentencia no valida");
        }
        finally
        {
            desconectar();
        }
    }

    public void eliminarCliente(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        // Making sure that the client that I want to delete, exists on my DB
        try {
            buscarCliente(id);
        } catch (NoDataException e) {
            throw new GlobalException("El cliente con ID " + id + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Cliente");
        }

        // perform the deletion
        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_CLIENTE)) {
            pstmt.setString(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el cliente. Puede estar referenciado en otras tablas.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al eliminar Cliente");
        } finally {
            desconectar();
        }
    }

    public Collection<Cliente> listarCliente() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ArrayList<Cliente> coleccion = new ArrayList<>();

        try (CallableStatement pstmt = conexion.prepareCall(LISTAR_CLIENTE)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();

            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                while (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getString("ID"),
                            rs.getString("NOMBRE"),
                            rs.getString("PRIMERAPELLIDO"),
                            rs.getString("SEGUNDOAPELLIDO"),
                            rs.getString("GENERO"),
                            rs.getString("DIRECCION"),
                            rs.getInt("EDAD"),
                            rs.getString("CORREOELECTRONICO"),
                            rs.getString("TELEFONOCASA"),
                            rs.getString("TELEFONOCELULAR")
                    );
                    coleccion.add(cliente);
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al ejecutar consulta");
        } finally {
            desconectar();
        }

        if (coleccion.isEmpty()) {
            throw new NoDataException("No hay datos disponibles.");
        }

        return coleccion;
    }

    public Cliente buscarCliente(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Cliente cliente = null;
        try (CallableStatement pstmt = conexion.prepareCall(BUSCAR_CLIENTE_POR_ID)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();

            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getString("ID"),
                            rs.getString("NOMBRE"),
                            rs.getString("PRIMERAPELLIDO"),
                            rs.getString("SEGUNDOAPELLIDO"),
                            rs.getString("GENERO"),
                            rs.getString("DIRECCION"),
                            rs.getInt("EDAD"),
                            rs.getString("CORREOELECTRONICO"),
                            rs.getString("TELEFONOCASA"),
                            rs.getString("TELEFONOCELULAR")
                    );
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida");
        } finally {
            desconectar();
        }

        if (cliente == null) {
            throw new NoDataException("No hay datos");
        }

        return cliente;
    }
}
