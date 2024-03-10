package Controllers;

import Models.Database;
import Models.MongoDB;
import Models.Neo4j;
import Models.SQLServer;
import Views.ConfigurationView;
import Views.MainView;
import Views.QueryView;
import Views.TransactionView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {
    private MainView mainView;
    private ConfigurationController configurationController;
    private TransactionController transactionController;
    private QueryController queryController;
    private Database database;

    public MainController(MainView mainView,ConfigurationView configurationView,TransactionView transactionView, QueryView queryView, Database database, SQLServer dbConfiguration) {
        this.mainView = mainView;

        this.configurationController = new ConfigurationController(configurationView, database, dbConfiguration);
        this.transactionController = new TransactionController(transactionView, database);
        this.queryController = new QueryController(queryView, database);

        setListeners();
    }

    private void setListeners() {
        mainView.getBtnConfiguration().addActionListener(this);
        mainView.getBtnTransactions().addActionListener(this);
        mainView.getBtnQueries().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainView.getBtnConfiguration()) {
            System.out.println("Configuration");
            configurationController.setVisible(true);
            return;
        }
        if (e.getSource() == mainView.getBtnTransactions()) {
            System.out.println("Transactions");
            transactionController.setVisible(true);
            return;
        }
        if (e.getSource() == mainView.getBtnQueries()) {
            System.out.println("Queries");
            queryController.setVisible(true);
            return;
        }
    }
}
