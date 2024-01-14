package dev.njari.document.repository;

import dev.njari.common_utils.persistence.AbstractMongoDBProtoRepository;
import iprs.document.v1.NationalIdCard;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author njari_mathenge
 * on 16/12/2023.
 * github.com/iannjari
 */

@Repository
public class NationalIdRepository extends AbstractMongoDBProtoRepository<NationalIdCard> {
    private static final String ID_FIELD = "id";
    private static final String COLLECTION = "national_identification_card";

    public NationalIdRepository(MongoTemplate mt) {
        super(mt, COLLECTION, NationalIdCard.class, ID_FIELD);
    }
}
