package ru.handy.handy.controllers.update;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.handy.handy.components.SharedLogic;
import ru.handy.handy.models.lead_heap.DocumentEntity;
import ru.handy.handy.models.lead_heap.FotoDocsEntity;
import ru.handy.handy.models.lead_heap.JuristInfoEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Statuses;
import ru.handy.handy.service.lead_heap.DocumentService;
import ru.handy.handy.service.lead_heap.FotoDocsService;
import ru.handy.handy.service.lead_heap.JuristInfoService;
import ru.handy.handy.service.lead_heap.LeadService;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/api/update/juridical")
public class UpdatableJuridicalController {

    private final JuristInfoService juristInfoService;
    private final LeadService leadService;
    private final FotoDocsService fotoDocsService;
    private final DocumentService documentService;
    private final SharedLogic sharedLogic;

    public UpdatableJuridicalController(JuristInfoService juristInfoService, LeadService leadService, FotoDocsService fotoDocsService, DocumentService documentService, SharedLogic sharedLogic) {
        this.juristInfoService = juristInfoService;
        this.leadService = leadService;
        this.fotoDocsService = fotoDocsService;
        this.documentService = documentService;
        this.sharedLogic = sharedLogic;
    }


    @PostMapping("/request_docs")
    public String updateRequestDocuments(@RequestParam("leadId") Long leadId,

                                         @RequestParam(value = "required_documents", required = false) String required_documents){

        LeadEntity lead = leadService.findLeadById(leadId);

        if (required_documents.trim().length() > 0){
            String[] arrayDocuments = required_documents.split(";");

            documentService.makeNewDocumentsListForJuristInfoByLead(lead, arrayDocuments);
            leadService.setLeadStatus(leadId, Statuses.DOC_OK.status, Statuses.DOC_OK.digit);
        }

        return  "redirect:/api/juridical/" + leadId;
    }


    /**************** documents checkboxes end request documents  *****************/
    @PostMapping("/existing_docs")
    public String updateCheckboxDocuments(@RequestParam("leadId") Long leadId,

                                          @RequestParam(value = "existingDocs", required = false) String[] existingDocs,
                                          @RequestParam(value = "deletDocs", required = false) String[] deletDocs){

        if (existingDocs != null)
            for (String s: existingDocs) {

                Long docId = Long.parseLong(s);
                DocumentEntity document = documentService.findDocumentById(docId);

                if(document != null){
                    Boolean curentExisting = !(document.getDocExisting());
                    documentService.setNewDocumentExistingInfo(docId, curentExisting);
                }
            }

        if (deletDocs != null)
            for (String s: deletDocs) {

                Long docId = Long.parseLong(s);
                DocumentEntity document = documentService.findDocumentById(docId);

                if(document != null){
                    documentService.deleteDocumentById(docId);
                }
            }

        return  "redirect:/api/juridical/" + leadId;
    }


    @PostMapping(value = "/delete_docs")
    public String deleteDocs(@RequestParam(value = "fotoId", required = false) String[] allFotoId,
                             @RequestParam(value = "leadId") Long leadId){

        if (allFotoId != null){
            for (String foto: allFotoId) {
                Long fotoId;
                try{
                    fotoId = Long.parseLong(foto);
                }catch (Exception e){
                    return "test/error";
                }
                fotoDocsService.deleteFotoDocById(fotoId);
            }
        }
        return  "redirect:/api/juridical/" + leadId;
    }

