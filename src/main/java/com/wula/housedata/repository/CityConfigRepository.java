package com.wula.housedata.repository;

import com.wula.housedata.entity.CityConfig;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lishaohua on 17-3-8.
 */
@Repository
public interface CityConfigRepository extends JpaRepository<CityConfig, Long>, QueryDslPredicateExecutor<CityConfig> {
    List<CityConfig> findByCityName(String cityName);
    List<CityConfig> findByOrderCodeLessThan(Integer integer, Sort sort);
    List<CityConfig> findByCityCode(String cityCode);
}
