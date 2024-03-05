import Controllers.MainController;
import Views.View;
import Models.MongoDB;
import Models.SQLServer;
import Models.Neo4j;
import Models.Cliente;

public class Main {
    public static void main(String[] args) {
        /*
        Neo4j neo4j = new Neo4j("neo4j+s://883e4884.databases.neo4j.io", "neo4j", "_aItXcqUubEUCoCcH6YLZwMro2SysElEYlRYTJupsI4");
        MongoDB mongo =new MongoDB("tesebada","carlosdaniel","7rLrp3XKjgVR5WmM");
        SQLServer sql = new SQLServer("tesebadabro.database.windows.net","TESEBADA","brocsm","PuroCoachMoy@");

        boolean jalo = mongo.insert("clientes",new Cliente(12,"Carlos Beltran","Chihuahua",10000,600));
        boolean jalo2 = sql.insert(new Cliente(9,"Angel Olan","Aguascalientes",60,300));
        boolean jalo3 = neo4j.insert(new Cliente(11,"Javier Rivera","Chiapas",500,2));
        if(jalo && jalo2 && jalo3){
            mongo.commitTransaction();
            sql.commitTransaction();
            neo4j.commitTransaction();
        }else{
            if(jalo){
                mongo.rollbackTransaction();
            }
            if(jalo2){
                sql.rollbackTransaction();
            }
            if(jalo3){
                neo4j.rollbackTransaction();
            }
        }
        */
        View view = new View();
        Neo4j neo4j = new Neo4j("neo4j+s://883e4884.databases.neo4j.io", "neo4j", "_aItXcqUubEUCoCcH6YLZwMro2SysElEYlRYTJupsI4");
        MongoDB mongo =new MongoDB("tesebada","carlosdaniel","7rLrp3XKjgVR5WmM");
//        SQLServer sql = new SQLServer("tesebadabro.database.windows.net","TESEBADA","brocsm","PuroCoachMoy@");
//        MainController controller = new MainController(view, mongo, neo4j, sql);
        view.start();
    }
}