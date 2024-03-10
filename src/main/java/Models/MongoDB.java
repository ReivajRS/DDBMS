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
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Projections.fields;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB {
    private MongoDatabase database;
    private MongoClient mongoClient;
    private ClientSession clientSession;
    private final String COLLECTION = "clientes", DATABASE = "tesebada";

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
            database = mongoClient.getDatabase(DATABASE).withCodecRegistry(pojoCodecRegistry);

            database.runCommand(new Document("ping", 1));
            System.out.println("CONEXION MONGODB REALIZADA");
        } catch (MongoException e) {
            System.out.println(e.getMessage());
        }

    }



    /*private Bson generateFilter(ArrayList<ConditionChain> conditions, ArrayList<String> logical){
        Bson filterFinal = null;
        int logicalCnt = 0;
        for(ConditionChain chain:conditions){
            Bson filterChain =generateFilterByChain((chain));
            if(filterFinal == null){
                filterFinal = filterChain;
                continue;
            }
            if(logical.get(logicalCnt).equals("AND")){
                filterFinal = Filters.and(filterFinal,filterChain);
            }else{
                filterFinal = Filters.or(filterFinal,filterChain);
            }
            logicalCnt++;
        }
        return filterFinal;
    }

    private Bson generateFilterByChain(ConditionChain chain){
        Bson filterChain = null;
        int logicalCnt = 0;
        for(Condition<Object> condition : chain.getChain()){
            if(filterChain == null){
                filterChain = filter(condition);
                continue;
            }
            if(chain.getLogical().get(logicalCnt).equals("AND")){
                filterChain = Filters.and(filterChain,filter(condition));
            }else{
                filterChain = Filters.or(filterChain,filter(condition));
            }
            logicalCnt++;
        }
        return filterChain;
    }

    private Bson generateFilter(Condition<Object> condition){
        switch (condition.getComparison()){
            case "eq":
                return Filters.eq(condition.getAttribute(),condition.getValue());
            case "gt":
                return Filters.gt(condition.getAttribute(),condition.getValue());
            case "gte":
                return Filters.gte(condition.getAttribute(),condition.getValue());
            case "in":
                return Filters.in(condition.getAttribute(),condition.getValue());
            case "lt":
                return Filters.lt(condition.getAttribute(),condition.getValue());
            case "lte":
                return Filters.lte(condition.getAttribute(),condition.getValue());
            case "ne":
                return Filters.ne(condition.getAttribute(),condition.getValue());
            case "nin":
                return Filters.nin(condition.getAttribute(),condition.getValue());
        }
        return null;
    }*/




    public ArrayList<Cliente> select(Document attributes, Bson filter){
        MongoCollection<Cliente> customersCollection = database.getCollection(COLLECTION,Cliente.class);

        ArrayList<Cliente> customers = new ArrayList<>();
        customersCollection.find(filter)
                .projection(attributes)
                .into(customers);

        return customers;
    }

    public boolean insert(Cliente customer) {
        try{
            clientSession = mongoClient.startSession();
            clientSession.startTransaction();
            MongoCollection<Cliente> customersCollection = database.getCollection(COLLECTION,Cliente.class);
            customersCollection.insertOne(clientSession,customer);
        }catch (MongoException e){
            clientSession.abortTransaction();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delete(Bson filter){
        try{
            clientSession = mongoClient.startSession();
            clientSession.startTransaction();
            MongoCollection<Cliente> customersCollection = database.getCollection(COLLECTION,Cliente.class);
            customersCollection.deleteMany(clientSession,filter);
        }catch (MongoException e){
            clientSession.abortTransaction();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(Bson filter,Bson updateFilter){
        try{
            clientSession = mongoClient.startSession();
            clientSession.startTransaction();
            MongoCollection<Cliente> customersCollection = database.getCollection(COLLECTION,Cliente.class);
            customersCollection.updateMany(clientSession,filter,updateFilter);
        }catch (MongoException e){
            clientSession.abortTransaction();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void commitTransaction() {
        if(clientSession.hasActiveTransaction()){
            clientSession.commitTransaction();
            System.out.println("Transaction commited MongoDB");
        }
        clientSession.close();
    }

    public void rollbackTransaction() {
        if(clientSession.hasActiveTransaction()){
            clientSession.abortTransaction();
            System.out.println("Transaction rolled back MongoDB");
        }
        clientSession.close();
    }

}
