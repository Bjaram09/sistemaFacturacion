import DataAccess.ServicioDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ServicioDB servicioDB = new ServicioDB();

        try {
            servicioDB.conectar();
            System.out.println("Conectado exitosamente a la base de datos");
            servicioDB.desconectar();
            System.out.println("Desconectado exitosamente de la base de datos");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}