package ru.handy.handy.controllers.home;

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
import ru.handy.handy.components.ExpertLogic;
import ru.handy.handy.components.SharedLogic;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Labels;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.service.lead_heap.FlatFotoService;
import ru.handy.handy.service.lead_heap.FotoDocsService;
import ru.handy.handy.service.lead_heap.LeadService;
import ru.handy.handy.service.PrincipalUserDetailsService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/home")
public class HomeController {

    private final LeadService leadService;
    private final PrincipalUserDetailsService principalService;

    private final AdvancedLogic advancedLogic;
    private final SharedLogic sharedLogic;
    private final ExpertLogic expertLogic;

    public HomeController(LeadService leadService, PrincipalUserDetailsService principalService, AdvancedLogic advancedLogic, SharedLogic sharedLogic, ExpertLogic expertLogic) {
        this.leadService = leadService;
        this.principalService = principalService;
        this.advancedLogic = advancedLogic;
        this.sharedLogic = sharedLogic;
        this.expertLogic = expertLogic;
    }

    @GetMapping("")
    public String chooseHomeFiltersFilter(Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        String role = principalEntity.getRole().toLowerCase();

        if (role.equalsIgnoreCase("ADMIN"))
            return "redirect:/api/home/admin/filters/start";
        else if (role.equalsIgnoreCase("SUPERUSER"))
            return "redirect:/api/home/superuser/filters/start";
        else if (role.equalsIgnoreCase("EXPERT"))
            return "redirect:/api/home/expert/start";

        return "redirect:/api/home/advanced/filters/start";
    }

    @GetMapping("/home_expert")
    public String chooseHomeExpertsFilter(Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        String role = principalEntity.getRole().toLowerCase();

        if (role.equalsIgnoreCase("ADMIN"))
            return "redirect:/api/home/admin/experts/one";
        else if (role.equalsIgnoreCase("SUPERUSER"))
            return "redirect:/api/home/superuser/experts/one";

        return "redirect:/api/home/advanced/experts/one";
    }

    @ModelAttribute
    public void addCommonObjectsToModel(Model model, Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

        model.addAttribute("person", principalEntity);

        if (!principalEntity.getRole().equalsIgnoreCase("EXPERT")){
            model.addAttribute("toFiltersPath", "/api/home/advanced/filters");
            model.addAttribute("toExpertsPath", "/api/home/advanced/experts");
        }

        sharedLogic.addCommmonHomePageColoredLeadsDigitCheckersAtTheModel(model);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/filters/{filter}")
    public String adminHomeFilters(@PathVariable("filter") String filter,
                                   Model model,
                                   Device device){

        if (filter.equals("start"))
            filter = Labels.ALL_ACTIVE_LEADS.title;

        Map<String, List<LeadEntity>> allFilterFilters = leadService.findAllFilters();
        advancedLogic.addHomeCommonLinksToPersonModel(model, allFilterFilters, filter);

        if (device.isMobile())
            return "/mobile/home/m_admin_filters";
        else
            return "home/admin_filters";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/experts/{expert}")
    public String adminHomeExperts(@PathVariable("expert") String expert,
                                   Model model,
                                   Device device){

        Map<String, List<LeadEntity>> allExpertsFilters = leadService.findAllOfExpertFilters();
        advancedLogic.addHomeCommonLinksToPersonModel(model, allExpertsFilters, expert);

        if (device.isMobile())
            return "/mobile/home/m_admin_experts";
        else
            return "home/admin_experts";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
    @GetMapping("/superuser/filters/{filter}")
    public String superuserHomeFilters(@PathVariable("filter") String filter,
                                       Principal principal,
                                       Model model){

        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

        if (filter.equals("start"))
            filter = Labels.YOUR_ACTIVE_LEADS.title;

        Map<String, List<LeadEntity>> allFilterFilters = leadService.findAllSuperuserFiltersById(principalEntity.getId());
        advancedLogic.addHomeCommonLinksToSuperuserModel(model, principalEntity.getId(), allFilterFilters, filter);

        return "home/superuser_filters";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
    @GetMapping("/superuser/experts/{expert}")
    public String superuserHomeExperts(@PathVariable("expert") String expert,
                                       Principal principal,
                                       Model model){

        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

        Map<String, List<LeadEntity>> allExpertsFilters = leadService.findAllOfExpertFilters();
        advancedLogic.addHomeCommonLinksToSuperuserModel(model, principalEntity.getId(), allExpertsFilters, expert);

        return "/home/superuser_experts";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER', 'ROLE_JURIST', 'ROLE_ADVERTISER')")
    @GetMapping("/advanced/filters/{filter}")
    public String advancedHomeFilters(@PathVariable("filter") String filter,
                                      Model model){

        if (filter.equals("start"))
            filter = Labels.ALL_ACTIVE_LEADS.title;

        Map<String, List<LeadEntity>> allFilterFilters = leadService.findAllFilters();
        advancedLogic.addJuristAndAdvertiserHomeCommonLinksToPersonModel(model, allFilterFilters, filter);

        return "home/advanced_filters";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER', 'ROLE_JURIST', 'ROLE_ADVERTISER')")
    @GetMapping("/advanced/experts/{expert}")
    public String advancedHomeExperts(@PathVariable("expert") String expert,
                                      Model model){

        Map<String, List<LeadEntity>> allExpertsFilters = leadService.findAllOfExpertFilters();
        advancedLogic.addJuristAndAdvertiserHomeCommonLinksToPersonModel(model, allExpertsFilters, expert);

        return "home/advanced_experts";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER', 'ROLE_EXPERT')")
    @GetMapping("/expert/{filter}")
    public String expertHomeCurrentFilter(@PathVariable("filter") String filter,
                                          Model model,
                                          Principal principal) {

        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
        if (filter.equals("start"))
            filter = Labels.YOUR_ACTIVE_LEADS.title;

        Map<String, List<LeadEntity>> allFilters = leadService.findAllExpertFiltersById(principalEntity.getId());
        expertLogic.addAllExpertHomeVariablesAtTheModel(model, principalEntity.getId(), filter, allFilters);
        sharedLogic.addCommmonHomePageColoredLeadsDigitCheckersAtTheModel(model);

        return "home/expert";
    }
}
