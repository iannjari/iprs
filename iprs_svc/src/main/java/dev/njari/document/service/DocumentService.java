package dev.njari.document.service;

import dev.njari.document.repository.DocumentRepository;
import iprs.document.v1.AmendDocumentCmd;
import iprs.document.v1.ApplyDocumentCmd;
import iprs.document.v1.Document;
import iprs.document.v1.IssueDocumentCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author njari_mathenge
 * on 13/01/2024.
 * github.com/iannjari
 */

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepo;


    public ApplyDocumentCmd applyForDocument(ApplyDocumentCmd cmd) {
        validate(cmd);

        Document document = documentRepo.save(cmd.getDocumentTemplate());

        return ApplyDocumentCmd.newBuilder()
                .setDocumentTemplate(document)
                .build();

    }

    public IssueDocumentCmd issueDocument(IssueDocumentCmd cmd) {

        // validate
        validate(cmd);
        // update and save
        Document document = documentRepo.findById(cmd.getId());

        if (Objects.isNull(document)) throw new RuntimeException("Document with id: "
                .concat(cmd.getId()).concat(" not found"));

        //TODO: add issue logic here

        documentRepo.save(document);

        return cmd;

    }

    public AmendDocumentCmd updateDocument(AmendDocumentCmd cmd) {
        // validate
        validate(cmd);
        // update and save
        Document document = documentRepo.findById(cmd.getId());

        if (Objects.isNull(document)) throw new RuntimeException("Document with id: "
                .concat(cmd.getId()).concat(" not found"));

        //TODO: add amend logic here

        documentRepo.save(document);

        return cmd;

    }

    private void validate(ApplyDocumentCmd cmd) {

    }

    private void validate(IssueDocumentCmd cmd) {

    }

    private void validate(AmendDocumentCmd cmd) {

    }

}
