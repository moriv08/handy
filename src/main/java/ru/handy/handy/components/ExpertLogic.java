package ru.handy.handy.components;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Labels;
import ru.handy.handy.service.lead_heap.LeadService;

import java.util.*;

@Component
public class ExpertLogic {
    private final SharedLogic sharedLogic;
    private final LeadService leadService;

    public ExpertLogic(SharedLogic sharedLogic, LeadService leadService) {
        this.sharedLogic = sharedLogic;
        this.leadService = leadService;
    }

    public synchronized void addAllExpertHomeVariablesAtTheModel(Model model,
                                                                 Long id,
                                                                 String filter,
                                                                 Map<String, List<LeadEntity>> allFilters){

        int requiredDocsCounter = leadService.findAllRequiredDocsCounterByExpertId(id);

        model.addAttribute("requiredDocsCounter", requiredDocsCounter);
        model.addAttribute("docLessLeads", allFilters.get(Labels.REQUIRED_DOCS.title));
        model.addAttribute("endingBookings", allFilters.get(Labels.ENDING_BOOKING.title));
        model.addAttribute("yesterdayCalls", allFilters.get(Labels.YESTERDAY_CALL.title));
        model.addAttribute("todayCalls", allFilters.get(Labels.TODAY_CALL.title));

        model.addAttribute("docLessLeadsLabel", Labels.REQUIRED_DOCS.title);
        model.addAttribute("endingBookingsLabel", Labels.ENDING_BOOKING.title);
        model.addAttribute("yestodayCallsLabel", Labels.YESTERDAY_CALL.title);
        model.addAttribute("todayCallsLabel", Labels.TODAY_CALL.title);

        model.addAttribute("allFilters", allFilters);

        List<LeadEntity> curentFilter = allFilters.get(filter);
        model.addAttribute("allActiveLeads", curentFilter);

        boolean ifNothingToShow = (curentFilter == null || curentFilter.size() == 0);
        model.addAttribute("ifNothingToShow", ifNothingToShow);

        model.addAttribute("hilightedFilter", filter);

//        sharedLogic.addLeadStatusFeaturesAtTheModel(model);

    }
//    public void addPageCommonLinksToPersonModel(Model model, String role, LeadEntity lead){
//
//        model.addAttribute("home", ("/api/" + role + "/home/start"));
//
//        model.addAttribute("flat_path", "/api/" + role + "/flat/" + lead.getId());
//        model.addAttribute("booking_path", "/api/" + role + "/booking/" + lead.getId());
//        model.addAttribute("juridical_path", "/api/" + role + "/juridical/" + lead.getId());
//        model.addAttribute("advertisment_path", "/api/" + role + "/advertisment/" + lead.getId());
//
//        model.addAttribute("role", role);
//        model.addAttribute("lead", lead);
//
//        model.addAttribute("workingPeriod", sharedLogic.workingPeriod(lead));
//        model.addAttribute("contractPeriod", sharedLogic.contractPeriod(lead));
//
//    }
    public Map<Long, String> findAllFlatFotosByLeadAndPutIntoMap(LeadEntity lead){

        Map<Long, byte[]> fotos = new HashMap<>();
        Map<Long, String> allFlatFotos = new HashMap<>();

        lead.getFotos().stream()
                .forEach(foto -> fotos.put(foto.getId(), foto.getFoto()));

        for (Map.Entry<Long, byte[]> entry : fotos.entrySet())
            allFlatFotos.put(entry.getKey(), Base64.getEncoder().encodeToString(entry.getValue()));

        return allFlatFotos;
    }


    public synchronized Map<Long, String> findAllFotoDocsByLeadAndPutIntoMap(LeadEntity lead){

        Map<Long, byte[]> fotoBytes = new HashMap<>();
        Map<Long, String> allFotoDocs = new HashMap<>();

        lead.getJuristInfo().getFotoDocs().stream()
                .forEach(foto -> fotoBytes.put(foto.getId(), foto.getFotoDoc()));

        for (Map.Entry<Long, byte[]> entry : fotoBytes.entrySet())
            allFotoDocs.put(entry.getKey(), Base64.getEncoder().encodeToString(entry.getValue()));

        return allFotoDocs;
    }

//    public synchronized Map<String, List<LeadEntity>> findAllAppropriateExpertLeadsByQueryAndId(String wanted,
//                                                                                                PrincipalEntity expert){
//
//        Map<String, List<LeadEntity>> answer = new HashMap<>();
//        List<LeadEntity> leadsList = new ArrayList<>();
//
//        if (sharedLogic.findWhatItIs(wanted) == 1){
//
//            Long wantedLeadId = Long.parseLong(wanted);
//
//            expert.getLeads().stream()
//                    .filter(elem -> elem.getId().equals(wantedLeadId))
//                    .findFirst().ifPresent(leadsList::add);
//
//        }else if(sharedLogic.findWhatItIs(wanted) == 2){
//
//            expert.getLeads().stream()
//                    .filter(elem -> elem.getTel().contains(wanted))
//                    .forEach(leadsList::add);
//
//        }else if(sharedLogic.findWhatItIs(wanted) == 3){
//
//            expert.getLeads().stream()
//                    .filter(elem -> elem.getName() != null)
//                    .filter(elem -> elem.getName().toLowerCase().contains(wanted.toLowerCase()))
//                    .forEach(leadsList::add);
//
//            expert.getLeads().stream()
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
