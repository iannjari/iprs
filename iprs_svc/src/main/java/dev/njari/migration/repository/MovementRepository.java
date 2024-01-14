package dev.njari.migration.repository;

import dev.njari.common_utils.persistence.AbstractMongoDBProtoRepository;
import iprs.migration.v1.Movement;
import iprs.person.v1.Person;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author njari_mathenge
 * on 16/12/2023.
 * github.com/iannjari
 */

@Repository
public class MovementRepository extends AbstractMongoDBProtoRepository<Movement> {
    private static final String ID_FIELD = "id";
    private static final String COLLECTION = "movement";

    public MovementRepository(MongoTemplate mt) {
        super(mt, COLLECTION, Movement.class, ID_FIELD);
    }
}
