package Controllers;

import DataAccess.ProductoDAO;
import DataAccess.GlobalException;
import DataAccess.NoDataException;
import Models.Producto;
import Views.Producto.ViewProducto;
import Views.Producto.ViewRegistrarProducto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collection;

public class ProductoController {
    private final Producto producto;
    private final ProductoDAO productoDAO;
    private ViewProducto viewProducto;

    public ProductoController(ViewProducto vista, Producto modelo, ProductoDAO accesoDatos) {
        this.producto = modelo;
        this.productoDAO = accesoDatos;
        this.viewProducto = vista;

        cargarProductos();

        viewProducto.getBtnRegistrarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewRegistrarProducto registrarProducto = new ViewRegistrarProducto();

                registrarProducto.addUpdateListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        producto.setId(registrarProducto.getID());
                        producto.setDescripcion(registrarProducto.getDescripcion());
                        producto.setMin(registrarProducto.getMinCantidad());
                        producto.setMax(registrarProducto.getMaxCantidad());
                        producto.setPrecioPorUnidad(registrarProducto.getPrecioPorUnidad());

                        try {
                            productoDAO.insertarProducto(producto);
                            JOptionPane.showMessageDialog(null, "Producto registrado exitosamente!");
                            registrarProducto.dispose();
                            cargarProductos();
                        } catch (GlobalException | NoDataException | SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al registrar el producto - " + ex.getMessage());
                        }
                    }
                });
            }
        });

        //Listener para eliminar un producto
        viewProducto.setDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hubo un click en el boton de eliminar!");
                if (e.getSource() instanceof ViewProducto.ActionsEditor) {
                    ViewProducto.ActionsEditor editor = (ViewProducto.ActionsEditor) e.getSource();
                    Producto productoAEliminar = editor.getProducto();
                    if (productoAEliminar != null) {
                        System.out.println("Tratando de eliminar a producto: " + productoAEliminar.getId());
                        eliminarProducto(productoAEliminar);
                    }
                }
            }
        });

        //Listener para modificar un producto
        viewProducto.setUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hubo un click en el boton de actualizar!");
                if (e.getSource() instanceof ViewProducto.ActionsEditor) {
                    ViewProducto.ActionsEditor editor = (ViewProducto.ActionsEditor) e.getSource();
                    Producto productoAActualizar = editor.getProducto();
                    if (productoAActualizar != null) {
                        System.out.println("Tratando de actualizar a producto: " + productoAActualizar.getId());
                        ViewRegistrarProducto registrarProducto = new ViewRegistrarProducto();
                        registrarProducto.setTitle("Actualizar Producto");
                        registrarProducto.getBtnRegistrar().setText("Actualizar");

                        registrarProducto.setID(productoAActualizar.getId());
                        registrarProducto.setDescripcion(productoAActualizar.getDescripcion());
                        registrarProducto.setMinCantidad(productoAActualizar.getMin());
                        registrarProducto.setMaxCantidad(productoAActualizar.getMax());
                        registrarProducto.setPrecioUnitario(productoAActualizar.getPrecioPorUnidad());

                        // Se hace read-only el ID, ya que no puede ser cambiado
                        registrarProducto.getTxtID().setEnabled(false);

                        registrarProducto.addUpdateListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                producto.setId(registrarProducto.getID());
                                producto.setDescripcion(registrarProducto.getDescripcion());
                                producto.setMin(registrarProducto.getMinCantidad());
                                producto.setMax(registrarProducto.getMaxCantidad());
                                producto.setPrecioPorUnidad(registrarProducto.getPrecioPorUnidad());

                                try {
                                    productoDAO.modificarProducto(producto);
                                    JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente!");
                                    registrarProducto.dispose();
                                    cargarProductos();
                                } catch (GlobalException | NoDataException | SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "Error al actualizar el producto - " + ex.getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void cargarProductos() {
        try {
            Collection<Producto> productos = productoDAO.listarProducto();
            viewProducto.listarProductos(productos);
        } catch (GlobalException | NoDataException | SQLException e) {
            JOptionPane.showMessageDialog(viewProducto,
                    "Error al cargar productos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto(Producto producto) {
        int confirmacion = JOptionPane.showConfirmDialog(
                viewProducto,
                "¿Está seguro que desea eliminar al producto " + producto.getId() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                System.out.println("Intentando eliminar producto con ID: " + producto.getId());
                productoDAO.eliminarProducto(producto.getId());
                // Actualizar la tabla después de eliminar
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) viewProducto.getTablaProductos().getModel();
                    int rowCount = model.getRowCount();
                    if (rowCount > 0) {
                        // Si no es el último registro, actualizar la tabla normalmente
                        cargarProductos();
                    } else {
                        // Si es el último registro, limpiar la tabla
                        model.setRowCount(0);
                    }
                });
            } catch (SQLException | GlobalException | NoDataException ex) {
                JOptionPane.showMessageDialog(viewProducto,
                        "Error al eliminar producto: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("Error al eliminar producto: " + ex);
            }
        }
    }
}
