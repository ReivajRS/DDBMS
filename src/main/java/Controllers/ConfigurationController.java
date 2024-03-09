package Controllers;

import Models.MongoDB;
import Models.Neo4j;
import Models.SQLServer;
import Views.ConfigurationView;

import java.awt.event.*;
import java.util.ArrayList;

public class ConfigurationController implements ActionListener {
    private MongoDB mongoDB;
    private Neo4j neo4j;
    private SQLServer sqlServer, dbConfiguration;
    private ConfigurationView configurationView;
    public ConfigurationController(ConfigurationView configurationView, MongoDB mongoDB, Neo4j neo4j, SQLServer sqlServer){
        this.configurationView= configurationView;
        this.mongoDB = mongoDB;
        this.neo4j = neo4j;
        this.sqlServer = sqlServer;
        dbConfiguration = new SQLServer("localhost", "DBConfiguration", "sa", "sa");
        setListeners();
    }

    public void setVisible(boolean visible){
        fillTable();
        configurationView.setVisible(visible);
    }

    private void fillTable() {
        configurationView.getTable().getModel().getDataVector().removeAllElements();
        ArrayList<String[]> configuration = dbConfiguration.recoverConfiguration();
        for (String[] tuple : configuration)
            configurationView.getTable().getModel().addRow(tuple);
        configurationView.revalidate();
        configurationView.repaint();
    }

    private void setListeners() {
        configurationView.getBtnAddFragment().addActionListener(this);
        configurationView.getBtnDeleteFragment().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == configurationView.getBtnAddFragment()) {
            configurationView.getDialogAddFragment().clear();
            configurationView.getDialogAddFragment().setVisible(true);
            return;
        }
        if (e.getSource() == configurationView.getBtnDeleteFragment()) {
            String answer = configurationView.showDeleteMessage();
            System.out.println(answer);
            if (answer.isEmpty())
                return;
            // TODO: Delete the fragment from the table on DBConfiguration
        }
    }
}
