import Controllers.ClienteController;
import DataAccess.ClienteDAO;
import Models.Cliente;
import Views.RegistrarCliente;

public class Main {
    public static void main(String[] args) {

        Cliente modelo = new Cliente();
        RegistrarCliente vista = new RegistrarCliente();
        ClienteDAO accesoDatos = new ClienteDAO();

        ClienteController clienteController = new ClienteController(vista, modelo, accesoDatos);



    }
}