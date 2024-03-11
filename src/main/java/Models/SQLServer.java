package Models;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class SQLServer extends Database {
    private Connection connection;
    private String tableName;
    private String[] attributes;
    public SQLServer(String servidor, String bd, String tableName, String usuario, String contrasena, String[] attributes) {
        this.tableName = tableName;
        this.attributes = attributes;
        String url = "jdbc:sqlserver://" + servidor + ":1433;database=" + bd
                + ";trustServerCertificate=true;loginTimeout=30";
        try {
            connection = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("SQL connection established successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public SQLServer(String servidor, String bd, String usuario, String contrasena) {
        String url = "jdbc:sqlserver://" + servidor + ":1433;database=" + bd
                + ";trustServerCertificate=true;loginTimeout=30";
        try {
            connection = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("SQL Configuration connection established successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private String transformStatement(String statement){
        statement = statement.toLowerCase().trim();
        statement = statement.replace("clientes",tableName);
        statement = statement.replaceAll("idcliente",attributes[0]);
        statement = statement.replaceAll("nombre",attributes[1]);
        statement = statement.replaceAll("estado",attributes[2]);
        statement = statement.replaceAll("credito",attributes[3]);
        statement = statement.replaceAll("deuda",attributes[4]);
        return statement;
    }

    @Override
    public ArrayList<Cliente> makeQuery(String query){
        query = transformStatement(query);
        String withoutWhere = query.split("from")[0];
        boolean[] hasColumn = {
                withoutWhere.contains(attributes[0]),
                withoutWhere.contains(attributes[1]),
                withoutWhere.contains(attributes[2]),
                withoutWhere.contains(attributes[3]),
                withoutWhere.contains(attributes[4])
        };
        boolean hasAsterisk = withoutWhere.contains("*");
        ArrayList<Cliente> customers = new ArrayList<>();
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                if(hasAsterisk){
                    customers.add(new Cliente(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDouble(4),
                            rs.getDouble(5)
                    ));
                    continue;
                }
                Cliente customer = new Cliente();
                int cnt = 1;
                if(hasColumn[0]){
                    customer.setIdcliente(rs.getInt(cnt++));
                }
                if(hasColumn[1]){
                    customer.setNombre(rs.getString(cnt++));
                }
                if(hasColumn[2]){
                    customer.setEstado(rs.getString(cnt++));
                }
                if(hasColumn[3]){
                    customer.setCredito(rs.getDouble(cnt++));
                }
                if(hasColumn[4]){
                    customer.setDeuda(rs.getDouble(cnt));
                }
                customers.add(customer);
            }
            st.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return customers;
    }
    @Override
    public boolean makeTransaction(String statement){
        statement = transformStatement(statement);
        try {
            Statement st = connection.createStatement();
            connection.setAutoCommit(false);
            st.execute(statement);
            st.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            rollbackTransaction();
            return false;
        }
        return true;
    }

    @Override
    public void commitTransaction() {
        try{
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("Transaction commited SQLServer");
        }catch (Exception e){
            rollbackTransaction();
        }


    }
    @Override
    public void rollbackTransaction() {
        try{
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println("Transaction rolled back SQLServer");
        }catch (Exception e) {
            return;
        }
    }

    public boolean addFragment(String[] values) {
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO Fragment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            PreparedStatement st = connection.prepareStatement("DELETE FROM Fragment WHERE IDFragment = ?");
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
            Statement st = connection.createStatement();
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

    public String getZoneByState(String state){
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT ZONE FROM STATE_ZONE WHERE STATE = '"+state+"'");
            rs.next();
            if(rs.wasNull()) return "";
            return rs.getString(1).toLowerCase();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    public ArrayList<String> getZonesByQuery(String query){
        ArrayList<String> zones = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                zones.add(rs.getString(1));
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return zones;
        }
        return zones;
    }

    public int getID(){
        String query= "DECLARE @ID INT "+
                "EXEC SP_GetID @ID OUTPUT "+
                "SELECT ID = @ID";
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public void run() {
        if (statement.contains("select")) {
            results = makeQuery(statement);
            finalStatus = results != null;
            return;
        }
        if (results != null) results.clear();
        else results = new ArrayList<>();
        finalStatus = makeTransaction(statement);
    }
}
