package Models;

import Models.DatabaseModels.SQLServer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingletonDatabaseConfiguration {
    private static SingletonDatabaseConfiguration instance;
    private final SQLServer database;
    private SingletonDatabaseConfiguration () {
        database = new SQLServer("25.2.140.49", "DBConfiguration", "sa", "sa");
    }

    public static SingletonDatabaseConfiguration getInstance() {
        if (instance == null) instance = new SingletonDatabaseConfiguration();
        return instance;
    }

    public ArrayList<Fragment> selectFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        try {
            Statement st = database.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Fragment");
            while (rs.next()) {
                Fragment fragment = new Fragment(
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10)
                );
                fragments.add(fragment);
            }
            st.close();
            return fragments;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return fragments;
    }

    public String getZoneByState(String state){
        try {
            Statement st = database.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT ZONE FROM STATE_ZONE WHERE STATE = '"+state+"'");
            rs.next();
            if(rs.wasNull()) return "";
            return rs.getString(1).toLowerCase();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    public ArrayList<String> getZonesByQuery(String query){
        ArrayList<String> zones = new ArrayList<>();
        try {
            Statement st = database.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                zones.add(rs.getString(1).toLowerCase());
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return zones;
        }
        return zones;
    }

    public int getID() {
        String query= "DECLARE @ID INT "+
                "EXEC SP_GetID @ID OUTPUT "+
                "SELECT ID = @ID";
        try{
            Statement st = database.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    public ArrayList<String> getZones(String statement){
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
        return getZonesByQuery(query);
    }

    public String putId(String query) {
        int id =  getID();
        String[] partes = query.split("\\(");
        return partes[0] + "(" + id + "," + partes[1];
    }

    public boolean addFragment(String[] values) {
        try {
            PreparedStatement st = database.getConnection().prepareStatement("INSERT INTO Fragment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < values.length; i++)
                st.setString(i + 1, values[i]);
            st.executeUpdate();
            st.close();
            return true;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteFragment(int id) {
        try {
            PreparedStatement st = database.getConnection().prepareStatement("DELETE FROM Fragment WHERE IDFragment = ?");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
            return true;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}