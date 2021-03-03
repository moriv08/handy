package ru.handy.handy.service.lead_heap;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.handy.handy.models.lead_heap.DocumentEntity;
import ru.handy.handy.models.lead_heap.JuristInfoEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.repository.lead_heap.DocumentRepository;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Transactional
    @ReadOnlyProperty
    public DocumentEntity findDocumentById(Long id){
        return documentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteDocumentById(Long id){
        documentRepository.deleteById(id);
    }

    @Transactional
    public void makeNewDocumentsListForJuristInfoByLead(LeadEntity lead, String[] requiredDocs){

        JuristInfoEntity juristInfo = lead.getJuristInfo();

        for (String doc: requiredDocs) {

            DocumentEntity newDocument = new DocumentEntity();
            newDocument.setJuristInfo(juristInfo);
            newDocument.setDoc(doc);
            newDocument.setDocExisting(false);
            documentRepository.save(newDocument);
        }

    }

    @Transactional
    public void setNewDocumentExistingInfo(Long docId, Boolean existing){
        documentRepository.setNewDocumentExistingInfo(docId, existing);
    }
}
