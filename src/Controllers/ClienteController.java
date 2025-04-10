package Controllers;

import DataAccess.ClienteDAO;
import DataAccess.GlobalException;
import DataAccess.NoDataException;
import Models.Cliente;
import Views.RegistrarCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ClienteController {
    private Cliente cliente;
    private ClienteDAO clienteDAO;
    private RegistrarCliente registrarCliente;

    public ClienteController(RegistrarCliente vista, Cliente modelo, ClienteDAO accesoDatos) {
        this.cliente = modelo;
        this.clienteDAO = accesoDatos;
        this.registrarCliente = vista;


        this.registrarCliente.addUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Registro actualizado");
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

                System.out.println(cliente.toString());
                try {
                    System.out.println("Entre al try del controller");
                    clienteDAO.insertarCliente(cliente);
                } catch (GlobalException | NoDataException | SQLException ex) {
                    throw new RuntimeException(ex);
                }

                registrarCliente.limpiarInputs();
            }
        });

    }
}
