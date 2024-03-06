package Controllers;

import Models.MongoDB;
import Models.Neo4j;
import Models.SQLServer;
import Views.ConfigurationView;

import java.awt.event.*;

public class ConfigurationController implements ActionListener {
    private MongoDB mongoDB;
    private Neo4j neo4j;
    private SQLServer sqlServer;
    private ConfigurationView configurationView;
    public ConfigurationController(ConfigurationView configurationView, MongoDB mongoDB, Neo4j neo4j, SQLServer sqlServer){
        this.configurationView= configurationView;
        this.mongoDB = mongoDB;
        this.neo4j = neo4j;
        this.sqlServer = sqlServer;
        setListener();
    }

    public void setVisible(boolean visible){
        configurationView.setVisible(visible);
    }

    private void setListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
