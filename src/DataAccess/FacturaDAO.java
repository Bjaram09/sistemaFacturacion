package DataAccess;

import Models.Factura;
import Models.Maestro;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class FacturaDAO extends ServicioDB {
    private static final String INSERTAR_FACTURA = "{CALL SISTEMAFACTURACION.INSERTAR_FACTURA(?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_FACTURA = "{CALL SISTEMAFACTURACION.MODIFICAR_FACTURA(?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_FACTURA = "{CALL SISTEMAFACTURACION.ELIMINAR_FACTURA(?)}";
    private static final String LISTAR_FACTURA = "{?=CALL SISTEMAFACTURACION.LISTAR_FACTURA()}";
    private static final String BUSCAR_FACTURA_POR_ID = "{?=CALL SISTEMAFACTURACION.BUSCAR_FACTURA_POR_ID(?)}";
    private static final String OBTENER_SUBTOTAL_FACTURA = "{CALL SISTEMAFACTURACION.OBTENER_SUBTOTAL_FACTURA(?, ?)}";

    MaestroDAO maestroDAO = new MaestroDAO();

    public FacturaDAO() {
        super();
    }

    public double calcularSubtotalFactura(int facturaId) throws SQLException, GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try (CallableStatement pstmt = conexion.prepareCall(OBTENER_SUBTOTAL_FACTURA)) {
            pstmt.setInt(1, facturaId);
            pstmt.registerOutParameter(2, java.sql.Types.NUMERIC);

            pstmt.execute();
            return pstmt.getDouble(2);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("No se pudo obtener el subtotal de la factura");
        } finally {
            desconectar();
        }
    }

    public int insertarFactura(Factura factura) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Maestro maestroConsultado;
        try {
            maestroConsultado = maestroDAO.buscarMaestro(factura.getMaestro().getConsecutivoDeFactura());
        } catch (NoDataException e) {
            throw new GlobalException("El Maestro con ID " + factura.getMaestro().getConsecutivoDeFactura() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar maestro");
        }

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_FACTURA)) {
            pstmt.setString(1, maestroConsultado.getConsecutivoDeFactura());
            pstmt.setDouble(2, 0);
            pstmt.setDouble(3, factura.getIVA());
            pstmt.setDouble(4, 0);
            pstmt.registerOutParameter(5, java.sql.Types.NUMERIC);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la inserción de la factura.");
            }

            return pstmt.getInt(5);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al insertar Factura: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    public void modificarFactura(Factura factura) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Factura facturaExistente;
        try {
            facturaExistente = buscarFacturaPorId(factura.getId());
        } catch (NoDataException e) {
            throw new GlobalException("La Factura con ID " + factura.getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Factura");
        }

        Maestro maestroConsultado;
        try {
            maestroConsultado = maestroDAO.buscarMaestro(factura.getMaestro().getConsecutivoDeFactura());
        } catch (NoDataException e) {
            throw new GlobalException("El Maestro con ID " + factura.getMaestro().getConsecutivoDeFactura() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Maestro");
        }

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_FACTURA)) {
            pstmt.setString(1, facturaExistente.getId());
            pstmt.setString(2, maestroConsultado.getConsecutivoDeFactura());
            pstmt.setDouble(3, calcularSubtotalFactura(Integer.parseInt(facturaExistente.getId())));
            pstmt.setDouble(4, facturaExistente.getIVA());
            pstmt.setDouble(5, facturaExistente.getPrecioFinal());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación de la Factura.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al modificar Factura");
        } finally {
            desconectar();
        }
    }

    public void eliminarFactura(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try {
            buscarFacturaPorId(id);
        } catch (NoDataException e) {
            throw new GlobalException("La factura con ID " + id + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Factura");
        }

        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_FACTURA)) {
            pstmt.setString(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó la factura. Puede estar referenciada en otras tablas.");
            } else {
                System.out.println("Se eliminó la factura con ID " + id + " correctamente.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida");
        } finally {
            desconectar();
        }
    }

    public Collection<Factura> listarFactura() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ArrayList<Factura> coleccion = new ArrayList<>();

        try (CallableStatement pstmt = conexion.prepareCall(LISTAR_FACTURA)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                while (rs.next()) {
                    String maestroId = rs.getString("CONSECUTIVODEFACTURA");
                    Maestro maestroConsultado = maestroDAO.buscarMaestro(maestroId);

                    Factura factura = new Factura(
                            rs.getString("ID"),
                            maestroConsultado,
                            new ArrayList<>(), // Empty list of detalles for now
                            rs.getDouble("SUBTOTAL")
                    );
                    coleccion.add(factura);
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

    public Factura buscarFacturaPorId(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Factura factura = null;
        try (CallableStatement pstmt = conexion.prepareCall(BUSCAR_FACTURA_POR_ID)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();

            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                if (rs.next()) {
                    String maestroId = rs.getString("CONSECUTIVODEFACTURA");
                    Maestro maestroConsultado = maestroDAO.buscarMaestro(maestroId);

                    factura = new Factura(
                            rs.getString("ID"),
                            maestroConsultado,
                            new ArrayList<>(), // Empty list of detalles for now
                            rs.getDouble("SUBTOTAL")
                    );
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida");
        } finally {
            desconectar();
        }

        if (factura == null) {
            throw new NoDataException("No hay datos");
        }

        return factura;
    }
}
