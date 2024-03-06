package Models;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB {
    private MongoDatabase database;
    private MongoClient mongoClient;
    private ClientSession clientSession;

    public MongoDB(String baseDatos,String usuario, String contrasena) {
        String connectionString = "mongodb+srv://"+usuario+":"+contrasena+"@cluster0.szlfm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        try {
            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("tesebada").withCodecRegistry(pojoCodecRegistry);

            database.runCommand(new Document("ping", 1));
            System.out.println("CONEXION MONGODB REALIZADA");
        } catch (MongoException e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean insert(String coleccion, Cliente cliente) {
        try{
            clientSession = mongoClient.startSession();
            clientSession.startTransaction();
            MongoCollection<Cliente>coleccionCliente = database.getCollection(coleccion,Cliente.class);
            coleccionCliente.insertOne(clientSession,cliente);
        }catch (MongoException e){
            rollbackTransaction();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private Bson generateFilter(ArrayList<ConditionChain> conditions, ArrayList<String> relations){
        Bson filterFinal = null;
        int cnt2 = 0;
        for(ConditionChain chain:conditions){
            Bson filterChain = null;
            int cnt = 0;
            for(Condition<Object> condition : chain.getChain()){
                if(filterChain == null){
                    filterChain = filter(condition);
                    continue;
                }
                if(chain.getRelation().get(cnt).equals("AND")){
                    filterChain = Filters.and(filterChain,filter(condition));
                }else{
                    filterChain = Filters.or(filterChain,filter(condition));
                }
                cnt++;
            }

            if(filterFinal == null){
                filterFinal = filterChain;
                continue;
            }
            if(relations.get(cnt2).equals("AND")){
                filterFinal = Filters.and(filterFinal,filterChain);
            }else{
                filterFinal = Filters.or(filterFinal,filterChain);
            }
            cnt2++;
        }
        return filterFinal;
    }

    private Bson filter(Condition<Object> condition){
        switch (condition.getComparison()){
            case "eq":
                return Filters.eq(condition.getAttribute(),condition.getValue());
        }
        return null;
    }

    public void commitTransaction() {
        clientSession.commitTransaction();
        clientSession.close();
        System.out.println("Transaction commited MongoDB");
    }

    public void rollbackTransaction() {
        clientSession.abortTransaction();
        clientSession.close();
        System.out.println("Transaction rolled back MongoDB");
    }

}
