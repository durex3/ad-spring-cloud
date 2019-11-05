package com.durex.ad.dao;

import com.durex.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @author gelong
 * @date 2019/11/6 0:23
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    /**
     * 根据planId和unitName查询推广单元
     * @param planId planId
     * @param unitName unitName
     * @return AdUnit
     */
    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    /**
     * 根据status查询推广单元列表
     * @param status status
     * @return List<AdUnit>
     */
    List<AdUnit> findAllByUnitStatus(Integer status);
}
