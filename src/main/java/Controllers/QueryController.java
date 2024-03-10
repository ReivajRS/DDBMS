package Controllers;

import Models.Database;
import Models.MongoDB;
import Models.Neo4j;
import Models.SQLServer;
import Views.QueryView;

import java.awt.event.ActionEvent;
import java.awt.event.*;

public class QueryController implements ActionListener {
    private QueryView queryView;
    private Database database;

    public QueryController(QueryView queryView, Database database){
        this.queryView= queryView;
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
            String query = queryView.getTxtQuery().getText();
            System.out.println(query);
            return;
        }
    }
}
