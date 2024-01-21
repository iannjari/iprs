package dev.njari.document.repository;

import dev.njari.common_utils.persistence.AbstractMongoDBProtoRepository;
import iprs.document.v1.Passport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author njari_mathenge
 * on 16/12/2023.
 * github.com/iannjari
 */

@Repository
public class PassportRepository extends AbstractMongoDBProtoRepository<Passport> {
    private static final String ID_FIELD = "id";
    private static final String COLLECTION = "passport";

    public PassportRepository(MongoTemplate mt) {
        super(mt, COLLECTION, Passport.class, ID_FIELD);
    }
}
