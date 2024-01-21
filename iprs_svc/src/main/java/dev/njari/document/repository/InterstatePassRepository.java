package dev.njari.document.repository;

import dev.njari.common_utils.persistence.AbstractMongoDBProtoRepository;
import iprs.document.v1.InterstatePass;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author njari_mathenge
 * on 16/12/2023.
 * github.com/iannjari
 */

@Repository
public class InterstatePassRepository extends AbstractMongoDBProtoRepository<InterstatePass> {
    private static final String ID_FIELD = "id";
    private static final String COLLECTION = "interstate_pass";

    public InterstatePassRepository(MongoTemplate mt) {
        super(mt, COLLECTION, InterstatePass.class, ID_FIELD);
    }
}
