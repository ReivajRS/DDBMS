package Controllers;

import Models.DatabaseConnections;
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
    private DatabaseConnections databaseConnections;

    public MainController(MainView mainView, ConfigurationView configurationView, TransactionView transactionView,
                          QueryView queryView, DatabaseConnections databaseConnections, SQLServer dbConfiguration) {
        this.mainView = mainView;

        this.configurationController = new ConfigurationController(configurationView, databaseConnections, dbConfiguration);
        this.transactionController = new TransactionController(transactionView, databaseConnections,dbConfiguration);
        this.queryController = new QueryController(queryView, databaseConnections,dbConfiguration);

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
            configurationController.setVisible(true);
            return;
        }
        if (e.getSource() == mainView.getBtnTransactions()) {
            transactionController.setVisible(true);
            return;
        }
        if (e.getSource() == mainView.getBtnQueries()) {
            queryController.setVisible(true);
            return;
        }
    }
}
