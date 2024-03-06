package Models;

import java.sql.*;
public class SQLServer {
    private Connection conexion;
    public SQLServer(String servidor, String bd, String usuario, String contrasena) {
        String url = "jdbc:sqlserver://" + servidor + ":1433;database=" + bd
                + ";trustServerCertificate=true;loginTimeout=3";
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("CONEXION SQL REALIZADA");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean insert(Cliente cliente){
        String query = "INSERT INTO CLIENTES VALUES(?,?,?,?,?)";
        try{
            PreparedStatement st = conexion.prepareStatement(query);
            st.setInt(1,cliente.getIdCliente());
            st.setString(2,cliente.getNombre());
            st.setString(3,cliente.getEstado());
            st.setDouble(4,cliente.getCredito());
            st.setDouble(5,cliente.getDeuda());
            conexion.setAutoCommit(false);
            st.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            rollbackTransaction();
            return false;
        }
        return true;
    }

    public void commitTransaction() {
        try{
            conexion.commit();
            conexion.setAutoCommit(true);
            System.out.println("Transaction commited SQLServer");
        }catch (Exception e){
            rollbackTransaction();
        }


    }

    public void rollbackTransaction() {
        try{
            conexion.rollback();
            conexion.setAutoCommit(true);
            System.out.println("Transaction rolled back SQLServer");
        }catch (Exception e) {
            rollbackTransaction();
        }
    }
}
