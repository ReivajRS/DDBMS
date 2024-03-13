package Models.DatabaseModels;

import Models.Cliente;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class SQLServer extends Database {
    private Connection connection;
    private String tableName;
    private String[] attributes;
    public SQLServer(String server, String bd, String tableName, String user, String password, String[] attributes) {
        this.tableName = tableName;
        this.attributes = attributes;
        String url = "jdbc:sqlserver://" + server + ":1433;database=" + bd
                + ";trustServerCertificate=true;loginTimeout=30";
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("SQL connection established successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public SQLServer(String server, String bd, String user, String password) {
        String url = "jdbc:sqlserver://" + server + ":1433;database=" + bd
                + ";trustServerCertificate=true;loginTimeout=30";
        try {
            connection = DriverManager.getConnection(url, user, password);
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
    public void makeQuery(String query){
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
        results = new ArrayList<>();
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                if(hasAsterisk){
                    results.add(new Cliente(
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
                results.add(customer);
            }
            st.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            results = null;
        }
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
        System.out.println("SQL Server ready to commit");
        return true;
    }

    public boolean checkConnection(){
        if(connection == null) return false;
        try{
            return connection.isValid(3);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return false;
        }
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

    @Override
    public void run() {
        if(!checkConnection()){
            finalStatus = false;
            return;
        }
        if (!statement.contains("select")) {
            finalStatus = makeTransaction(statement);
            return;
        }
        makeQuery(statement);
        finalStatus = results != null;
    }

    public Connection getConnection() {
        return connection;
    }
}
