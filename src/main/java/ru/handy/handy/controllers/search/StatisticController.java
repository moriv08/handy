package ru.handy.handy.controllers.search;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Labels;
import ru.handy.handy.service.PrincipalUserDetailsService;
import ru.handy.handy.service.lead_heap.LeadService;
import ru.handy.handy.service.service.StatisticService;

import java.net.http.HttpHeaders;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/statistic")
public class StatisticController {

    private final LeadService leadService;
    private final PrincipalUserDetailsService principalService;
    private final StatisticService statisticService;

    public StatisticController(LeadService leadService, PrincipalUserDetailsService principalService, StatisticService statisticService) {
        this.leadService = leadService;
        this.principalService = principalService;
        this.statisticService = statisticService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
    @GetMapping("")
    public String statistics(@RequestParam(name = "expertId", required = false) Long expertId,
                             Model model,
                             Principal principal){


        System.out.println("?????????????" +
                "???????????????????????" +
                "??????????????????" +
                "??????????? " + 5);

        Long id = 0L;

        if (expertId instanceof Long)
            id =  expertId;

//        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

        Map<String, List<Integer>> allStatistics = statisticService.findCurrentYearStatistic();
        Map<String, List<Integer>> expertStatistic = statisticService.findCurrentYearStatisticByPrincipalId(id);
        Map<String, Long> expertsList = principalService.findAllOfExpertNamesAndIdInfo();

        String expert = null;
        if (expertId != null)
            expert = principalService.findPrincipalEntityById(expertId).getName();

        model.addAttribute("statistics", allStatistics);
        model.addAttribute("expertStatistic", expertStatistic);
        model.addAttribute("expertList", expertsList);
        model.addAttribute("expert", expert);

        System.out.println(allStatistics.size());
        System.out.println(expertStatistic.size());
        System.out.println(expertsList.size());
        System.out.println(expert);

        return "/admin/statistic";
    }
}
