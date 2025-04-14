import Controllers.ClienteController;
import DataAccess.ClienteDAO;
import Models.Cliente;
import Views.Cliente.ViewCliente;

public class Main {
    public static void main(String[] args){

        Cliente modeloCliente = new Cliente();
        ClienteDAO accesoDatosCliente = new ClienteDAO();
        ViewCliente vistaCliente = new ViewCliente();

        ClienteController clienteController = new ClienteController(vistaCliente, modeloCliente, accesoDatosCliente);

    }
}