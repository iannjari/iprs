package dev.njari.document.grpc;

import iprs.document.v1.AmendDocumentCmd;
import iprs.document.v1.ApplyDocumentCmd;
import iprs.document.v1.DocumentServiceGrpc;
import iprs.document.v1.IssueDocumentCmd;
import iprs.person.v1.PersonServiceGrpc;
import iprs.person.v1.RecordDeathCmd;
import iprs.person.v1.RegisterBirthCmd;
import iprs.person.v1.UpdatePersonDetailsCmd;
import lombok.Getter;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author njari_mathenge
 * on 31/01/2024.
 * github.com/iannjari
 */

@Service
public class DocumentGrpcClient {

    @Getter
    @GrpcClient("iprs_svc_client")
    DocumentServiceGrpc.DocumentServiceBlockingStub client;

    public ApplyDocumentCmd applyForDocument(ApplyDocumentCmd cmd) {
        return client.applyForDocument(cmd);
    }

    public AmendDocumentCmd amendDocument(AmendDocumentCmd cmd) {
        return client.amendDocument(cmd);
    }

    public IssueDocumentCmd issueDocument(IssueDocumentCmd cmd) {
        return client.issueDocument(cmd);
    }
}
