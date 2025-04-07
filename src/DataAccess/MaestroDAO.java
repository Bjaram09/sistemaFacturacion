package DataAccess;

import Models.Cliente;
import Models.Maestro;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MaestroDAO extends ServicioDB {
    private static final String INSERTAR_MAESTRO = "{CALL SISTEMAFACTURACION.INSERTAR_MAESTRO(?, ?, ?)}";
    private static final String MODIFICAR_MAESTRO = "{CALL SISTEMAFACTURACION.MODIFICAR_MAESTRO(?, ?, ?)}";
    private static final String ELIMINAR_MAESTRO = "{CALL SISTEMAFACTURACION.ELIMINAR_MAESTRO(?)}";
    private static final String LISTAR_MAESTRO = "{?=CALL SISTEMAFACTURACION.LISTAR_MAESTRO()}";
    private static final String BUSCAR_MAESTRO_POR_ID = "{?=CALL SISTEMAFACTURACION.BUSCAR_MAESTRO_POR_ID(?)}";

    ClienteDAO clienteDAO = new ClienteDAO();

    public MaestroDAO() {
        super();
    }

    public void insertarMaestro(Maestro maestro) throws GlobalException, NoDataException, SQLException {
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

        Cliente clienteConsultado;

        try {
            clienteConsultado = clienteDAO.buscarCliente(maestro.getInfoCliente().getId());
        } catch (NoDataException e) {
            throw new GlobalException("El cliente con ID " + maestro.getInfoCliente().getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar cliente");
        }

        try (CallableStatement pstmt  = conexion.prepareCall(INSERTAR_MAESTRO))
        {
            pstmt.setString(1, maestro.getConsecutivoDeFactura());
            pstmt.setString(2, maestro.getCedulaJuridica());
            pstmt.setString(3, clienteConsultado.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la inserción del Maestro.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        }
        finally
        {
            desconectar();
        }
    }

    public void modificarMaestro(Maestro maestro) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        // Make sure that a Maestro exists on my DB
        Maestro maestroExistente;
        try {
            maestroExistente = buscarMaestro(maestro.getConsecutivoDeFactura());
        } catch (NoDataException e) {
            throw new GlobalException("El Maestro con ID " + maestro.getConsecutivoDeFactura() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Maestro");
        }

        // Making sure that my cliente exists on my DB
        Cliente clienteConsultado;
        try {
            clienteConsultado = clienteDAO.buscarCliente(maestro.getInfoCliente().getId());
        } catch (NoDataException e) {
            throw new GlobalException("El cliente con ID " + maestro.getInfoCliente().getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Cliente");
        }

        // Now knowing that both my maestro and client exists on my DB, then I can update them
        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_MAESTRO)) {
            pstmt.setString(1, maestro.getConsecutivoDeFactura());
            pstmt.setString(2, maestro.getCedulaJuridica());
            pstmt.setString(3, clienteConsultado.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Maestro.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al modificar Maestro");
        } finally {
            desconectar();
        }
    }

    public void eliminarMaestro(String id) throws GlobalException, NoDataException {
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
        CallableStatement pstmt = null;
        try
        {
            pstmt = conexion.prepareCall(ELIMINAR_MAESTRO);
            pstmt.setString(1, id);

            int resultado = pstmt.executeUpdate();

            if (resultado != 0)
            {
                throw new NoDataException("No se realizo el borrado");
            }
            else
            {
                System.out.println("\nEliminaci�n Satisfactoria!");
            }
        }
        catch (SQLException e)
        {
            throw new GlobalException("Sentencia no valida");
        }
        finally
        {
            try
            {
                if (pstmt != null)
                {
                    pstmt.close();
                }
                desconectar();
            }
            catch (SQLException e)
            {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public Collection<Maestro> listarMaestro() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ArrayList<Maestro> coleccion = new ArrayList<>();

        try (CallableStatement pstmt = conexion.prepareCall(LISTAR_MAESTRO))
        {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();

            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                while (rs.next()) {
                    String clienteId = rs.getString("CLIENTEID");
                    Cliente clienteConsultado = clienteDAO.buscarCliente(clienteId);

                    Maestro maestro = new Maestro(
                            rs.getString("CONSECUTIVODEFACTURA"),
                            rs.getString("CEDULAJURIDICA"),
                            clienteConsultado
                    ); // Pass the full Cliente object, not just the ID
                    coleccion.add(maestro);
                }
            }
        }
        catch (SQLException e) {
            throw new GlobalException("Error al ejecutar consulta");
        } finally {
            desconectar();
        }

        if (coleccion.isEmpty()) {
            throw new NoDataException("No hay datos disponibles.");
        }

        return coleccion;
    }

    public Maestro buscarMaestro(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Maestro maestro = null;
        try (CallableStatement pstmt = conexion.prepareCall(BUSCAR_MAESTRO_POR_ID)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();

            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                if (rs.next()) {
                    // Fetch the Cliente object instead of just the ID
                    String clienteId = rs.getString("CLIENTEID");
                    Cliente clienteConsultado = clienteDAO.buscarCliente(clienteId);

                    maestro = new Maestro(
                            rs.getString("CONSECUTIVODEFACTURA"),
                            rs.getString("CEDULAJURIDICA"),
                            clienteConsultado // Pass the full Cliente object, not just the ID
                    );
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida");
        } finally {
            desconectar();
        }

        if (maestro == null) {
            throw new NoDataException("No hay datos");
        }

        return maestro;
    }
    
    
}
