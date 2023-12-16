package dev.njari.common_utils.persistence;

import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.bson.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author njari_mathenge
 * on 10/12/2023.
 * github.com/iannjari
 */

public abstract class AbstractMongoDBProtoRepository<X extends Message>{
    protected final MongoTemplate mt;
    protected final String collection;
    protected final Class<X> type;
    protected JsonFormat.Printer protobufJsonPrinter;
    protected JsonFormat.Parser protobufJsonParser;
    protected String messageIdField;
    private static final String MONGO_ID_FIELD = "_id";

    public AbstractMongoDBProtoRepository(MongoTemplate mt, String collection, Class<X> type, String idField){

        this.mt = mt;
        this.collection = collection;
        this.type = type;
        this.protobufJsonPrinter = JsonFormat.printer().preservingProtoFieldNames().includingDefaultValueFields();
        this.protobufJsonParser = JsonFormat.parser().ignoringUnknownFields();
        this.messageIdField = idField;
    }

    /**
     * Populate Message from BSON document
     * @param doc - BSON doc
     * @return -protobuf message
     */
    protected X populateMessage(Document doc) {

        try {
            var builder = Internal.getDefaultInstance(type).newBuilderForType();
            protobufJsonParser.merge(doc.toJson(), builder);
            return (X) builder;

        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("Exception during document parse in AbstractMongoDBProtoRepository: {}", e);
        }
    }


    // find one by id
     public X findById(String id) {
        Document document = mt.findById(id, Document.class, this.collection);
        if (document == null) return null;
        return populateMessage(document);
     }

    // findAll by query, paged and sorted
    public List<X> findAll(Query query) {
        List<Document> documents = mt.find(query, Document.class, this.collection);

        return documents.stream()
                .map(this::populateMessage)
                .toList();
    }

    // findAll no query, paged and sorted
    public List<X> findAll(Query query, Sort sort, Pageable pagination) {
        query.with(sort).with(pagination);
        List<Document> documents = mt.find(query, Document.class, this.collection);

        return documents.stream()
                .map(this::populateMessage)
                .toList();
    }

    // findAll by query, un-paged, unsorted

    // findAll no query, un-paged, unsorted
    public List<X> findAll() {
        List<Document> documents = mt.findAll(Document.class, this.collection);

        return documents.stream()
                .map(this::populateMessage)
                .toList();
    }

    // save
    public X save(X message) {
        Document jsonDoc = null;
        try {
            jsonDoc = Document.parse(protobufJsonPrinter.print(message));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        jsonDoc.put(MONGO_ID_FIELD,jsonDoc.getString(messageIdField));
        return populateMessage(mt.save(jsonDoc,this.collection));
    }

    // TODO: save all

    // delete
    public void delete(Query query) {
        mt.remove(query, Document.class, this.collection);
    }
    // delete all
    public void deleteAll(){
        mt.dropCollection(this.collection);
    }

    // count
    public long count(Query query){
        return mt.count(query,Document.class,this.collection);
    }

}
