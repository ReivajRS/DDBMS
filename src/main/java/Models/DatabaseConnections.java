package Models;

import Models.DatabaseModels.Database;
import Models.DatabaseModels.MongoDB;
import Models.DatabaseModels.Neo4j;
import Models.DatabaseModels.SQLServer;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseConnections {

    private final HashMap<String, Database> databases;

    public DatabaseConnections() {
        ArrayList<Fragment> fragments = SingletonDatabaseConfiguration.getInstance().selectFragments();
        databases =new HashMap<>();
        for (Fragment fragment : fragments) {
            switch (fragment.getDBMS()) {
                case "SQL Server":
                    databases.put(fragment.getCriteriaValue().toLowerCase(), new SQLServer(fragment.getURI(), fragment.getDB(),
                     fragment.getDistributedTable(), fragment.getAccessUser(),
                     fragment.getAccessPassword(), fragment.getAttributes()));
                    break;
                case "Mongo DB":
                    databases.put(fragment.getCriteriaValue().toLowerCase(),new MongoDB(fragment.getURI(),fragment.getDB(),fragment.getDistributedTable(), fragment.getAccessUser(), fragment.getAccessPassword()));
                    break;
                case "Neo4j":
                    databases.put(fragment.getCriteriaValue().toLowerCase(),new Neo4j(fragment.getURI(), fragment.getAccessUser(), fragment.getAccessPassword(),fragment.getDistributedTable(),fragment.getAttributes()));
                    break;
            }
        }
    }

    public HashMap<String,Database> getDatabases(){
        return databases;
    }

}
