package ru.handy.handy.controllers.pages;

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

@Controller
@RequestMapping("/api/booking")
public class BookingController {

    private final LeadService leadService;
    private final FlatFotoService flatFotoService;
    private final FotoDocsService fotoDocsService;
    private final PrincipalUserDetailsService principalService;

    private final AdvancedLogic advancedLogic;
    private final SharedLogic sharedLogic;
    private final ComponentLinks componentLinks;

    public BookingController(LeadService leadService, FlatFotoService flatFotoService, FotoDocsService fotoDocsService, PrincipalUserDetailsService principalService, AdvancedLogic advancedLogic, SharedLogic sharedLogic, ComponentLinks componentLinks) {
        this.leadService = leadService;
        this.flatFotoService = flatFotoService;
        this.fotoDocsService = fotoDocsService;
        this.principalService = principalService;
        this.advancedLogic = advancedLogic;
        this.sharedLogic = sharedLogic;
        this.componentLinks = componentLinks;
    }

    @GetMapping("/{leadId}")
    public String chooseBookingTheOne(@PathVariable("leadId") Long leadId,
                                      Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        String role = principalEntity.getRole();

        if (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("SUPERUSER"))
            return "redirect:/api/booking/advanced/" + leadId;
        else if (role.equalsIgnoreCase("EXPERT"))
            return "redirect:/api/booking/expert/" + leadId;

        return "redirect:/api/booking/tourism/" + leadId;
    }

    @ModelAttribute
    public void addCommonObjectsToModel(Model model, Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        model.addAttribute("person", principalEntity);
//        model.addAttribute("role", principalEntity.getRole());

        sharedLogic.addLabelLinksToModel(model);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
    @GetMapping("/advanced/{leadId}")
    public String adminAndSuperuserBookingPage(@PathVariable("leadId") Long leadId,
                                               Principal principal,
                                               Model model){

//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        LeadEntity lead = leadService.findLeadById(leadId);
        model.addAttribute("lead", lead);

        sharedLogic.addBookingPageDigitCheckersAtTheModel(model);
        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_2_booking/expert-admin-super_booking";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EXPERT')")
    @GetMapping("/expert/{leadId}")
    public String adminAndExpertBookingPage(@PathVariable("leadId") Long leadId,
                                            Principal principal,
                                            Model model){

        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        LeadEntity lead = principalEntity.getLeads().stream()
                .filter(leadEntity -> leadEntity.getId().equals(leadId))
                .findFirst().orElse(null);

        if (lead == null)
            return "error/outside_lead";

        model.addAttribute("lead", lead);
        sharedLogic.addBookingPageDigitCheckersAtTheModel(model);
        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_2_booking/expert-admin-super_booking";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'JURIST', 'ADVERTISER')")
    @GetMapping("/tourism/{leadId}")
    public String juristAndAdvertiserBookingPage(@PathVariable("leadId") Long leadId,
                                                 Principal principal,
                                                 Model model){

//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

        LeadEntity lead = leadService.findLeadById(leadId);
        model.addAttribute("lead", lead);

        sharedLogic.addBookingPageDigitCheckersAtTheModel(model);
        sharedLogic.addPageCommonLinksToPersonModel(model, lead);

        return "pages/frames_2_booking/expert-admin-super_booking";
    }

}
