package com.wula.housedata.repository;

import com.querydsl.core.types.Predicate;
import com.wula.housedata.entity.HouseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lishaohua on 17-3-9.
 */
@Repository
public interface HouseDataRepository extends JpaRepository<HouseData, Long>, QueryDslPredicateExecutor<HouseData> {
    List<HouseData> findByCityIdAndTitle(Long cityId, String title);
    Page<HouseData> findByCityName(String cityName, Pageable pageable);

    /**
     * 删除条件内数据
     *
     * @param predicate
     */
    void deleteAllBy(Predicate predicate);

}
