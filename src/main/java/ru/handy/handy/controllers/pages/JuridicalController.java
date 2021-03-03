package ru.handy.handy.controllers.pages;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.handy.handy.components.ComponentLinks;
import ru.handy.handy.components.SharedLogic;
import ru.handy.handy.models.lead_heap.JuristInfoEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.service.lead_heap.FotoDocsService;
import ru.handy.handy.service.lead_heap.LeadService;
import ru.handy.handy.service.PrincipalUserDetailsService;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/api/juridical")
public class JuridicalController {

    private final LeadService leadService;
    private final FotoDocsService fotoDocsService;
    private final SharedLogic sharedLogic;
    private final ComponentLinks componentLinks;
    private final PrincipalUserDetailsService principalService;

    public JuridicalController(LeadService leadService, FotoDocsService fotoDocsService, SharedLogic sharedLogic, ComponentLinks componentLinks, PrincipalUserDetailsService principalService) {
        this.leadService = leadService;
        this.fotoDocsService = fotoDocsService;
        this.sharedLogic = sharedLogic;
        this.componentLinks = componentLinks;
        this.principalService = principalService;
    }

    @GetMapping("/{leadId}")
    public String chooseOne(@PathVariable("leadId") String leadId,
                            Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        String role = principalEntity.getRole();

        if (role.equalsIgnoreCase("ADMIN")
                || role.equalsIgnoreCase("JURIST")
                || role.equalsIgnoreCase("SUPERUSER")
                || role.equalsIgnoreCase("ADVERTISER"))
            return "redirect:/api/juridical/advanced/" + leadId;
        else if (role.equalsIgnoreCase("EXPERT"))
            return "redirect:/api/juridical/expert/" + leadId;

        return "redirect:/api/flat/juridical/" + leadId;
    }

    @ModelAttribute
    public void addCommonObjectsToModel(Model model, Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        model.addAttribute("person", principalEntity);
//        model.addAttribute("role", principalEntity.getRole());

        sharedLogic.addLabelLinksToModel(model);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_JURIST', 'ROLE_SUPERUSER', 'ROLE_ADVERTISER')")
    @GetMapping("/advanced/{leadId}")
    public String adminAndJuristAndSuperuserJuridicalPage(@PathVariable("leadId") Long leadId,
                                              Model model){

        LeadEntity lead = leadService.findLeadById(leadId);

        JuristInfoEntity juristInfo = lead.getJuristInfo();
        model.addAttribute("juristInfo", juristInfo);
//        List<DocumentEntity> requiredDocuments = juristInfo.getRequiredDocuments();
//        model.addAttribute("requiredDocuments", requiredDocuments);

        Map<Long, String> allFotoDocs = fotoDocsService.findAllFotoDocsByJuristIdAndPutIntoMap(juristInfo.getId());
        model.addAttribute("allFotos", allFotoDocs);

        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_3_juridical/jurist_admin_super_juridical";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EXPERT')")
    @GetMapping("/expert/{leadId}")
    public String expertJuridicalPage(@PathVariable("leadId") Long leadId,
                                      Principal principal,
                                      Model model){

        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

        LeadEntity lead = principalEntity.getLeads().stream()
                .filter(leadEntity -> leadEntity.getId().equals(leadId))
                .findFirst().orElse(null);

        if (lead == null)
            return "error/outside_lead";

        JuristInfoEntity juristInfo = lead.getJuristInfo();
        model.addAttribute("juristInfo", juristInfo);
//        List<DocumentEntity> requiredDocuments = juristInfo.getRequiredDocuments();
//        model.addAttribute("requiredDocuments", requiredDocuments);

        Map<Long, String> allFotoDocs = fotoDocsService.findAllFotoDocsByJuristIdAndPutIntoMap(juristInfo.getId());
        model.addAttribute("allFotos", allFotoDocs);

        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_3_juridical/expert_juridical";
    }
}
