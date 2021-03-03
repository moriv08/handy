package ru.handy.handy.controllers.pages;

import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.handy.handy.components.ComponentLinks;
import ru.handy.handy.components.AdvancedLogic;
import ru.handy.handy.components.SharedLogic;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.service.lead_heap.FlatFotoService;
import ru.handy.handy.service.lead_heap.FotoDocsService;
import ru.handy.handy.service.lead_heap.LeadService;
import ru.handy.handy.service.PrincipalUserDetailsService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/flat")
public class FlatController {

    private final LeadService leadService;
    private final FlatFotoService flatFotoService;
    private final PrincipalUserDetailsService principalService;

    private final AdvancedLogic advancedLogic;
    private final SharedLogic sharedLogic;
    private final ComponentLinks componentLinks;

    public FlatController(LeadService leadService, FlatFotoService flatFotoService, FotoDocsService fotoDocsService, PrincipalUserDetailsService principalService, AdvancedLogic advancedLogic, SharedLogic sharedLogic, ComponentLinks componentLinks) {
        this.leadService = leadService;
        this.flatFotoService = flatFotoService;
        this.principalService = principalService;
        this.advancedLogic = advancedLogic;
        this.sharedLogic = sharedLogic;
        this.componentLinks = componentLinks;
    }

    @GetMapping("/{leadId}")
    public String chooseOne(@PathVariable("leadId") Long leadId,
                            Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        String role = principalEntity.getRole();

        if (role.equalsIgnoreCase("ADMIN"))
            return "redirect:/api/flat/admin/" + leadId;
        else if (role.equalsIgnoreCase("SUPERUSER"))
            return "redirect:/api/flat/superuser/" + leadId;
        else if (role.equalsIgnoreCase("EXPERT"))
            return "redirect:/api/flat/expert/" + leadId;

        return "redirect:/api/flat/tourism/" + leadId;
    }

    @ModelAttribute
    public void addCommonObjectsToModel(Model model, Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        model.addAttribute("person", principalEntity);

        List<String> leadStatusList = sharedLogic.makeAllLeadStatusList();
        model.addAttribute("leadStatusList", leadStatusList);
        sharedLogic.addLabelLinksToModel(model);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/{leadId}")
    public String adminFlatPage(@PathVariable("leadId") Long leadId,
                                Principal principal,
                                Model model,
                                Device device){

//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        LeadEntity lead = leadService.findLeadById(leadId);

        Map<Long, String> allFlatFotos = flatFotoService.findAllFlatFotosByLeadIdAndPutIntoMap(lead.getId());
        model.addAttribute("allFotos", allFlatFotos);

        sharedLogic.addBookingAndWeakSideObjectsToModel(model, lead);
        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        if (device.isMobile())
            return "mobile/m_pages/m_frames_1_flat/m_admin_flat";
        else
            return "pages/frames_1_flat/admin_flat";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'EXPERT')")
    @GetMapping("/expert/{leadId}")
    public String expertFlatPage(@PathVariable("leadId") Long leadId,
                                 Principal principal,
                                 Model model){

        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        LeadEntity lead = principalEntity.getLeads().stream()
                .filter(leadEntity -> leadEntity.getId().equals(leadId))
                .findFirst().orElse(null);

        if (lead == null)
            return "error/outside_lead";

        Map<Long, String> allFlatFotos = flatFotoService.findAllFlatFotosByLeadIdAndPutIntoMap(lead.getId());
        model.addAttribute("allFotos", allFlatFotos);

        sharedLogic.addBookingAndWeakSideObjectsToModel(model, lead);
        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_1_flat/expert_flat";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'SUPERUSER')")
    @GetMapping("/superuser/{leadId}")
    public String superFlatPage(@PathVariable("leadId") Long leadId,
                                Principal principal,
                                Model model){

//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        LeadEntity lead = leadService.findLeadById(leadId);

        Map<Long, String> allFlatFotos = flatFotoService.findAllFlatFotosByLeadIdAndPutIntoMap(lead.getId());
        model.addAttribute("allFotos", allFlatFotos);

        sharedLogic.addBookingAndWeakSideObjectsToModel(model, lead);
        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_1_flat/super_flat";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'JURIST', 'ADVERTISER')")
    @GetMapping("/tourism/{leadId}")
    public String juristAndAdvertiserFlatPage(@PathVariable("leadId") Long leadId,
                                              Principal principal,
                                              Model model){

//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        LeadEntity lead = leadService.findLeadById(leadId);

        Map<Long, String> allFlatFotos = flatFotoService.findAllFlatFotosByLeadIdAndPutIntoMap(lead.getId());
        model.addAttribute("allFotos", allFlatFotos);

        sharedLogic.addBookingAndWeakSideObjectsToModel(model, lead);
        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_1_flat/super_flat";
    }











//    @GetMapping("/booking/{leadId}")
//    public String getBooking(@PathVariable("leadId") Long leadId,
//                                   Model model){
//
//        LeadEntity lead = leadService.findLeadById(leadId);
//
//        sharedLogic.addBookingPageDigitCheckersAtTheModel(model);
//        sharedLogic.addPageCommonLinksToPersonModel(model, ROLE, lead);
//
//        return "/pages/frames_2_booking/expert-admin-super_booking";
//    }
//
//    @GetMapping("/juridical/{leadId}")
//    public String superuserJuridicalPage(@PathVariable("leadId") Long leadId,
//                                         Model model){
//
//        LeadEntity lead = leadService.findLeadById(leadId);
//
//        sharedLogic.addPageCommonLinksToPersonModel(model, ROLE, lead);
//
//        JuristInfoEntity juristInfo = lead.getJuristInfo();
//        model.addAttribute("juristInfo", juristInfo);
//
//        Map<Long, String> allFotoDocs = fotoDocsService.findAllFotoDocsByJuristIdAndPutIntoMap(juristInfo.getId());
//        model.addAttribute("allFotos", allFotoDocs);
//
//        return "/pages/frames_3_juridical/jurist_admin_super_juridical";
//    }
//
//    @GetMapping("/advertisment/{leadId}")
//    public String expertAdvertisment(@PathVariable("leadId") Long leadId,
//                                     Model model){
//
//        LeadEntity lead = leadService.findLeadById(leadId);
//        sharedLogic.addPageCommonLinksToPersonModel(model, ROLE, lead);
//
//        Map<Long, String> allFlatFotos = flatFotoService.findAllFlatFotosByLeadIdAndPutIntoMap(lead.getId());
//        model.addAttribute("allFotos", allFlatFotos);
//
//        return "/pages/frames_4_sale/expert_admin_advertiser_super_sale";
//    }


    /************************************************   FLAT BLOCK  ****************************************************/
//    @GetMapping("/new_lead")
//    public String makeNewLead(Model model){
//
//        model.addAttribute("role", ROLE);
//
//        List<String> experts = expertService.findAllExpertsNames();
//        model.addAttribute("experts", experts);
////?????????????????????????????????
////        LeadEntity lead = leadService.findLeadById(leadId);
//
////        sharedLogic.addBookingAndWeakSideObjectsToModel(model, lead);
////        sharedLogic.addPageCommonLinksToPersonModel(model, ROLE, lead);
//
//        return "/admin/new_lead";
//    }
}
