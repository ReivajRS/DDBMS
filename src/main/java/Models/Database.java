package Models;

import java.util.ArrayList;

public abstract class Database implements Runnable {
    protected String statement;
    protected boolean finalStatus;
    protected ArrayList<Cliente> results;
    public abstract ArrayList<Cliente> makeQuery(String query);
    public abstract boolean makeTransaction(String transaction);
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