package ru.handy.handy.controllers.search;

import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.handy.handy.components.AdvancedLogic;
import ru.handy.handy.components.ExpertLogic;
import ru.handy.handy.components.SharedLogic;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.service.lead_heap.LeadService;
import ru.handy.handy.service.PrincipalUserDetailsService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/searcher")
public class SearchControl {

    private final AdvancedLogic advancedLogic;
    private final LeadService leadService;
    private final SharedLogic sharedLogic;
    private final PrincipalUserDetailsService principalService;
    private final ExpertLogic expertLogic;

    public SearchControl(AdvancedLogic advancedLogic, LeadService leadService, SharedLogic sharedLogic, PrincipalUserDetailsService principalService, ExpertLogic expertLogic) {
        this.advancedLogic = advancedLogic;
        this.leadService = leadService;
        this.sharedLogic = sharedLogic;
        this.principalService = principalService;
        this.expertLogic = expertLogic;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER', 'ROLE_JURIST', 'ROLE_ADVERTISER')")
    @GetMapping("/find/advanced_query")
    public String showAdvancedQueryInfo(@RequestParam("wanted") String wanted,
                                        Model model,
                                        Principal principal,
                                        Device device){

        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

//        model.addAttribute("home", ("/"));
//        model.addAttribute("homeUsers", ("/"));
//        List<LeadEntity> allLeads = leadService.findAllLeads();
//        Map<String, List<LeadEntity>> answer = advancedLogic.findAllAdvancedAppropriateLeadsByQuery(wanted, allLeads);

        Map<String, List<LeadEntity>> answer = leadService.findAllAdvancedAppropriateLeadsByQuery(wanted);
        model.addAttribute("allFilters", answer);

        List<LeadEntity> curentFilter = answer.get(wanted);
        model.addAttribute("allActiveLeads", curentFilter);

        boolean emptyRequestSoGoHome = (curentFilter == null || curentFilter.size() == 0);
        model.addAttribute("ifNothingToShow", emptyRequestSoGoHome);
        model.addAttribute("wanted", wanted);

        sharedLogic.addCommmonHomePageColoredLeadsDigitCheckersAtTheModel(model);

        if (principalEntity.getRole().equalsIgnoreCase("ADMIN")){
            if (device.isMobile())
                return "/mobile/home/m_admin_filters";
            else
                return "/home/admin_filters";
        }
        else{
            if (device.isMobile())
                return "/mobile/home/m_advanced_filters";
            else
                return "/home/advanced_filters";
        }
    }

    @GetMapping("/find/expert_query")
    public String showExpertQueryInfo(@RequestParam("wanted") String wanted,
                                      Principal principal,
                                      Model model){

        PrincipalEntity expert = principalService.findPrincipalEntityByUsername(principal.getName());

        Map<String, List<LeadEntity>> answer = leadService.findAllAppropriateExpertLeadsByQueryAndExpertId(wanted, expert.getId());
        model.addAttribute("allFilters", answer);

        List<LeadEntity> curentFilter = answer.get(wanted);
        model.addAttribute("allActiveLeads", curentFilter);

        boolean emptyRequestSoGoHome = (curentFilter == null || curentFilter.size() == 0);
        model.addAttribute("ifNothingToShow", emptyRequestSoGoHome);
        model.addAttribute("wanted", wanted);

        sharedLogic.addCommmonHomePageColoredLeadsDigitCheckersAtTheModel(model);

        return "/home/expert";
    }

}