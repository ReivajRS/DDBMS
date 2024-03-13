package Controllers;

import Extra.Routines;
import Compiler.SyntaxAnalyzer.SyntaxAnalyzer;
import Models.DatabaseModels.Database;
import Models.DatabaseConnections;
import Models.SingletonDatabaseConfiguration;
import Views.TransactionView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TransactionController implements ActionListener {
    private final TransactionView transactionView;
    private final DatabaseConnections databaseConnections;

    public TransactionController(TransactionView transactionView, DatabaseConnections databaseConnections) {
        this.transactionView = transactionView;
        this.databaseConnections = databaseConnections;

        setListeners();
    }

    public void setVisible(boolean visible){
        transactionView.getTxtTransaction().setText("");
        transactionView.setVisible(visible);
    }

    private void setListeners() {
        transactionView.getBtnExecute().addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == transactionView.getBtnExecute()){

            String statement = transactionView.getTxtTransaction().getText().trim().toLowerCase();

            if(!SyntaxAnalyzer.analyzeStatement(statement)){
                transactionView.showMessage("Incorrect Statement");
                return;
            }

            if(statement.contains("insert")){
                statement = SingletonDatabaseConfiguration.getInstance().putId(statement);
                String state = statement.split(",")[2].trim().replaceAll("'","");
                String zona = SingletonDatabaseConfiguration.getInstance().getZoneByState(state);
                if(zona.isEmpty()) {
                    transactionView.showMessage("State not found");
                    return;
                }
                if (databaseConnections.getDatabases().get(zona).checkConnection()
                        && databaseConnections.getDatabases().get(zona).makeTransaction(statement)) {
                    databaseConnections.getDatabases().get(zona).commitTransaction();
                    transactionView.showMessage("Customer has been added successfully");
                    return;
                }
                transactionView.showMessage("An error has occurred");
                return;
            }

            if(statement.contains("where")){
                String whereStatement = statement.split("where")[1].trim();
                if(whereStatement.contains("zona") || whereStatement.contains("estado")){
                    ArrayList<String> zones = SingletonDatabaseConfiguration.getInstance().getZones(statement);
                    ArrayList<Database> databases = new ArrayList<>();

                    statement = Routines.deleteZones(statement);

                    Thread[] threads = new Thread[zones.size()];
                    int i = 0;

                    for(String zone:zones){
                        System.out.println("Request to fragment " + zone);
                        databases.add(databaseConnections.getDatabases().get(zone));
                        databases.getLast().setStatement(statement);
                        threads[i++] = new Thread(databaseConnections.getDatabases().get(zone));
                    }

                    for (Thread thread : threads)
                        thread.start();

                    while (Routines.someThreadIsRunning(threads));

                    ArrayList<String> namesOfDatabasesThatFailed = new ArrayList<>();
                    for (Database database : databases)
                        if (!database.isFinalStatus())
                            namesOfDatabasesThatFailed.add(database.getClass().getName());

                    boolean someTransactionWentWrong = !namesOfDatabasesThatFailed.isEmpty();

                    if (someTransactionWentWrong) {
                        transactionView.showMessage("The following fragments failed:\n" +
                                namesOfDatabasesThatFailed.stream().map(Object::toString).collect(Collectors.joining("\n"))
                        );
                        for (Database database : databases)
                            database.rollbackTransaction();
                        return;
                    }

                    for (Database database : databases)
                        database.commitTransaction();
                    transactionView.showMessage("Transaction commited");
                    return;
                }
            }

            Thread[] threads = new Thread[databaseConnections.getDatabases().size()];
            System.out.println("Request to all fragments");
            int i = 0;
            for (Database database : databaseConnections.getDatabases().values()){
                threads[i] = new Thread(database);
                database.setStatement(statement);
                i++;
            }

            for (Thread thread : threads)
                thread.start();

            while (Routines.someThreadIsRunning(threads));

            ArrayList<String> namesOfDatabasesThatFailed = new ArrayList<>();
            for (Database database : databaseConnections.getDatabases().values())
                if (!database.isFinalStatus())
                    namesOfDatabasesThatFailed.add(database.getClass().getName());

            boolean someTransactionWentWrong = !namesOfDatabasesThatFailed.isEmpty();

            if (someTransactionWentWrong) {
                transactionView.showMessage("The following fragments failed:\n" +
                        namesOfDatabasesThatFailed.stream().map(Object::toString).collect(Collectors.joining("\n"))
                );
                for (Database database : databaseConnections.getDatabases().values())
                    database.rollbackTransaction();
                return;
            }

            for (Database database : databaseConnections.getDatabases().values())
                database.commitTransaction();
            transactionView.showMessage("Transaction commited");
        }
    }
}
