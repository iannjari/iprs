package dev.njari.document.grpc;

import dev.njari.document.service.DocumentService;
import io.grpc.stub.StreamObserver;
import iprs.document.v1.AmendDocumentCmd;
import iprs.document.v1.ApplyDocumentCmd;
import iprs.document.v1.DocumentServiceGrpc;
import iprs.document.v1.IssueDocumentCmd;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author njari_mathenge
 * on 17/12/2023.
 * github.com/iannjari
 */

@GrpcService
@RequiredArgsConstructor
public class DocumentGrpcServer extends DocumentServiceGrpc.DocumentServiceImplBase {

    private final DocumentService documentService;

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void applyForDocument(ApplyDocumentCmd request, StreamObserver<ApplyDocumentCmd> responseObserver) {
        ApplyDocumentCmd response =  documentService.applyForDocument(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void issueDocument(IssueDocumentCmd request, StreamObserver<IssueDocumentCmd> responseObserver) {
        IssueDocumentCmd response = documentService.issueDocument(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void amendDocument(AmendDocumentCmd request, StreamObserver<AmendDocumentCmd> responseObserver) {
        AmendDocumentCmd response = documentService.updateDocument(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
