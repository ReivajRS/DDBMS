package Models.DatabaseModels;

import Models.Cliente;

import java.sql.*;
import java.util.ArrayList;

public class Neo4j extends Database {
    private Connection connection;
    private final String tableName;
    private final String[] attributes;
    public Neo4j(String uri, String user, String password, String tableName, String[] attributes) {
        String server = "jdbc:" + uri;
        this.tableName = tableName;
        this.attributes = attributes;
        try {
            connection = DriverManager.getConnection(server, user, password);
            System.out.println("Neo4j connection established successfully");
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
    public void makeQuery(String query) {
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
        try {
            Statement st = connection.createStatement();
            String cypherQuery = connection.nativeSQL(query);
            ResultSet rs = st.executeQuery(cypherQuery);
            while(rs.next()){
                if(hasAsterisk){
                    results.add(new Cliente(
                            rs.getInt(attributes[0]),
                            rs.getString(attributes[1]),
                            rs.getString(attributes[2]),
                            Double.parseDouble(rs.getObject(attributes[3]).toString()),
                            Double.parseDouble(rs.getObject(attributes[4]).toString())
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
                    customer.setCredito(Double.parseDouble(rs.getObject(cnt++).toString()));
                }
                if(hasColumn[4]){
                    customer.setDeuda(Double.parseDouble(rs.getObject(cnt).toString()));
                }
                results.add(customer);
            }
            st.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            results = null;
        }
    }

    @Override
    public boolean makeTransaction(String transaction) {
        try {
            transaction = transformStatement(transaction);
            if (transaction.contains("insert")) {
                String[] insertParts = transaction.split("values");
                if (!insertParts[0].contains("("))
                    insertParts[0] += "(" + attributes[0] + "," + attributes[1] + "," + attributes[2] +
                        "," + attributes[3] + "," + attributes[4] + ")";
                transaction = insertParts[0] + " VALUES " + insertParts[1];
            }
            String cypherTransaction = connection.nativeSQL(transaction);
            connection.setAutoCommit(false);
            Statement st = connection.createStatement();
            st.executeUpdate(cypherTransaction);
            st.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        System.out.println("Neo4j ready to commit");
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
            System.out.println("Transaction commited Neo4j");
        }catch (Exception e){
            rollbackTransaction();
        }
    }

    @Override
    public void rollbackTransaction() {
        try{
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println("Transaction rolled back Neo4j");
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
}
