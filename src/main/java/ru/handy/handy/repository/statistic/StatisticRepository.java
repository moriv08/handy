package ru.handy.handy.repository.statistic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.statistic.MonthStatisticEntity;

import java.util.List;

@Repository
public interface StatisticRepository extends CrudRepository<MonthStatisticEntity, Long> {

//    List<Integer> findAllByMonth(String month);

//    List<MonthStatisticEntity> findAllByPrincipalId(Long id);
}
