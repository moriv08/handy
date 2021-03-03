package ru.handy.handy.controllers.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Labels;
import ru.handy.handy.service.PrincipalUserDetailsService;
import ru.handy.handy.service.lead_heap.LeadService;
import ru.handy.handy.service.service.StatisticService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/test")
public class TestController {

    private final LeadService leadService;
    private final PrincipalUserDetailsService principalService;
    private final StatisticService statisticService;

    public TestController(LeadService leadService, PrincipalUserDetailsService principalService, StatisticService statisticService) {
        this.leadService = leadService;
        this.principalService = principalService;
        this.statisticService = statisticService;
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER', 'ROLE_EXPERT')")
//    @GetMapping("/{expert}")
//    public String statistics(@PathVariable(name = "expert") String expert,
//                             Model model,
//                             Principal principal){
//
//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
//
//        Map<String, List<Integer>> allStatistics = statisticService.findCurrentYearStatistic();
//        Map<String, List<Integer>> expertStatistic = statisticService.findCurrentYearStatisticByPrincipalId(principalEntity.getId());
//
//        model.addAttribute("statistics", allStatistics);
//        model.addAttribute("expert", expertStatistic);
//
//        return "/admin/statistic";
//    }

    @GetMapping("/one/{start}/{end}")
    public String mobile(Model model, Principal principal,
                         @PathVariable(name = "start") Integer start,
                         @PathVariable(name = "end") Integer end) {

        System.out.println("?????????????????????????????????????????????" +
                "????????????????????????????????????????????????" +
                "??????????????????????????????????????????????????????? " + start);
        System.out.println("?????????????????????????????????????????????" +
                "????????????????????????????????????????????????" +
                "??????????????????????????????????????????????????????? " + end);

        model.addAttribute("start", start);
        model.addAttribute("end", end);

        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
//        if (filter.equals("start"))
//            filter = Statuses.NEW_LEAD.status;
//
        Map<String, List<LeadEntity>> allFilters = leadService.TEST_MAP(start, end);
        model.addAttribute("allLeads", allFilters.get(Labels.YOUR_NEW_LEAD.title));
        model.addAttribute("allFilters", allFilters);

//        // necessary! add home objects
//        expertLogic.addAllExpertHomeVariablesAtTheModel(model, principalEntity.getRole(), filter, allFilters);
//        // necessary! add digit checkers
//        sharedLogic.addHomePageColoredLeadsDigitCheckersAtTheModel(model);

        return "/test";
    }


//    @GetMapping("/home/{filter}")
//    public String expertHomeCurrentFilter(@PathVariable("filter") String filter,
//                                          Model model,
//                                          Principal principal) {
//
//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());
//        if (filter.equals("start"))
//            filter = Statuses.NEW_LEAD.status;
//
//        Map<String, List<LeadEntity>> allFilters = leadService.findAPrincipalFiltersById(principalEntity.getId());
//        // necessary! add home objects
//        expertLogic.addAllExpertHomeVariablesAtTheModel(model, principalEntity.getRole(), filter, allFilters);
//        // necessary! add digit checkers
//        sharedLogic.addHomePageColoredLeadsDigitCheckersAtTheModel(model);
//
//        return "/home/home_expert";
//    }
    /****************************************   FLAT PAGE    ***********************************************/
//    @GetMapping("/flat/{leadId}")
//    public String expertFlat(@PathVariable("leadId") Long leadId,
//                             Principal principal,
//                             Model model){
//
//        ExpertEntity expert = expertService.findExpertByPrincipalName(principal.getName());
//        LeadEntity lead = expert.getLeads().stream()
//                .filter(leadEntity -> leadEntity.getId().equals(leadId))
//                .findFirst().orElse(null);
//        if (lead == null)
//            return "error/outside_appl";
//
//        Map<Long, String> allFlatFotos = expertLogic.findAllFlatFotosByLeadAndPutIntoMap(lead);
//        model.addAttribute("allFotos", allFlatFotos);
//
//        // necessary! lead statuses!
//        List<String> leadStatusList = sharedLogic.makeAllLeadStatusList();
//        model.addAttribute("leadStatusList", leadStatusList);
//
//        sharedLogic.addBookingAndWeakSideObjectsToModel(model, lead);
//        sharedLogic.addPageCommonLinksToPersonModel(model, ROLE, lead);
//
//        return "/pages/frames_1_flat/expert_flat";
////        return "/pages/frames_1_flat/super_flat";
//    }

//    @GetMapping("/booking/{leadId}")
//    public String expertWorkingAndBooking(@PathVariable("leadId") Long leadId,
//                                          Principal principal,
//                                          Model model){
//
//        ExpertEntity expert = expertService.findExpertByPrincipalName(principal.getName());
//        LeadEntity lead = expert.getLeads().stream()
//                .filter(l -> l.getId().equals(leadId))
//                .findFirst().orElse(null);
//        if (lead == null)
//            return "error/outside_appl";
//
//        sharedLogic.addBookingPageDigitCheckersAtTheModel(model);
//        sharedLogic.addPageCommonLinksToPersonModel(model, ROLE, lead);
//
//        return "/pages/frames_2_booking/expert-admin-super_booking";
//    }
//
//    @GetMapping("/juridical/{leadId}")
//    public String expertJuridicalPage(@PathVariable("leadId") Long leadId,
//                                      Principal principal,
//                                      Model model){
//
//        ExpertEntity expert = expertService.findExpertByPrincipalName(principal.getName());
//        LeadEntity lead = expert.getLeads().stream()
//                .filter(l -> l.getId().equals(leadId))
//                .findFirst().orElse(null);
//        if (lead == null)
//            return "error/outside_appl";
//
//        sharedLogic.addPageCommonLinksToPersonModel(model, ROLE, lead);
//
//        JuristInfoEntity juristInfo = lead.getJuristInfo();
//        model.addAttribute("juristInfo", juristInfo);
//
//
//        Map<Long, String> allFotoDocs = fotoDocsService.findAllFotoDocsByJuristIdAndPutIntoMap(juristInfo.getId());
//        model.addAttribute("allFotos", allFotoDocs);
//
//        return "/pages/frames_3_juridical/new_expert_juridical";
//    }
//
//    @GetMapping("/advertisment/{leadId}")
//    public String expertAdvertisment(@PathVariable("leadId") Long leadId,
//                                     Principal principal,
//                                     Model model){
//
//        ExpertEntity expert = expertService.findExpertByPrincipalName(principal.getName());
//        LeadEntity lead = expert.getLeads().stream()
//                .filter(l -> l.getId().equals(leadId))
//                .findFirst().orElse(null);
//        if (lead == null)
//            return "error/outside_appl";
//
//        JuristInfoEntity juristInfo = lead.getJuristInfo();
//        model.addAttribute("juristInfo", juristInfo);
//
//        List<DocumentEntity> requiredDocuments = juristInfo.getRequiredDocuments();
//        model.addAttribute("requiredDocuments", requiredDocuments);
//
//        sharedLogic.addPageCommonLinksToPersonModel(model, ROLE, lead);
//
//        Map<Long, String> allFlatFotos = expertLogic.findAllFlatFotosByLeadAndPutIntoMap(lead);
//        model.addAttribute("allFotos", allFlatFotos);
//
//        return "/pages/frames_4_sale/expert_admin_advertiser_super_sale";
//    }
}
