package ru.handy.handy.components;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Statuses;
import ru.handy.handy.service.lead_heap.LeadService;

import java.util.List;
import java.util.Map;

//@Controller
@Component
public class AdvancedLogic {

    private final SharedLogic sharedLogic;
    private final LeadService leadService;

    public AdvancedLogic(SharedLogic sharedLogic, LeadService leadService) {
        this.sharedLogic = sharedLogic;
        this.leadService = leadService;
    }


    public synchronized void addHomeCommonLinksToSuperuserModel(Model model,
                                                                Long id,
                                                                Map<String, List<LeadEntity>> allFilters,
                                                                String filter){

        model.addAttribute("allFilters", allFilters);

        List<LeadEntity> activeLeads = allFilters.get(filter);
        model.addAttribute("allActiveLeads", activeLeads);

        boolean isEmptyAnswer = (activeLeads == null || (activeLeads.size() == 0));
        model.addAttribute("ifNothingToShow", isEmptyAnswer);

        model.addAttribute("requiredDocs", leadService.findAllRequiredDocsBySuperuserId(id));
        model.addAttribute("requiredDocsCounter", leadService.findAllRequiredDocsCounterBySuperuserId(id));
        model.addAttribute("endingBookings", leadService.findAllSuperuserEndingBookings(id));
        model.addAttribute("yesterdayCalls", leadService.findAllSuperuserYesterdayCallLeadsBySuperuserId(id));
        model.addAttribute("todayCalls", leadService.findAllSuperuserTodayCallLeadsBySuperuserId(id));

        model.addAttribute("docsChecking", Statuses.DOC_WAITING.digit);

        model.addAttribute("hilightedFilter", filter);

    }

    public synchronized void addHomeCommonLinksToPersonModel(Model model,
                                                             Map<String, List<LeadEntity>> allFilters,
                                                             String filter){

        model.addAttribute("allFilters", allFilters);

        List<LeadEntity> activeLeads = allFilters.get(filter);
        model.addAttribute("allActiveLeads", activeLeads);

        boolean isEmptyAnswer = (activeLeads == null || (activeLeads.size() == 0));
        model.addAttribute("ifNothingToShow", isEmptyAnswer);

        model.addAttribute("requiredDocs", leadService.findAllByRequiredDocs());
        model.addAttribute("requiredDocsCounter", leadService.findAllByRequiredDocsCounter());
        model.addAttribute("endingBookings", leadService.findAllEndingBookings());
        model.addAttribute("yesterdayCalls", leadService.findAllByYesterdayCall());
        model.addAttribute("todayCalls", leadService.findAllByTodayCall());

        model.addAttribute("docsChecking", Statuses.DOC_WAITING.digit);

        model.addAttribute("hilightedFilter", filter);
    }


    public synchronized void addJuristAndAdvertiserHomeCommonLinksToPersonModel(Model model,
                                                                                Map<String, List<LeadEntity>> allFilters,
                                                                                String filter){

        model.addAttribute("allFilters", allFilters);

        List<LeadEntity> activeLeads = allFilters.get(filter);
        model.addAttribute("allActiveLeads", activeLeads);

        boolean isEmptyAnswer = (activeLeads == null || (activeLeads.size() == 0));
        model.addAttribute("ifNothingToShow", isEmptyAnswer);

        model.addAttribute("requiredDocs", leadService.findAllByRequiredDocs());
        model.addAttribute("requiredDocsCounter", leadService.findAllByRequiredDocsCounter());
        model.addAttribute("endingBookings", leadService.findAllEndingBookings());

        model.addAttribute("docsChecking", Statuses.DOC_WAITING.digit);

        model.addAttribute("hilightedFilter", filter);

    }






//    public synchronized Map<String, List<LeadEntity>> findAllAdvancedAppropriateLeadsByQuery(String wanted, List<LeadEntity> allLeads){
//
//        Map<String, List<LeadEntity>> answer = new HashMap<>();
//        List<LeadEntity> leadsList = new ArrayList<>();
//
//        if (sharedLogic.findWhatItIs(wanted) == 1){
//
//            Long wantedLeadId = Long.parseLong(wanted);
//
//            allLeads.stream()
//                    .filter(elem -> elem.getId().equals(wantedLeadId))
//                    .findFirst().ifPresent(leadsList::add);
//
//        }else if(sharedLogic.findWhatItIs(wanted) == 2){
//
//            allLeads.stream()
//                    .filter(elem -> elem.getTel().contains(wanted))
//                    .forEach(leadsList::add);
//
//        }else if(sharedLogic.findWhatItIs(wanted) == 3){
//
//            allLeads.stream()
//                    .filter(elem -> elem.getName() != null)
//                    .filter(elem -> elem.getName().toLowerCase().contains(wanted.toLowerCase()))
//                    .forEach(leadsList::add);
//
//            allLeads.stream()
//                    .filter(elem -> elem.getAddress() != null)
//                    .filter(elem -> elem.getAddress().toLowerCase().contains(wanted.toLowerCase()))
//                    .forEach(leadsList::add);
//        }
//
//        answer.put(wanted, leadsList.stream()
//                .sorted().collect(Collectors.toList())
//        );
//
//        return answer;
//    }
}
