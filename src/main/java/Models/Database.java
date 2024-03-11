package Models;

import java.util.ArrayList;

public abstract class Database implements Runnable {
    public abstract ArrayList<Cliente> makeQuery(String query);
    public abstract boolean makeTransaction(String transaction);
    public abstract void commitTransaction();
    public abstract void rollbackTransaction();
}
