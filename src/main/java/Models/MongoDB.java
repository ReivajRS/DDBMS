package Models;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.github.vincentrussell.query.mongodb.sql.converter.MongoDBQueryHolder;
import com.github.vincentrussell.query.mongodb.sql.converter.QueryConverter;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.util.ArrayList;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB extends Database{
    private MongoDatabase database;
    private MongoClient mongoClient;
    private ClientSession clientSession;
    private String collection;
    public MongoDB(String URI,String dbName,String collection, String usuario, String contrasena) {
        this.collection = collection;
        String connectionString = "mongodb+srv://" + usuario + ":" + contrasena + URI;

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
            database = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);

            database.runCommand(new Document("ping", 1));
            System.out.println("MongoDB connection established successfully");
        } catch (MongoException e) {
            System.out.println(e.getMessage());
        }

    }

    private ArrayList<Cliente> select(Bson attributes, Bson filter) {
        MongoCollection<Cliente> customersCollection = database.getCollection(collection, Cliente.class);

        ArrayList<Cliente> customers = new ArrayList<>();
        customersCollection.find(filter)
                .projection(attributes)
                .into(customers);

        return customers;
    }

    @Override
    public ArrayList<Cliente> makeQuery(String query) {
        query = query.toLowerCase();
        try {
            QueryConverter queryConverter = new QueryConverter.Builder().sqlString(query).build();
            MongoDBQueryHolder mongoDBQueryHolder = queryConverter.getMongoQuery();
            Document filter = mongoDBQueryHolder.getQuery();
            Document projection = mongoDBQueryHolder.getProjection();
            return select(projection, filter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Bson getUpdateChanges(String statement) {
        String updateSetRaw = statement.split("set|where")[1].trim();
        String[] updateSet = updateSetRaw.split(",");

        Bson updateChange = Filters.empty();
        for (String valor : updateSet) {
            valor = valor.trim();
            String[] partes = valor.split(" ");
            String attribute = partes[0].trim();

            if (partes.length == 3) {
                if (attribute.equals("credito") || attribute.equals("deuda")) {
                    updateChange = Updates.combine(updateChange, Updates.set(attribute, Double.parseDouble(partes[2])));
                } else {
                    updateChange = Updates.combine(updateChange,
                            Updates.set(attribute, partes[2].replace("'", "")));
                }
            } else {
                // ES COLUMNA = COLUMNA +-*/ VALOR
                if (partes[3].equals("+")) {
                    updateChange = Updates.combine(updateChange, Updates.inc(attribute, Double.parseDouble(partes[4])));
                } else if (partes[3].equals("-")) {
                    updateChange = Updates.combine(updateChange, Updates.inc(attribute, Double.parseDouble(partes[4]) * -1.0));
                } else if (partes[3].equals("*")) {
                    updateChange = Updates.combine(updateChange, Updates.mul(attribute, Double.parseDouble(partes[4])));
                } else if (partes[3].equals("/")) {
                    updateChange = Updates.combine(updateChange, Updates.mul(attribute, 1.0 / Double.parseDouble(partes[4])));
                }
            }
        }
        return updateChange;
    }

    private Bson getFilter(String statement) {
        // Fix bug
        if (statement.charAt(0) == 'u') {
            String[] partes = statement.split("set|where");
            statement = partes[0] + "set deuda = deuda where" + partes[2];
        }

        try {
            QueryConverter queryConverter = new QueryConverter.Builder().sqlString(statement).build();
            MongoDBQueryHolder mongoDBQueryHolder = queryConverter.getMongoQuery();
            return mongoDBQueryHolder.getQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Filters.empty();
        }
    }

    @Override
    public boolean makeTransaction(String statement) {
        statement = statement.toLowerCase().trim();

        // INSERT
        if (statement.contains("insert")) {
            String valuesRaw = statement.split("values")[1].trim();
            String[] values = valuesRaw.split(",");

            return insert(new Cliente(Integer.parseInt(values[0].trim().replace("(", "")),
                    values[1].trim().replace("'", ""),
                    values[2].trim().replace("'", ""),
                    Double.parseDouble(values[3].trim()), Double.parseDouble(values[4].trim().replace(")", ""))));
        }

        // UPDATE
        if (statement.contains("update")) {
            Bson updateChange = getUpdateChanges(statement);
            Bson filter = Filters.empty();
            if(statement.contains("where")){
                filter = getFilter(statement);
            }
            return update(filter, updateChange);
        }

        // DELETE
        if (statement.contains("delete")) {
            Bson filter = Filters.empty();
            if(statement.contains("where")){
                filter = getFilter(statement);
            }
            return delete(filter);
        }
        return false;
    }

    private boolean insert(Cliente customer) {
        try {
            clientSession = mongoClient.startSession();
            clientSession.startTransaction();
            MongoCollection<Cliente> customersCollection = database.getCollection(collection, Cliente.class);
            customersCollection.insertOne(clientSession, customer);
        } catch (MongoException e) {
            clientSession.abortTransaction();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean delete(Bson filter) {
        try {
            clientSession = mongoClient.startSession();
            clientSession.startTransaction();
            MongoCollection<Cliente> customersCollection = database.getCollection(collection, Cliente.class);
            customersCollection.deleteMany(clientSession, filter);
        } catch (MongoException e) {
            clientSession.abortTransaction();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean update(Bson filter, Bson updateFilter) {
        try {
            clientSession = mongoClient.startSession();
            clientSession.startTransaction();
            MongoCollection<Cliente> customersCollection = database.getCollection(collection, Cliente.class);
            customersCollection.updateMany(clientSession, filter, updateFilter);
        } catch (MongoException e) {
            clientSession.abortTransaction();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    @Override
    public void commitTransaction() {
        if (clientSession.hasActiveTransaction()) {
            clientSession.commitTransaction();
            System.out.println("Transaction commited MongoDB");
        }
        clientSession.close();
    }
    @Override
    public void rollbackTransaction() {
        if (clientSession.hasActiveTransaction()) {
            clientSession.abortTransaction();
            System.out.println("Transaction rolled back MongoDB");
        }
        clientSession.close();
    }

    @Override
    public void run() {
        if (statement.contains("select")) {
            results = makeQuery(statement);
            finalStatus = results != null;
            return;
        }
        if (results != null) results.clear();
        else results = new ArrayList<>();
        finalStatus = makeTransaction(statement);
    }
}
