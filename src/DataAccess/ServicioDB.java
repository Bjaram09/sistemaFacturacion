package DataAccess;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServicioDB {
    protected Connection conexion;

    public ServicioDB() {

    }

    private void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void conectar() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.setConexion(DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SISTEMAFACTURACION", "Sn0wf!r99"));
    }

    public void desconectar() throws SQLException {
        if(!conexion.isClosed()){
            conexion.close();
        }
    }

    private Connection getJdbcMydbsource() throws NamingException, SQLException {
        Context context = new InitialContext();
        try {
            return ((DataSource) context.lookup("jdbc/Mydbsource")).getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
