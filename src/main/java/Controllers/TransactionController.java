package Controllers;

import Compiler.SyntaxAnalyzer.SyntaxAnalyzer;
import Models.Database;
import Models.DatabaseConnections;
import Models.SQLServer;
import Views.TransactionView;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionController implements ActionListener {
    private TransactionView transactionView;
    private DatabaseConnections databaseConnections;
    private SQLServer dbConfiguration;

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
        Pattern patron = Pattern.compile("\\b(estado|zona)\\s*(?:=|in|not\\s+in|!=)\\s*('([^']*)'|\\([^)]*\\))");
        Matcher matcher = patron.matcher(statement);
        String query = "select distinct zone from state_zone where ";
        while (matcher.find()) {
            String condition = matcher.group(0);
            query = query + condition + " or ";
        }
        query = query + "1!=1";
        query = query.replaceAll("zona","zone");
        query = query.replaceAll("estado","state");
        return dbConfiguration.getZonesByQuery(query);
    }

    private String deleteZones(String statement){
        Pattern patron = Pattern.compile("\\b(estado|zona)\\s*(?:=|in|not\\s+in|!=)\\s*('([^']*)'|\\([^)]*\\))");
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
                }else{
                    JOptionPane.showMessageDialog(null,"An error occurred");
                }

                return;
            }

            if(statement.contains("where")){
                String whereStatement = statement.split("where")[1].trim();
                if(whereStatement.contains("zona") || whereStatement.contains("estado")){
                    ArrayList<String> zones = getZones(statement);
                    statement = deleteZones(statement);
                    boolean isWorking = true;
                    for(String zone:zones){
                        System.out.println("Request to fragment " + zone);
                        if(!databaseConnections.getDatabases().get(zone.toLowerCase()).makeTransaction(statement)){
                            isWorking = false;
                        }
                    }
                    if(isWorking){
                        for(String zone:zones){
                            databaseConnections.getDatabases().get(zone.toLowerCase()).commitTransaction();
                        }
                    }else{
                        for(String zone:zones){
                            databaseConnections.getDatabases().get(zone.toLowerCase()).rollbackTransaction();
                        }
                    }
                    return;
                }
            }


            System.out.println("Request to all fragments");
            boolean isWorking = true;
            for(Database database : databaseConnections.getDatabases().values()){
                if(!database.makeTransaction(statement)){
                    isWorking=false;
                }
            }
            if(isWorking){
                for(Database database : databaseConnections.getDatabases().values()){
                    database.commitTransaction();
                }
            }else{
                for(Database database : databaseConnections.getDatabases().values()){
                    database.rollbackTransaction();
                }
            }
            return;
        }
    }
}
