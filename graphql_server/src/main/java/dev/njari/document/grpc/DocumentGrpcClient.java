package dev.njari.document.grpc;

import iprs.document.v1.AmendDocumentCmd;
import iprs.document.v1.ApplyDocumentCmd;
import iprs.document.v1.DocumentServiceGrpc;
import iprs.document.v1.IssueDocumentCmd;
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
    DocumentServiceGrpc.DocumentServiceBlockingStub documentServiceBlockingStub;

    public ApplyDocumentCmd applyForDocument(ApplyDocumentCmd cmd) {
        return documentServiceBlockingStub.applyForDocument(cmd);
    }

    public AmendDocumentCmd amendDocument(AmendDocumentCmd cmd) {
        return documentServiceBlockingStub.amendDocument(cmd);
    }

    public IssueDocumentCmd issueDocument(IssueDocumentCmd cmd) {
        return documentServiceBlockingStub.issueDocument(cmd);
    }
}
