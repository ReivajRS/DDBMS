package Controllers;

import Extra.Routines;
import Compiler.SyntaxAnalyzer.SyntaxAnalyzer;
import Models.*;
import Models.DatabaseModels.Database;
import Views.QueryView;

import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.util.ArrayList;

public class QueryController implements ActionListener {
    private final QueryView queryView;
    private final DatabaseConnections databaseConnections;

    public QueryController(QueryView queryView, DatabaseConnections databaseConnections){
        this.queryView= queryView;
        this.databaseConnections = databaseConnections;
        setListeners();
    }

    public void setVisible(boolean visible){
        queryView.getTxtQuery().setText("");
        queryView.setVisible(visible);
    }

    private void setListeners() {
        queryView.getBtnSearch().addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == queryView.getBtnSearch()){
            String query = queryView.getTxtQuery().getText().trim().toLowerCase();

            if(!SyntaxAnalyzer.analyzeStatement(query)){
                queryView.showMessage("Incorrect Query");
                return;
            }

            if(query.contains("where")){
                String whereStatement = query.split("where")[1].trim();
                if(whereStatement.contains("zona") || whereStatement.contains("estado")){
                    ArrayList<String> zones = SingletonDatabaseConfiguration.getInstance().getZones(query);
                    ArrayList<Database> databases = new ArrayList<>();

                    query = Routines.deleteZones(query);

                    Thread[] threads = new Thread[zones.size()];
                    int i = 0;

                    for(String zone:zones){
                        System.out.println("Request to fragment " + zone);
                        databases.add(databaseConnections.getDatabases().get(zone));
                        databases.getLast().setStatement(query);
                        threads[i++] = new Thread(databaseConnections.getDatabases().get(zone));
                    }

                    for (Thread thread : threads)
                        thread.start();

                    while (Routines.someThreadIsRunning(threads));

                    for (Database database : databases)
                        if (!database.isFinalStatus()) {
                            queryView.showMessage("fragment " + database.getClass().getName() + " failed");
                            return;
                        }

                    ArrayList<Cliente> customers = new ArrayList<>();
                    for (String zone : zones)
                        customers.addAll(databaseConnections.getDatabases().get(zone).getResults());
                    customers.sort(null);
                    queryView.fillTable(customers);
                    return;
                }
            }

            Thread[] threads = new Thread[databaseConnections.getDatabases().size()];
            int i = 0;
            for (Database database : databaseConnections.getDatabases().values()) {
                database.setStatement(query);
                threads[i] = new Thread(database);
                i++;
            }

            for (i = 0; i < databaseConnections.getDatabases().size(); i++)
                threads[i].start();

            while (Routines.someThreadIsRunning(threads));

            for (Database database : databaseConnections.getDatabases().values())
                if (!database.isFinalStatus()) {
                    queryView.showMessage("fragment " + database.getClass().getName() + " failed");
                    return;
                }

            ArrayList<Cliente> customers = new ArrayList<>();
            for(Database database: databaseConnections.getDatabases().values())
                customers.addAll(database.getResults());
            customers.sort(null);
            queryView.fillTable(customers);
        }
    }
}
