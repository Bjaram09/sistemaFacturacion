package Controllers;

import DataAccess.ClienteDAO;
import DataAccess.GlobalException;
import DataAccess.NoDataException;
import Models.Cliente;
import Views.Cliente.ViewCliente;
import Views.Cliente.ViewRegistrarCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collection;

public class ClienteController {
    private final Cliente cliente;
    private final ClienteDAO clienteDAO;
    private ViewCliente viewCliente;

    public ClienteController(ViewCliente vista, Cliente modelo, ClienteDAO accesoDatos) {
        this.cliente = modelo;
        this.clienteDAO = accesoDatos;
        this.viewCliente = vista;

        cargarClientes();

        viewCliente.getBtnRegistrarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewRegistrarCliente registrarCliente = new ViewRegistrarCliente();

                registrarCliente.addUpdateListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cliente.setId(registrarCliente.getID());
                        cliente.setNombre(registrarCliente.getNombre());
                        cliente.setPrimerApellido(registrarCliente.getPrimerApellido());
                        cliente.setSegundoApellido(registrarCliente.getSegundoApellido());
                        cliente.setCorreoElectronico(registrarCliente.getCorreo());
                        cliente.setDireccion(registrarCliente.getDireccion());
                        cliente.setEdad(registrarCliente.getEdad());
                        cliente.setGenero(registrarCliente.getGenero());
                        cliente.setTelefonoCelular(registrarCliente.getTelefonoCelular());
                        cliente.setTelefonoCasa(registrarCliente.getTelefonoCasa());

                        try {
                            clienteDAO.insertarCliente(cliente);
                            JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente!");
                            registrarCliente.dispose();
                            cargarClientes();
                        } catch (GlobalException | NoDataException | SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al registrar el cliente - " + ex.getMessage());
                        }
                    }
                });
            }
        });

        //Listener para eliminar un cliente
        viewCliente.setDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hubo un click en el boton de eliminar!");
                if (e.getSource() instanceof ViewCliente.ActionsEditor) {
                    ViewCliente.ActionsEditor editor = (ViewCliente.ActionsEditor) e.getSource();
                    Cliente clienteAEliminar = editor.getCliente();
                    if (clienteAEliminar != null) {
                        System.out.println("Tratando de eliminar a cliente: " + clienteAEliminar.getId());
                        eliminarCliente(clienteAEliminar);
                    }
                }
            }
        });

        //Listener para modificar un cliente
        viewCliente.setUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hubo un click en el boton de actualizar!");
                if (e.getSource() instanceof ViewCliente.ActionsEditor) {
                    ViewCliente.ActionsEditor editor = (ViewCliente.ActionsEditor) e.getSource();
                    Cliente clienteAActualizar = editor.getCliente();
                    if (clienteAActualizar != null) {
                        System.out.println("Tratando de actualizar a cliente: " + clienteAActualizar.getId());
                        ViewRegistrarCliente registrarCliente = new ViewRegistrarCliente();
                        registrarCliente.setTitle("Actualizar Cliente");
                        registrarCliente.getBtnRegistrar().setText("Actualizar");

                        //Se popula el view de registrarCliente con el cliente que vamos a actualizar
                        registrarCliente.setID(clienteAActualizar.getId());
                        registrarCliente.setNombre(clienteAActualizar.getNombre());
                        registrarCliente.setPrimerApellido(clienteAActualizar.getPrimerApellido());
                        registrarCliente.setSegundoApellido(clienteAActualizar.getSegundoApellido());
                        registrarCliente.setGenero(clienteAActualizar.getGenero());
                        registrarCliente.setDireccion(clienteAActualizar.getDireccion());
                        registrarCliente.setEdad(clienteAActualizar.getEdad());
                        registrarCliente.setCorreo(clienteAActualizar.getCorreoElectronico());
                        registrarCliente.setTelefonoCasa(clienteAActualizar.getTelefonoCasa());
                        registrarCliente.setTelefonoCelular(clienteAActualizar.getTelefonoCelular());

                        // Se hace read-only el ID, ya que no puede ser cambiado
                        registrarCliente.getTxtID().setEnabled(false);

                        registrarCliente.addUpdateListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                cliente.setId(registrarCliente.getID());
                                cliente.setNombre(registrarCliente.getNombre());
                                cliente.setPrimerApellido(registrarCliente.getPrimerApellido());
                                cliente.setSegundoApellido(registrarCliente.getSegundoApellido());
                                cliente.setCorreoElectronico(registrarCliente.getCorreo());
                                cliente.setDireccion(registrarCliente.getDireccion());
                                cliente.setEdad(registrarCliente.getEdad());
                                cliente.setGenero(registrarCliente.getGenero());
                                cliente.setTelefonoCelular(registrarCliente.getTelefonoCelular());
                                cliente.setTelefonoCasa(registrarCliente.getTelefonoCasa());

                                try {
                                    clienteDAO.modificarCliente(cliente);
                                    JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente!");
                                    registrarCliente.dispose();
                                    cargarClientes();
                                } catch (GlobalException | NoDataException | SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "Error al actualizar el cliente - " + ex.getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void cargarClientes() {
        try {
            Collection<Cliente> clientes = clienteDAO.listarCliente();
            viewCliente.listarClientes(clientes);
        } catch (GlobalException | NoDataException | SQLException e) {
            JOptionPane.showMessageDialog(viewCliente,
                "Error al cargar clientes: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente(Cliente cliente) {
        int confirmacion = JOptionPane.showConfirmDialog(
            viewCliente,
            "¿Está seguro que desea eliminar al cliente " + cliente.getNombre() + " " + cliente.getPrimerApellido() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                System.out.println("Intentando eliminar cliente con ID: " + cliente.getId());
                clienteDAO.eliminarCliente(cliente.getId());
                // Actualizar la tabla después de eliminar
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) viewCliente.getTablaClientes().getModel();
                    int rowCount = model.getRowCount();
                    if (rowCount > 0) {
                        // Si no es el último registro, actualizar la tabla normalmente
                        cargarClientes();
                    } else {
                        // Si es el último registro, limpiar la tabla
                        model.setRowCount(0);
                    }
                });
            } catch (SQLException | GlobalException | NoDataException ex) {
                JOptionPane.showMessageDialog(viewCliente,
                    "Error al eliminar cliente: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                System.out.println("Error al eliminar cliente: " + ex);
            }
        }
    }

}
