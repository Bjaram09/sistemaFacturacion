import Controllers.ClienteController;
//import Controllers.ProductoController;
import Controllers.ProductoController;
import DataAccess.ClienteDAO;
import DataAccess.ProductoDAO;
import Models.Cliente;
import Models.Producto;
import Views.Cliente.ViewCliente;
import Views.Producto.ViewProducto;
//import Views.Producto.ViewProducto;

public class Main {
    public static void main(String[] args){
        Cliente modeloCliente = new Cliente();
        ClienteDAO accesoDatosCliente = new ClienteDAO();
        ViewCliente vistaCliente = new ViewCliente();

        Producto modeloProducto = new Producto();
        ProductoDAO accesoDatosProducto = new ProductoDAO();
        ViewProducto vistaProducto = new ViewProducto();

        ClienteController clienteController = new ClienteController(vistaCliente, modeloCliente, accesoDatosCliente);
        ProductoController productoController = new ProductoController(vistaProducto, modeloProducto, accesoDatosProducto);
    }
}