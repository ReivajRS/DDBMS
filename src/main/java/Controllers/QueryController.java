package Controllers;

import Compiler.SyntaxAnalyzer.SyntaxAnalyzer;
import Models.Database;
import Models.DatabaseConnections;
import Models.Cliente;
import Models.Fragment;
import Models.SQLServer;
import Views.QueryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryController implements ActionListener {
    private QueryView queryView;
    private DatabaseConnections databaseConnections;
    private SQLServer dbConfiguration;

    public QueryController(QueryView queryView, DatabaseConnections databaseConnections, SQLServer dbConfiguration){
        this.queryView= queryView;
        this.databaseConnections = databaseConnections;
        this.dbConfiguration = dbConfiguration;
        setListeners();
    }

    public void setVisible(boolean visible){
        queryView.setVisible(visible);
    }

    private void setListeners() {
        queryView.getBtnSearch().addActionListener(this);
    }

    private void fillTable(ArrayList<Cliente> customers) {
        queryView.getTable().getModel().getDataVector().removeAllElements();
        for (Cliente customer : customers) {
            String[] tuple = new String[queryView.getTable().getTable().getColumnCount()];
            tuple[0] = customer.getIdcliente() == null ? "" :customer.getIdcliente()+"";
            tuple[1] = customer.getNombre() == null ? "" : customer.getNombre();
            tuple[2] = customer.getEstado() == null ? "" : customer.getEstado();
            tuple[3] = customer.getCredito() == null ? "" : customer.getCredito()+"";
            tuple[4] = customer.getDeuda() == null ? "" : customer.getDeuda()+"";
            queryView.getTable().getModel().addRow(tuple);
        }
        queryView.refresh();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == queryView.getBtnSearch()){
            String query = queryView.getTxtQuery().getText().trim().toLowerCase();

            if(!SyntaxAnalyzer.analyzeStatement(query)){
                JOptionPane.showMessageDialog(null,"Incorrect Query");
                return;
            }

            if(query.contains("where")){
                String whereStatement = query.split("where")[1].trim();
                if(whereStatement.contains("zona") || whereStatement.contains("estado")){
                    ArrayList<String> zones = getZones(query);
                    query = deleteZones(query);
                    ArrayList<Cliente> customers = new ArrayList<>();

                    for(String zone:zones){
                        customers.addAll(databaseConnections.getDatabases().get(zone.toLowerCase()).makeQuery(query));
                        System.out.println("Request to fragment " + zone);
                    }
                   customers.sort(null);
                    fillTable(customers);
                    return;
                }
            }

            System.out.println("Request to all fragments");
            ArrayList<Cliente> customers = new ArrayList<>();
            for(Database database: databaseConnections.getDatabases().values()){
                customers.addAll(database.makeQuery(query));
            }
            customers.sort(null);
            fillTable(customers);

            return;
        }
    }
}
