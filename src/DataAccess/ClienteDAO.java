package DataAccess;

import Models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ClienteDAO extends ServicioDB {
    private static final String INSERTAR_CLIENTE = "{CALL SISTEMAFACTURACION.INSERTAR_CLIENTE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_CLIENTE = "{CALL SISTEMAFACTURACION.MODIFICAR_CLIENTE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_CLIENTE = "{CALL SISTEMAFACTURACION.ELIMINAR_CLIENTE(?)}";
    private static final String LISTAR_CLIENTE = "SELECT SISTEMAFACTURACION.LISTAR_CLIENTE FROM DUAL";
    private static final String BUSCAR_CLIENTE_POR_ID = "SELECT SISTEMAFACTURACION.BUSCAR_CLIENTE_POR_ID(?) FROM DUAL";

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
        Cliente clienteExistente;
        try {
            clienteExistente = buscarCliente(cliente.getId());
        } catch (NoDataException e) {
            throw new GlobalException("El Cliente con ID " + cliente.getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Cliente");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_CLIENTE))
        {
            setClienteParameters(pstmt, cliente);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Cliente.");
            } else {
                System.out.println("Cliente modificado exitosamente con ID: " + cliente.getId());
            }
        }
        catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            desconectar();
        }
    }

    public void eliminarCliente(String id) throws GlobalException, NoDataException, SQLException {
        try {
            buscarCliente(id);
        } catch (NoDataException e) {
            throw new GlobalException("El cliente con ID " + id + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Cliente");
        }

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_CLIENTE)) {
            pstmt.setString(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el cliente. Puede estar referenciado en otras tablas.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar Cliente " + e);
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
            throw new NoDataException("La base de datos no se encuentra disponible: " + e.getMessage());
        }

        ArrayList<Cliente> coleccion = new ArrayList<>();

        try (PreparedStatement pstmt = conexion.prepareStatement(LISTAR_CLIENTE); ResultSet rs = pstmt.executeQuery()){
            while (rs.next()) {
                ResultSet clientesCursor = (ResultSet) rs.getObject(1);
                while (clientesCursor.next()) {
                    Cliente cliente = new Cliente(
                            clientesCursor.getString("ID"),
                            clientesCursor.getString("NOMBRE"),
                            clientesCursor.getString("PRIMERAPELLIDO"),
                            clientesCursor.getString("SEGUNDOAPELLIDO"),
                            clientesCursor.getString("GENERO"),
                            clientesCursor.getString("DIRECCION"),
                            clientesCursor.getInt("EDAD"),
                            clientesCursor.getString("CORREOELECTRONICO"),
                            clientesCursor.getString("TELEFONOCASA"),
                            clientesCursor.getString("TELEFONOCELULAR")
                    );
                    coleccion.add(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log full stack trace
            throw new GlobalException("Error al ejecutar consulta: " + e.getMessage());
        } finally {
            try {
                desconectar();
            } catch (SQLException e) {
                System.err.println("Error al desconectar: " + e.getMessage());
            }
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

        try (PreparedStatement pstmt = conexion.prepareStatement(BUSCAR_CLIENTE_POR_ID)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSet clienteCursor = (ResultSet) rs.getObject(1);
                    if (clienteCursor.next()) {
                        cliente = new Cliente(
                            clienteCursor.getString("ID"),
                            clienteCursor.getString("NOMBRE"),
                            clienteCursor.getString("PRIMERAPELLIDO"),
                            clienteCursor.getString("SEGUNDOAPELLIDO"),
                            clienteCursor.getString("GENERO"),
                            clienteCursor.getString("DIRECCION"),
                            clienteCursor.getInt("EDAD"),
                            clienteCursor.getString("CORREOELECTRONICO"),
                            clienteCursor.getString("TELEFONOCASA"),
                            clienteCursor.getString("TELEFONOCELULAR")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida " + e);
        } finally {
            desconectar();
        }

        if (cliente == null) {
            throw new NoDataException("No hay datos");
        }

        return cliente;
    }
}
