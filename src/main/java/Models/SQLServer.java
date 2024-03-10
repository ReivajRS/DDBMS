package Models;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<Cliente> select(String query){
        ArrayList<Cliente> customers = new ArrayList<>();
        try{

            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                customers.add(new Cliente(rs.getInt("idCliente"),rs.getString("nombre"),
                        rs.getString("estado"),rs.getDouble("credito"),rs.getDouble("deuda")));
            }
            st.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return customers;
    }
    public boolean writeOperation(String statement){
        try {
            Statement st =conexion.createStatement();
            conexion.setAutoCommit(false);
            st.execute(statement);
            st.close();
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


    // Configuration

    public ArrayList<String[]> recoverConfiguration() {
        ArrayList<String[]> configuration = new ArrayList<>();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Fragmento");
            while (rs.next()) {
                String[] tuple = new String[7];
                for (int i = 0; i < 7; i++)
                    tuple[i] = rs.getObject(i+1).toString();
                configuration.add(tuple);
            }
            st.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return configuration;
    }

    public boolean addFragment(String[] values) {
        try {
            PreparedStatement st = conexion.prepareStatement("INSERT INTO Fragment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < values.length; i++)
                st.setString(i + 1, values[i]);
            st.executeUpdate();
            st.close();
            return true;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteFragment(int id) {
        try {
            PreparedStatement st = conexion.prepareStatement("DELETE FROM Fragment WHERE IDFragment = ?");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
            return true;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<Fragment> selectFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Fragment");
            while (rs.next()) {
                Fragment fragment = new Fragment(
                  rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10)
                );
                fragments.add(fragment);
            }
            st.close();
            return fragments;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return fragments;
    }

    public int getId(){
        String query= "DECLARE @ID INT "+
                "EXEC SP_GetID @ID OUTPUT "+
                "SELECT ID = @ID";
        try{
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }
}
