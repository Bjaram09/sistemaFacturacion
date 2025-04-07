package DataAccess;

import Models.Producto;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ProductoDAO extends ServicioDB {
    private static final String INSERTAR_PRODUCTO = "{CALL SISTEMAFACTURACION.INSERTAR_PRODUCTO(?, ?, ?, ?, ?)}";
    private static final String MODIFICAR_PRODUCTO = "{CALL SISTEMAFACTURACION.MODIFICAR_PRODUCTO(?, ?, ?, ?, ?)}";
    private static final String ELIMINAR_PRODUCTO = "{CALL SISTEMAFACTURACION.ELIMINAR_PRODUCTO(?)}";
    private static final String LISTAR_PRODUCTO = "{?=CALL SISTEMAFACTURACION.LISTAR_PRODUCTO()}";
    private static final String BUSCAR_PRODUCTO_POR_ID = "{?=CALL SISTEMAFACTURACION.BUSCAR_PRODUCTO_POR_ID(?)}";

    public ProductoDAO() {
        super();
    }

    private void setProductoParameters(CallableStatement pstmt, Producto producto) throws SQLException {
        try {
            pstmt.setString(1, producto.getId());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setInt(3, producto.getMin());
            pstmt.setInt(4, producto.getMax());
            pstmt.setDouble(5, producto.getPrecioPorUnidad());
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error setting parameters for CallableStatement");
        }
    }

    public void insertarProducto(Producto producto) throws GlobalException, NoDataException, SQLException {
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

        try (CallableStatement pstmt = conexion.prepareCall(INSERTAR_PRODUCTO))
        {
            setProductoParameters(pstmt, producto);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la operación en el Cliente.");
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

    public void modificarProducto(Producto producto) throws GlobalException, NoDataException, SQLException {
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

        Producto productoExistente;
        try {
            productoExistente = buscarProducto(producto.getId());
        } catch (NoDataException e) {
            throw new GlobalException("El Maestro con ID " + producto.getId() + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Maestro");
        }


        try (CallableStatement pstmt = conexion.prepareCall(MODIFICAR_PRODUCTO))
        {

            setProductoParameters(pstmt, productoExistente);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se realizó la modificación del Cliente.");
            } else {
                System.out.println("Cliente modificado exitosamente con ID: " + producto.getId());
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

    public void eliminarProducto(String id) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        // Making sure that the product that I want to delete, exists on my DB
        try {
            buscarProducto(id);
        } catch (NoDataException e) {
            throw new GlobalException("El producto con ID " + id + " no existe en la base de datos.");
        } catch (SQLException e) {
            throw new GlobalException("Error al consultar Cliente");
        }

        // perform the deletion
        try (CallableStatement pstmt = conexion.prepareCall(ELIMINAR_PRODUCTO)) {
            pstmt.setString(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoDataException("No se eliminó el producto. Puede estar referenciado en otras tablas.");
            }
        } catch (SQLException e) {
            throw new GlobalException("Error al eliminar Producto");
        } finally {
            desconectar();
        }
    }

    public Collection<Producto> listarProducto() throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ArrayList<Producto> coleccion = new ArrayList<>();
        try (CallableStatement pstmt = conexion.prepareCall(LISTAR_PRODUCTO)){
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                while (rs.next()) {
                    Producto producto = new Producto(
                            rs.getString("ID"),
                            rs.getString("DESCRIPCION"),
                            rs.getInt("MINCANTIDAD"),
                            rs.getInt("MAXCANTIDAD"),
                            rs.getDouble("PRECIOPORUNIDAD")
                    );
                    coleccion.add(producto);
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

    public Producto buscarProducto(String id) throws GlobalException, NoDataException, SQLException {

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

        Producto producto = null;
        try (CallableStatement pstmt = conexion.prepareCall(BUSCAR_PRODUCTO_POR_ID)){
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();

            try (ResultSet rs = (ResultSet) pstmt.getObject(1)) {
                if (rs.next())
                {
                    producto = new Producto(
                            rs.getString("ID"),
                            rs.getString("DESCRIPCION"),
                            rs.getInt("MINCANTIDAD"),
                            rs.getInt("MAXCANTIDAD"),
                            rs.getDouble("PRECIOPORUNIDAD")
                    );
                }
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
        if (producto == null)
        {
            throw new NoDataException("No hay datos");
        }
        return producto;
    }
}
