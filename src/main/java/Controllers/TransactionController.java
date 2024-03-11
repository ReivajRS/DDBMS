package Controllers;

import Extra.Routines;
import Compiler.SyntaxAnalyzer.SyntaxAnalyzer;
import Models.Database;
import Models.DatabaseConnections;
import Models.SQLServer;
import Views.TransactionView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class TransactionController implements ActionListener {
    private final TransactionView transactionView;
    private final DatabaseConnections databaseConnections;
    private final SQLServer dbConfiguration;

    public TransactionController(TransactionView transactionView, DatabaseConnections databaseConnections, SQLServer dbConfiguration) {
        this.transactionView = transactionView;
        this.databaseConnections = databaseConnections;
        this.dbConfiguration =dbConfiguration;

        setListeners();
    }

    public void setVisible(boolean visible){
        transactionView.setVisible(visible);
    }

    private void setListeners() {
        transactionView.getBtnExecute().addActionListener(this);
    }

    private ArrayList<String> getZones(String statement){
        Pattern patron = compile("\\b(estado|zona)\\s*(?:=|in|not\\s+in|!=)\\s*('([^']*)'|\\([^)]*\\))");
        Matcher matcher = patron.matcher(statement);
        StringBuilder query = new StringBuilder("select distinct zone from state_zone where ");
        while (matcher.find()) {
            String condition = matcher.group(0);
            query.append(condition).append(" or ");
        }
        query.append("1!=1");
        query = new StringBuilder(query.toString().replaceAll("zona", "zone"));
        query = new StringBuilder(query.toString().replaceAll("estado", "state"));
        return dbConfiguration.getZonesByQuery(query.toString());
    }

    private String deleteZones(String statement){
        Pattern patron = compile("\\b(estado|zona)\\s*(?:=|in|not\\s+in|!=)\\s*('([^']*)'|\\([^)]*\\))");
        Matcher matcher = patron.matcher(statement);
        while (matcher.find()) {
            String condition = matcher.group(0);
            if (matcher.group(1).equals("zona"))
                statement = statement.replace(condition, "idcliente > 0");
        }
        return statement;
    }


    private String putId(String query) {
        int id =  dbConfiguration.getID();
        String[] partes = query.split("\\(");
        return partes[0] + "(" + id + "," + partes[1];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == transactionView.getBtnExecute()){
            String statement = transactionView.getTxtTransaction().getText().trim().toLowerCase();

            if(!SyntaxAnalyzer.analyzeStatement(statement)){
                JOptionPane.showMessageDialog(null,"Incorrect Statement");
                return;
            }

            if(statement.contains("insert")){
                statement = putId(statement);
                String state = statement.split(",")[2].trim().replaceAll("'","");
                String zona = dbConfiguration.getZoneByState(state);
                if(zona.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"State not found");
                    return;
                }
                if(databaseConnections.getDatabases().get(zona).makeTransaction(statement)){
                    databaseConnections.getDatabases().get(zona).commitTransaction();
                    JOptionPane.showMessageDialog(null,"Customer has been added successfully");
                    return;
                }
                JOptionPane.showMessageDialog(null,"An error has occurred");
                return;
            }

            if(statement.contains("where")){
                String whereStatement = statement.split("where")[1].trim();
                if(whereStatement.contains("zona") || whereStatement.contains("estado")){
                    ArrayList<String> zones = getZones(statement);
                    ArrayList<Database> databases = new ArrayList<>();

                    statement = deleteZones(statement);

                    Thread[] threads = new Thread[zones.size()];
                    int i = 0;

                    for(String zone:zones){
                        System.out.println("Request to fragment " + zone);
                        databases.add(databaseConnections.getDatabases().get(zone.toLowerCase()));
                        databases.getLast().setStatement(statement);
                        threads[i++] = new Thread(databaseConnections.getDatabases().get(zone));
                    }

                    for (Thread thread : threads)
                        thread.start();

                    while (Routines.someThreadIsRunning(threads));

                    boolean someTransactionWentWrong = false;
                    for (Database database : databases)
                        if (!database.isFinalStatus()) {
                            someTransactionWentWrong = true;
                            break;
                        }

                    if (someTransactionWentWrong) {
                        JOptionPane.showMessageDialog(null, "Some fragment failed");
                        for (Database database : databases)
                            database.rollbackTransaction();
                        return;
                    }

                    for (Database database : databases)
                        database.commitTransaction();

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

            boolean someTransactionWentWrong = false;
            for (Database database : databaseConnections.getDatabases().values())
                if (!database.isFinalStatus()) {
                    someTransactionWentWrong = true;
                    break;
                }

            if (someTransactionWentWrong) {
                JOptionPane.showMessageDialog(null, "Some fragment failed");
                for (Database database : databaseConnections.getDatabases().values())
                    database.rollbackTransaction();
                return;
            }

            for (Database database : databaseConnections.getDatabases().values())
                database.commitTransaction();
        }
    }
}
