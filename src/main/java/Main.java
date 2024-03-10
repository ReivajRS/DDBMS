import Controllers.MainController;
import Models.*;
import Views.ConfigurationView;
import Views.MainView;
import Views.QueryView;
import Views.TransactionView;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SQLServer dbConfiguration = new SQLServer("localhost", "DBConfiguration", "sa", "sa");
        Database database = new Database(dbConfiguration.selectFragments());

        MainView view = new MainView();
        ConfigurationView configurationView = new ConfigurationView();
        TransactionView transactionView = new TransactionView();
        QueryView queryView = new QueryView();
//        Neo4j neo4j = new Neo4j("neo4j+s://883e4884.databases.neo4j.io", "neo4j", "_aItXcqUubEUCoCcH6YLZwMro2SysElEYlRYTJupsI4");
//        MongoDB mongo =new MongoDB("tesebada","carlosdaniel","7rLrp3XKjgVR5WmM");
//        SQLServer sql = new SQLServer("tesebadabro.database.windows.net","TESEBADA","brocsm","PuroCoachMoy@");
//        SQLServer sqlConfiguration = new SQLServer("localhost", "DBConfiguration", "sa", "sa");
        MainController controller = new MainController(view, configurationView, transactionView, queryView, database, dbConfiguration);
        view.start();

    }
}