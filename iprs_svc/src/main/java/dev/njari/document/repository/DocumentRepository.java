package dev.njari.document.repository;

import dev.njari.common_utils.persistence.AbstractMongoDBProtoRepository;
import iprs.document.v1.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author njari_mathenge
 * on 16/12/2023.
 * github.com/iannjari
 */

@Repository
public class DocumentRepository extends AbstractMongoDBProtoRepository<Document> {
    private static final String ID_FIELD = "id";
    private static final String COLLECTION = "document";

    public DocumentRepository(MongoTemplate mt) {
        super(mt, COLLECTION, Document.class, ID_FIELD);
    }
}
