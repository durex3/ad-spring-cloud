package com.durex.ad.dao;

import com.durex.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @author gelong
 * @date 2019/11/6 0:14
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    /**
     * 根据id和userId查询推广计划
     * @param id id
     * @param userId userId
     * @return AdPlan
     */
    AdPlan findByIdAndUserId(long id, Long userId);

    /**
     * 根据ids和userId查询推广计划列表
     * @param ids ids
     * @param userId userId
     * @return List<AdPlan>
     */
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    /**
     * 根据userId和PlanName查询计划
     * @param userId userId
     * @param planName planName
     * @return AdPlan
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据status查询计划列表
     * @param status status
     * @return List<AdPlan>
     */
    List<AdPlan> findAllByPlanStatus(Integer status);
}