    @PostMapping("/upload_docs")
    public String saveDocs(@RequestParam(value = "leadId") Long leadId,
                           @RequestParam(value = "foto_files", required = false) MultipartFile[] fotoDocs){

        LeadEntity lead = leadService.findLeadById(leadId);

        leadService.setLeadStatus(leadId, Statuses.DOC_WAITING.status, Statuses.DOC_WAITING.digit);
        for (MultipartFile file : fotoDocs) {

            JuristInfoEntity juristinfo = lead.getJuristInfo();

            if(juristinfo == null)
                return "error/db_error";

            FotoDocsEntity newFotoDocs = new FotoDocsEntity();
            newFotoDocs.setJuristInfo(juristinfo);

            try {

                newFotoDocs.setFotoDoc(file.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
                return "error/error";//??????????????????????????????
            }

            fotoDocsService.saveFotoDoc(newFotoDocs);
        }
        return "redirect:/api/juridical/" + leadId;
    }

    /**************** add juristic contract info   *****************/
    @PostMapping("/main_contract")
    public String updateContract(@RequestParam("leadId") Long leadId,

                                 @RequestParam(value = "contractType", required = false) String contractType,
                                 @RequestParam(value = "contractStart", required = false) String contractStart,
                                 @RequestParam(value = "contractEnd", required = false) String contractEnd){

        LeadEntity lead = leadService.findLeadById(leadId);

        LocalDate contractLocalStart = null;
        LocalDate contractLocalEnd = null;

        try{

            if (contractStart.length() == 10){
                contractLocalStart = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(contractStart));
                leadService.setLeadStatus(leadId, Statuses.DOC_SIGNED.status, Statuses.DOC_SIGNED.digit);

            }

            if (contractEnd.length() == 10)
                contractLocalEnd = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(contractEnd));

        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return "error/runtime"; //?????????????????????????????????????
        } catch (Exception e){
            e.printStackTrace();
            return "error/error"; //?????????????????????????????????????
        }


        if (contractType.length() > 0)
            juristInfoService.setJuristOpinionContractType(lead, contractType);

        if (contractLocalStart != null)
            juristInfoService.setJuristOpinionContractStartDate(lead, contractLocalStart);

        if (contractLocalEnd != null)
            juristInfoService.setJuristicOpinionContractEndDate(lead, contractLocalEnd);

        return  "redirect:/api/juridical/" + leadId;
    }

    /**************** add juridical prepayment contract info   *****************/
    @PostMapping("/pretayment_contract")
    public String updatePrepayment(@RequestParam("leadId") Long leadId,

                                   @RequestParam(value = "prepaymentType", required = false) String prepaymentType,
                                   @RequestParam(value = "prepaymentStart", required = false) String prepaymentStart,
                                   @RequestParam(value = "prepaymentEnd", required = false) String prepaymentEnd){

        LeadEntity lead = leadService.findLeadById(leadId);

        LocalDate prepaymentLocalStart = null;
        LocalDate prepaymentLocalEnd = null;

        try{

            if (prepaymentStart.length() == 10){
                prepaymentLocalStart = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(prepaymentStart));
                leadService.setLeadStatus(leadId, Statuses.GOT_PREPAYMENT.status, Statuses.GOT_PREPAYMENT.digit);

            }

            if (prepaymentEnd.length() == 10)
                prepaymentLocalEnd = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(prepaymentEnd));

        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return "errors/runtime"; //?????????????????????????????????????
        } catch (Exception e){
            e.printStackTrace();
            return "errors/error"; //?????????????????????????????????????
        }

        if (prepaymentType.length() > 0)
            juristInfoService.setJuristOpinionPrepaymentType(lead, prepaymentType);

        if (prepaymentLocalStart != null)
            juristInfoService.setJuristOpinionPrepaymentStart(lead, prepaymentLocalStart);

        if (prepaymentLocalEnd != null)
            juristInfoService.setJuristOpinionPrepaymentEnd(lead, prepaymentLocalEnd);

        return  "redirect:/api/juridical/" + leadId;
    }

    @PostMapping("/ownership")
    public String updateOwnersDesctiption(@RequestParam("leadId") Long leadId,

                                          @RequestParam(value = "basis", required = false) String basis,
                                          @RequestParam(value = "owners", required = false) String owners,
                                          @RequestParam(value = "bankCargo", required = false) String bankCargo,
                                          @RequestParam(value = "contractType", required = false) String contractType){

        LeadEntity lead = leadService.findLeadById(leadId);

        if (basis.length() > 0)
            juristInfoService.setJuristOpinionPropertyBasis(lead, basis);

        if (owners.length() > 0)
            juristInfoService.setJuristOpinionOwners(lead, owners);

        if (bankCargo.length() > 0)
            juristInfoService.setJuristOpinionBankCargo(lead, bankCargo);

        if (contractType.length() > 0)
            juristInfoService.setJuristOpinionContractType(lead, contractType);

        return  "redirect:/api/juridical/" + leadId;
    }
}
