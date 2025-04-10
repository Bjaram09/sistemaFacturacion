package DataAccess;

import Models.Detalle;
import Models.Producto;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DetalleDAO extends ServicioDB {
    private static final String INSERTAR_DETALLE = "{CALL SISTEMAFACTURACION.INSERTAR_DETALLE(?, ?, ?, ?)}";
    private static final String MODIFICAR_DETALLE = "{CALL SISTEMAFACTURACION.MODIFICAR_DETALLE(?, ?, ?, ?)}";
    private static final String ELIMINAR_DETALLE = "{CALL SISTEMAFACTURACION.ELIMINAR_DETALLE(?)}";
    private static final String LISTAR_DETALLE = "{?=CALL SISTEMAFACTURACION.LISTAR_DETALLE()}";
    private static final String BUSCAR_DETALLE_POR_ID = "{?=CALL SISTEMAFACTURACION.BUSCAR_DETALLE_POR_ID(?)}";

    ProductoDAO productoDAO;
    FacturaDAO facturaDAO;
    
    public DetalleDAO() {
        super();
        productoDAO = new ProductoDAO();
        facturaDAO = new FacturaDAO();
    }

    public void insertarDetalle(Detalle detalle, String facturaId) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        // Then verify that the Producto exists
        Producto productoConsultado;
        try {
            productoConsultado = productoDAO.buscarProducto(detalle.getInfoProducto().getId());
        } catch (NoDataException e) {
            throw new GlobalException("El Producto con ID " + detalle.getInfoProducto().getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Producto");
        }

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_DETALLE)) {
            pstmt.setString(1, detalle.getId());
            pstmt.setString(2, facturaId);
            pstmt.setString(3, productoConsultado.getId());
            pstmt.setInt(4, detalle.getCantidad());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la inserción del Detalle.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al insertar Detalle: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    public void modificarDetalle(Detalle detalle, String facturaId) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        // Make sure that a Detalle exists on my DB
        Detalle detalleExistente;
        try {
            detalleExistente = buscarDetalle(detalle.getId());
        } catch (NoDataException e) {
            throw new GlobalException("El Detalle con ID " + detalle.getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Detalle");
        }

        // Verify that the Factura exists
        try {
            facturaDAO.buscarFacturaPorId(facturaId);
        } catch (NoDataException e) {
            throw new GlobalException("La Factura con ID " + facturaId + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Factura");
        }

        // Making sure that my producto exists on my DB
        Producto productoConsultado;
        try {
            productoConsultado = productoDAO.buscarProducto(detalle.getInfoProducto().getId());
        } catch (NoDataException e) {
            throw new GlobalException("El producto con ID " + detalle.getInfoProducto().getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Producto");
        }

        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_DETALLE)) {
            pstmt.setString(1, detalleExistente.getId());
            pstmt.setString(2, facturaId);
            pstmt.setString(3, productoConsultado.getId());
            pstmt.setInt(4, detalleExistente.getCantidad());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Detalle.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al modificar Detalle");
        } finally {
            desconectar();
        }
    }

    public void eliminarDetalle(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        try {
            buscarDetalle(id);
        } catch (NoDataException e) {
            throw new GlobalException("El detalle con ID " + id + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Detalle");
        }

        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_DETALLE)) {
            pstmt.setString(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el detalle. Puede estar referenciado en otras tablas.");
            } else {
                System.out.println("Se eliminó el detalle con ID " + id + " correctamente.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida");
        } finally {
            desconectar();
        }
    }

    public Collection<Detalle> listarDetalle() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ArrayList<Detalle> coleccion = new ArrayList<>();

        try (CallableStatement pstmt = conexion.prepareCall(LISTAR_DETALLE)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                while (rs.next()) {
                    String productoId = rs.getString("PRODUCTOID");
                    Producto productoConsultado = productoDAO.buscarProducto(productoId);

                    Detalle detalle = new Detalle(
                            rs.getString("ID"),
                            productoConsultado,
                            rs.getInt("CANTIDAD")
                    );
                    coleccion.add(detalle);
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

    public Detalle buscarDetalle(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        Detalle detalle = null;
        try (CallableStatement pstmt = conexion.prepareCall(BUSCAR_DETALLE_POR_ID)) {
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();

            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                if (rs.next()) {
                    String productoId = rs.getString("PRODUCTOID");
                    Producto productoConsultado = productoDAO.buscarProducto(productoId);

                    detalle = new Detalle(
                            rs.getString("ID"),
                            productoConsultado,
                            rs.getInt("CANTIDAD")
                    );
                }
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no válida");
        } finally {
            desconectar();
        }

        if (detalle == null) {
            throw new NoDataException("No hay datos");
        }

        return detalle;
    }
}
