package ru.handy.handy.service.service;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import ru.handy.handy.models.statistic.MonthStatisticEntity;
import ru.handy.handy.repository.statistic.StatisticRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    private final StatisticRepository statisticRepository;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Transactional
    @ReadOnlyProperty
    public List<MonthStatisticEntity> findAllMonthStatistic(){
        List<MonthStatisticEntity> all = new ArrayList<>();

        statisticRepository.findAll().forEach(all::add);

//        System.out.println("??????????" +
//                "***************************" +
//                "**************************************************************");
//        all.stream().sorted().forEach(stat -> System.out.println(stat.getMonth()));

        return all.stream().sorted().collect(Collectors.toList());
    }

    @Transactional
    @ReadOnlyProperty
    public Map<String, List<Integer>> findCurrentYearStatistic(){
        Map<String, List<Integer>> statistic = new LinkedHashMap<>();

        System.out.println("111111111111111" +
                "1111111111111111" +
                "11111111111111" +
                "11111111111 ");

        findAllMonthStatistic().stream()
                .forEach(stat -> {


                    Integer leads = 0;
                    Integer contracts = 0;
                    Integer deals = 0;

                    if (statistic.get(stat.getMonth()) != null){
                        leads = statistic.get(stat.getMonth()).get(0);
                        contracts = statistic.get(stat.getMonth()).get(1);
                        deals = statistic.get(stat.getMonth()).get(2);
                    }

                    List<Integer> currentStat = new ArrayList<>();
                    currentStat.add(stat.getLeads() + leads);
                    currentStat.add(stat.getContracts() + contracts);
                    currentStat.add(stat.getDeals() + deals);

                    statistic.put(stat.getMonth(), currentStat);
                });

        return statistic;
    }

    @Transactional
    @ReadOnlyProperty
    public Map<String, List<Integer>> findCurrentYearStatisticByPrincipalId(Long id){
        Map<String, List<Integer>> statistic = new LinkedHashMap<>();

        findAllMonthStatistic().stream()
                .filter(stat -> stat.getPrincipal().getId().equals(id))
                .forEach(stat -> {
                    List<Integer> currentStat = new ArrayList<>();
                    currentStat.add(stat.getLeads());
                    currentStat.add(stat.getContracts());
                    currentStat.add(stat.getDeals());
                    statistic.put(stat.getMonth(), currentStat);
                });

        return statistic;
    }
}
