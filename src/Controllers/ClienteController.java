package Controllers;

import DataAccess.ClienteDAO;
import DataAccess.GlobalException;
import DataAccess.NoDataException;
import Models.Cliente;
import Views.Cliente.ViewCliente;
import Views.Cliente.ViewRegistrarCliente;

import javax.swing.*;
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

        // Load initial client list
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

        // Set up delete listener
        viewCliente.setDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete button clicked!");
                if (e.getSource() instanceof ViewCliente.ButtonEditor) {
                    ViewCliente.ButtonEditor editor = (ViewCliente.ButtonEditor) e.getSource();
                    Cliente clienteAEliminar = editor.getCliente();
                    if (clienteAEliminar != null) {
                        System.out.println("Trying to delete client: " + clienteAEliminar.getNombre());
                        eliminarCliente(clienteAEliminar);
                    }
                }
            }
        });
    }

    private void cargarClientes() {
        try {
            Collection<Cliente> clientes = clienteDAO.listarCliente();
            viewCliente.setClientes(clientes);
        } catch (GlobalException | NoDataException | SQLException e) {
            JOptionPane.showMessageDialog(viewCliente, 
                "Error al cargar clientes: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente(Cliente cliente) {
        int confirm = JOptionPane.showConfirmDialog(viewCliente,
                "¿Seguro que desea eliminar a " + cliente.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                System.out.println("Trying to eliminar cliente: " + cliente.getId());
                clienteDAO.eliminarCliente(cliente.getId());
                cargarClientes();
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
