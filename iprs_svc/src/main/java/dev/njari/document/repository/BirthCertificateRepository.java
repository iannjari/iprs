package dev.njari.document.repository;

import dev.njari.common_utils.persistence.AbstractMongoDBProtoRepository;
import iprs.document.v1.BirthCertificate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author njari_mathenge
 * on 16/12/2023.
 * github.com/iannjari
 */

@Repository
public class BirthCertificateRepository extends AbstractMongoDBProtoRepository<BirthCertificate> {
    private static final String ID_FIELD = "id";
    private static final String COLLECTION = "birth_certificate";

    public BirthCertificateRepository(MongoTemplate mt) {
        super(mt, COLLECTION, BirthCertificate.class, ID_FIELD);
    }
}
