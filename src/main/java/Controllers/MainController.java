package Controllers;

import Models.DatabaseConnections;
import Views.ConfigurationView;
import Views.MainView;
import Views.QueryView;
import Views.TransactionView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {
    private final MainView mainView;
    private final ConfigurationController configurationController;
    private final TransactionController transactionController;
    private final QueryController queryController;

    public MainController(MainView mainView, ConfigurationView configurationView, TransactionView transactionView,
                          QueryView queryView, DatabaseConnections databaseConnections) {
        this.mainView = mainView;

        this.configurationController = new ConfigurationController(configurationView);
        this.transactionController = new TransactionController(transactionView, databaseConnections);
        this.queryController = new QueryController(queryView, databaseConnections);

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
