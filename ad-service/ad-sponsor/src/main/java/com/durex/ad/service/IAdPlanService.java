package com.durex.ad.service;

import com.durex.ad.entity.AdPlan;
import com.durex.ad.exception.AdException;
import com.durex.ad.vo.AdPlanGetRequest;
import com.durex.ad.vo.AdPlanRequest;
import com.durex.ad.vo.AdPlanResponse;
import java.util.List;

/**
 * @author gelong
 * @date 2019/11/8 22:25
 */
public interface IAdPlanService {

    /**
     * 创建广告计划
     * @param adPlanRequest 接收参数
     * @return AdPlanResponse
     * @throws AdException 异常
     */
    AdPlanResponse createAdPlan(AdPlanRequest adPlanRequest) throws AdException;

    /**
     * 获取广告计划列表
     * @param adPlanGetRequest 接收参数
     * @return List<AdPlan>
     * @throws AdException 异常
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest adPlanGetRequest) throws AdException;

    /**
     * 更新推广计划
     * @param adPlanRequest 接收参数
     * @return AdPlanResponse
     * @throws AdException 异常
     */
    AdPlanResponse updateAdPlan(AdPlanRequest adPlanRequest) throws AdException;

    /**
     * 删除推广计划
     * @param adPlanRequest 接收参数
     * @throws AdException 异常
     */
    void deleteAdPlan(AdPlanRequest adPlanRequest) throws AdException;
}
