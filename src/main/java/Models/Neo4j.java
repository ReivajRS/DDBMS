package Models;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class Neo4j {
    private final String dbUri, dbUser, dbPassword;
    private Driver driver;
    private Session session;
    private Transaction transaction;

    public Neo4j(String dbUri, String dbUser, String dbPassword) {
        this.dbUri = dbUri;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        try {
            driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
            System.out.println("CONEXION NEO4J REALIZADA");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean insert(Cliente cliente) {
        try {
            session = driver.session(SessionConfig.builder().withDatabase("neo4j").build());
            transaction = session.beginTransaction();
            transaction.run(
                    "MERGE (c:Cliente {IdCliente: $id, Nombre: $nombre, Estado: $estado, Credito: $credito, Deuda: $deuda})",
                    Map.of("id", cliente.getIdCliente(), "nombre", cliente.getNombre(), "estado", cliente.getEstado(),
                            "credito", cliente.getCredito(), "deuda", cliente.getDeuda())
            );
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            rollbackTransaction();
            return false;
        }
        return true;
    }

    public ArrayList<Cliente> select() {
        try {
            session = driver.session(SessionConfig.builder().withDatabase("neo4j").build());
            transaction = session.beginTransaction();
            Result result = transaction.run(
                    "MATCH (c:Cliente) RETURN c"
            );
            ArrayList<Cliente> tuples = new ArrayList<>();
            while (result.hasNext()) {
                Node node = result.next().get(0).asNode();
                tuples.add(new Cliente(
                        node.get("IdCliente").asInt(),
                        node.get("Nombre").asString(),
                        node.get("Estado").asString(),
                        node.get("Credito").asDouble(),
                        node.get("Deuda").asDouble()
                ));
            }
            commitTransaction();
            return tuples;
        }
        catch (Exception e) {
            rollbackTransaction();
            System.err.println(e.getMessage());
        }
        return new ArrayList<Cliente>();
    }

    public ArrayList<Cliente> select(String[] attributes) {
        try {
            session = driver.session(SessionConfig.builder().withDatabase("neo4j").build());
            transaction = session.beginTransaction();
            Result result = transaction.run(
                    "MATCH (c:Cliente) RETURN " + makeSelectedAttributesString(attributes, "c")
            );
            ArrayList<Cliente> tuples = new ArrayList<>();
            while (result.hasNext()) {
                Cliente cliente = new Cliente();
                Record record = result.next();
                for (Pair<String, Value> r : record.fields()) {
                    switch (r.key()) {
                        case "IdCliente": cliente.setIdCliente(r.value().asInt());
                        case "Nombre": cliente.setNombre(r.value().asString());
                        case "Estado": cliente.setEstado(r.value().asString());
                        case "Credito": cliente.setCredito(r.value().asDouble());
                        case "Deuda": cliente.setDeuda(r.value().asDouble());
                    }
                }
                tuples.add(cliente);
            }
            commitTransaction();
            return tuples;
        }
        catch (Exception e) {
            rollbackTransaction();
            System.err.println(e.getMessage());
        }
        return new ArrayList<Cliente>();
    }

    private String makeSelectedAttributesString(String[] attributes, String variable) {
        StringBuilder selected = new StringBuilder();
        for (String attribute : attributes) {
            if (!selected.isEmpty())
                selected.append(", ");
            selected.append(variable).append(".").append(attribute).append(" AS ").append(attribute);
        }
        return selected.toString();
    }

    public void delete(Map<String, String> filters) {
        try {
            session = driver.session(SessionConfig.builder().withDatabase("neo4j").build());
            transaction = session.beginTransaction();
        }
        catch (Exception e) {
            rollbackTransaction();
            System.err.println(e.getMessage());
        }
    }

    // Metodo para construir los filtros, aun pendiente
    private String makeFilter(Map<String, String> filters, String variable) {
        StringBuilder filter = new StringBuilder();
        for (String key : filters.keySet()) {
            if (!filter.isEmpty())
                filter.append(" AND ");
            filter.append(variable);
            filter.append(".");
            filter.append(filters.get(key));
        }
        return filter.toString();
    }

    public void commitTransaction() {
        transaction.commit();
        transaction.close();
        session.close();
        System.out.println("Transaction commited Neo4j");
    }

    public void rollbackTransaction() {
        transaction.rollback();
        transaction.close();
        session.close();
        System.out.println("Transaction rolled back Neo4j");
    }

}
