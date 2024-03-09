package Controllers;

import Models.MongoDB;
import Models.Neo4j;
import Models.SQLServer;
import Views.QueryView;

import java.awt.event.ActionEvent;
import java.awt.event.*;

public class QueryController implements ActionListener {
    private MongoDB mongoDB;
    private Neo4j neo4j;
    private SQLServer sqlServer;
    private QueryView queryView;

    public QueryController(QueryView queryView, MongoDB mongoDB, Neo4j neo4j, SQLServer sqlServer){
        this.queryView= queryView;
        this.mongoDB = mongoDB;
        this.neo4j = neo4j;
        this.sqlServer = sqlServer;
    }

    public void setVisible(boolean visible){
        queryView.setVisible(visible);
    }

    private void setListeners() {
        queryView.getBtnSearch().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == queryView.getBtnSearch()){

            return;
        }
    }
}
