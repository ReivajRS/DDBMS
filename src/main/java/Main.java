import Controllers.MainController;
import Models.*;
import Views.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Connecting to fragments...");
        DatabaseConnections databaseConnections = new DatabaseConnections();

        MainView view = new MainView();
        ConfigurationView configurationView = new ConfigurationView();
        TransactionView transactionView = new TransactionView();
        QueryView queryView = new QueryView();

        new MainController(view,configurationView,transactionView,queryView,databaseConnections);
        view.start();
    }
}