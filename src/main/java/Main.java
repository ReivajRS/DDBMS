import Compiler.SyntaxAnalyzer.SyntaxAnalyzer;
import Controllers.MainController;
import Models.*;
import Views.ConfigurationView;
import Views.MainView;
import Views.QueryView;
import Views.TransactionView;

import javax.swing.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SQLServer dbConfiguration = new SQLServer("localhost", "DBConfiguration", "sa", "sa");
        DatabaseConnections databaseConnections = new DatabaseConnections(dbConfiguration.selectFragments());

        // VISTAS
        MainView view = new MainView();
        ConfigurationView configurationView = new ConfigurationView();
        TransactionView transactionView = new TransactionView();
        QueryView queryView = new QueryView();

        MainController mainController = new MainController(view,configurationView,transactionView,queryView,databaseConnections,dbConfiguration);
        view.start();
    }
}