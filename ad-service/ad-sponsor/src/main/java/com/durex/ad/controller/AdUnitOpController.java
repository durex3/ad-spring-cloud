package com.durex.ad.controller;

import com.alibaba.fastjson.JSON;
import com.durex.ad.exception.AdException;
import com.durex.ad.service.IAdUnitService;
import com.durex.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author gelong
 * @date 2019/11/14 0:34
 */
@Slf4j
@RestController
public class AdUnitOpController {

    @Resource
    private IAdUnitService iAdUnitService;

    @PostMapping("/create/adUnit")
    public AdUnitResponse createAdUnit(@RequestBody AdUnitRequest adUnitRequest) throws AdException{
        log.info("ad-sponsor: createAdUnit -> {}", JSON.toJSONString(adUnitRequest));
        return iAdUnitService.createUnit(adUnitRequest);
    }

    @PostMapping("/create/adUnit/keyword")
    public AdUnitKeywordResponse createAdUnitKeyword(@RequestBody AdUnitKeywordRequest adUnitKeywordRequest) throws AdException {
        log.info("ad-sponsor: createAdUnitKeyword -> {}", JSON.toJSONString(adUnitKeywordRequest));
        return iAdUnitService.createUnitKeyword(adUnitKeywordRequest);
    }

    @PostMapping("/create/adUnit/it")
    public AdUnitItResponse createAdUnitIt(@RequestBody AdUnitItRequest adUnitItRequest) throws AdException {
        log.info("ad-sponsor: createAdUnitIt -> {}", JSON.toJSONString(adUnitItRequest));
        return iAdUnitService.createUnitIt(adUnitItRequest);
    }

    @PostMapping("/create/adUnit/district")
    public AdUnitDistrictResponse createAdUnitDistrict(@RequestBody AdUnitDistrictRequest adUnitDistrictRequest) throws AdException {
        log.info("ad-sponsor: createAdUnitDistrict -> {}", JSON.toJSONString(adUnitDistrictRequest));
        return iAdUnitService.createUnitDistrict(adUnitDistrictRequest);
    }

    @PostMapping("create/adUnit/creative")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest creativeUnitRequest) throws AdException {
        log.info("ad-sponsor: createCreativeUnit -> {}", JSON.toJSONString(creativeUnitRequest));
        return iAdUnitService.createCreativeUnit(creativeUnitRequest);
    }

}
