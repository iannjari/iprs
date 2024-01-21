package dev.njari.document.repository;

import dev.njari.common_utils.persistence.AbstractMongoDBProtoRepository;
import iprs.document.v1.DeathCertificate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author njari_mathenge
 * on 16/12/2023.
 * github.com/iannjari
 */

@Repository
public class DeathCertificateRepository extends AbstractMongoDBProtoRepository<DeathCertificate> {
    private static final String ID_FIELD = "id";
    private static final String COLLECTION = "death_certificate";

    public DeathCertificateRepository(MongoTemplate mt) {
        super(mt, COLLECTION, DeathCertificate.class, ID_FIELD);
    }
}
