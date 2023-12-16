package dev.njari.person.repository;

import dev.njari.common_utils.persistence.AbstractMongoDBProtoRepository;
import iprs.person.v1.Person;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author njari_mathenge
 * on 16/12/2023.
 * github.com/iannjari
 */

@Repository
public class PersonRepository extends AbstractMongoDBProtoRepository<Person> {
    private static final String ID_FIELD = "id";
    private static final String COLLECTION = "person";

    public PersonRepository(MongoTemplate mt) {
        super(mt, COLLECTION, Person.class, ID_FIELD);
    }
}
