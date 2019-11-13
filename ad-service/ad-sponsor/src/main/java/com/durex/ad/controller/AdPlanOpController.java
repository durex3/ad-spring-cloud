package com.durex.ad.controller;

import com.alibaba.fastjson.JSON;
import com.durex.ad.entity.AdPlan;
import com.durex.ad.exception.AdException;
import com.durex.ad.service.IAdPlanService;
import com.durex.ad.vo.AdPlanGetRequest;
import com.durex.ad.vo.AdPlanRequest;
import com.durex.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author gelong
 * @date 2019/11/14 0:18
 */
@Slf4j
@RestController
public class AdPlanOpController {

    @Resource
    private IAdPlanService iAdPlanService;

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest adPlanRequest) throws AdException {
        log.info("ad-sponsor: createAdPlan -> {}", JSON.toJSONString(adPlanRequest));
        return iAdPlanService.createAdPlan(adPlanRequest);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest adPlanGetRequest) throws AdException {
        log.info("ad-sponsor: getAdPlanByIds -> {}", JSON.toJSONString(adPlanGetRequest));
        return iAdPlanService.getAdPlanByIds(adPlanGetRequest);
    }

    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest adPlanRequest) throws AdException {
        log.info("ad-sponsor: updateAdPlan -> {}", JSON.toJSONString(adPlanRequest));
        return iAdPlanService.updateAdPlan(adPlanRequest);
    }

    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest adPlanRequest) throws AdException {
        log.info("ad-sponsor: deleteAdPlan -> {}", JSON.toJSONString(adPlanRequest));
        iAdPlanService.deleteAdPlan(adPlanRequest);
    }
}
