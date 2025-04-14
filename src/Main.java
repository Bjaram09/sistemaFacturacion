import Controllers.ClienteController;
import DataAccess.ClienteDAO;
import DataAccess.GlobalException;
import DataAccess.NoDataException;
import Models.Cliente;
import Views.Cliente.ViewCliente;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws GlobalException, NoDataException, SQLException {

        Cliente modeloCliente = new Cliente();
        ClienteDAO accesoDatosCliente = new ClienteDAO();
        ViewCliente vistaCliente = new ViewCliente();

        ClienteController clienteController = new ClienteController(vistaCliente, modeloCliente, accesoDatosCliente);

    }
}