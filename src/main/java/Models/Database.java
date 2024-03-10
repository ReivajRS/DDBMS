package Models;

import java.util.ArrayList;

public class Database {
    private ArrayList<SQLServer> sqlServerConnections;
    private ArrayList<MongoDB> mongoDBConnections;
    private ArrayList<Neo4j> neo4jConnections;

    public Database(ArrayList<Fragment> fragments) {
        sqlServerConnections = new ArrayList<>();
        mongoDBConnections = new ArrayList<>();
        neo4jConnections = new ArrayList<>();
        for (Fragment fragment : fragments) {
            switch (fragment.getDBMS()) {
                case "SQL Server":
                    sqlServerConnections.add(new SQLServer(fragment.getURI(), fragment.getDB(), fragment.getAccessUser(), fragment.getAccessPassword()));
                    break;
                case "Mongo DB":
                    mongoDBConnections.add(new MongoDB(fragment.getDB(), fragment.getAccessUser(), fragment.getAccessPassword()));
                    break;
                case "Neo4j":
                    neo4jConnections.add(new Neo4j(fragment.getURI(), fragment.getAccessUser(), fragment.getAccessPassword()));
                    break;
            }
        }
    }

    public ArrayList<SQLServer> getSqlServerConnections() {
        return sqlServerConnections;
    }

    public ArrayList<MongoDB> getMongoDBConnections() {
        return mongoDBConnections;
    }

    public ArrayList<Neo4j> getNeo4jConnections() {
        return neo4jConnections;
    }
}
