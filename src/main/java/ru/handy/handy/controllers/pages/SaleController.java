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
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.service.lead_heap.FlatFotoService;
import ru.handy.handy.service.lead_heap.FotoDocsService;
import ru.handy.handy.service.lead_heap.LeadService;
import ru.handy.handy.service.PrincipalUserDetailsService;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/api/advertisment")
public class SaleController {

    private final LeadService leadService;
    private final FotoDocsService fotoDocsService;
    private final SharedLogic sharedLogic;
    private final ComponentLinks componentLinks;
    private final PrincipalUserDetailsService principalService;
    private final FlatFotoService flatFotoService;

    public SaleController(LeadService leadService, FotoDocsService fotoDocsService, SharedLogic sharedLogic, ComponentLinks componentLinks, PrincipalUserDetailsService principalService, FlatFotoService flatFotoService) {
        this.leadService = leadService;
        this.fotoDocsService = fotoDocsService;
        this.sharedLogic = sharedLogic;
        this.componentLinks = componentLinks;
        this.principalService = principalService;
        this.flatFotoService = flatFotoService;
    }

//    @GetMapping("/{leadId}")
//    public String chooseOne(@PathVariable("leadId") String leadId,
//                            Principal principal){
//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
//        String role = principalEntity.getRole();
//
//        if (role.equalsIgnoreCase("ADMIN")
//                || role.equalsIgnoreCase("JURIST")
//                || role.equalsIgnoreCase("SUPERUSER"))
//            return "redirect:/api/juridical/advanced/" + leadId;
//        else if (role.equalsIgnoreCase("EXPERT"))
//            return "redirect:/api/juridical/expert/" + leadId;
//
//        return "redirect:/api/flat/sale/" + leadId;
//    }

    @ModelAttribute
    public void addCommonObjectsToModel(Model model, Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        model.addAttribute("person", principalEntity);
//        model.addAttribute("role", principalEntity.getRole());

        sharedLogic.addLabelLinksToModel(model);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ADVERTISER', 'EXPERT', 'SUPERUSER', 'ROLE_JURIST', 'ROLE_SUPERUSER')")
    @GetMapping("/{leadId}")
    public String adminAndJuristJuridicalPage(@PathVariable("leadId") Long leadId,
                                              Model model){

        LeadEntity lead = leadService.findLeadById(leadId);
        model.addAttribute("lead", lead);

        Map<Long, String> allFlatFotos = flatFotoService.findAllFlatFotosByLeadIdAndPutIntoMap(lead.getId());
        model.addAttribute("allFotos", allFlatFotos);

        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_4_sale/expert_admin_advertiser_super_sale";
    }
}
