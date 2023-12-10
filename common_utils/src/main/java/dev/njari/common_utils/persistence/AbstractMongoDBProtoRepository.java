package dev.njari.common_utils.persistence;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.function.Supplier;

/**
 * @author njari_mathenge
 * on 10/12/2023.
 * github.com/iannjari
 */

public abstract class AbstractMongoDBProtoRepository<X extends Message>{
    protected final MongoTemplate mt;
    protected final String collection;
    protected final Supplier<X> supplier;
    protected JsonFormat.Printer protobufJsonPrinter;
    protected JsonFormat.Parser protobufJsonParser;

    public AbstractMongoDBProtoRepository(MongoTemplate mt, String table, Supplier<X> supplier){

        this.mt = mt;
        this.collection = table;
        this.supplier = supplier;
        this.protobufJsonPrinter = JsonFormat.printer().preservingProtoFieldNames().includingDefaultValueFields();
        this.protobufJsonParser = JsonFormat.parser().ignoringUnknownFields();
    }

    /**
     * Populate Message from BSON document
     * @param doc - BSON doc
     * @return -protobuf message
     */
    protected Message populateMessage(Document doc) {

        try {
            var builder = supplier.get().newBuilderForType();
            protobufJsonParser.merge(doc.toJson(), builder);
            return builder.build();

        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("Exception during document parse in AbstractMongoDBProtoRepository: {}", e);
        }
    }


    // find one by id

    // find one by query

    // findAll by query, paged and sorted

    // findAll no query, paged and sorted

    // findAll by query, un-paged, unsorted

    // findAll no query, un-paged, unsorted

    // save

    // save all

    // delete

    // delete all

    // count

}
