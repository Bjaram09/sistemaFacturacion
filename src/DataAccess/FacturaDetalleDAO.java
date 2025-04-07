package DataAccess;

import Models.Detalle;
import Models.Factura;

import java.sql.SQLException;
import java.util.List;

public class FacturaDetalleDAO extends ServicioDB {
    FacturaDAO facturaDAO = new FacturaDAO();
    DetalleDAO detalleDAO = new DetalleDAO();

    public FacturaDetalleDAO() {
        super();
    }

    public void registrarFacturaConDetalles(Factura factura, List<Detalle> detalles) throws SQLException {
        try {
            conexion.setAutoCommit(false);

            int facturaId = facturaDAO.insertarFactura(factura); // Insertar Factura

            for (Detalle detalle : detalles) {
                detalleDAO.insertarDetalle(detalle, String.valueOf(facturaId)); // Insertar cada Detalle de la factura
            }

            conexion.commit(); // Commit if everything succeeds
        } catch (SQLException | GlobalException | NoDataException e) {
            conexion.rollback(); // Rollback on failure
            throw new SQLException("Error al insertar factura y detalles", e);
        } finally {
            conexion.setAutoCommit(true); // Reset auto-commit
        }
    }
}
