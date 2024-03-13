package Models.DatabaseModels;

import Models.Cliente;

import java.util.ArrayList;

public abstract class Database implements Runnable {
    protected String statement;
    protected boolean finalStatus;
    protected ArrayList<Cliente> results;
    public abstract void makeQuery(String query);
    public abstract boolean makeTransaction(String transaction);
    public abstract boolean checkConnection();
    public abstract void commitTransaction();
    public abstract void rollbackTransaction();

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public boolean isFinalStatus() {
        return finalStatus;
    }

    public ArrayList<Cliente> getResults() {
        return results;
    }
}