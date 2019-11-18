package com.durex.ad.service.impl;

import com.durex.ad.constant.Constants;
import com.durex.ad.dao.AdPlanRepository;
import com.durex.ad.dao.AdUnitRepository;
import com.durex.ad.dao.CreativeRepository;
import com.durex.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.durex.ad.dao.unit_condition.AdUnitItRepository;
import com.durex.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.durex.ad.dao.unit_condition.CreativeUnitRepository;
import com.durex.ad.entity.AdPlan;
import com.durex.ad.entity.AdUnit;
import com.durex.ad.entity.unit_condition.AdUnitDistrict;
import com.durex.ad.entity.unit_condition.AdUnitIt;
import com.durex.ad.entity.unit_condition.AdUnitKeyword;
import com.durex.ad.entity.unit_condition.CreativeUnit;
import com.durex.ad.exception.AdException;
import com.durex.ad.service.IAdUnitService;
import com.durex.ad.vo.*;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gelong
 * @date 2019/11/10 12:21
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    @Resource
    private AdPlanRepository adPlanRepository;
    @Resource
    private AdUnitRepository adUnitRepository;
    @Resource
    private AdUnitKeywordRepository adUnitKeywordRepository;
    @Resource
    private AdUnitItRepository adUnitItRepository;
    @Resource
    private AdUnitDistrictRepository adUnitDistrictRepository;
    @Resource
    private CreativeRepository creativeRepository;
    @Resource
    private CreativeUnitRepository creativeUnitRepository;

    @Override
    public AdUnitResponse createUnit(AdUnitRequest adUnitRequest) throws AdException {
        if (!adUnitRequest.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> adPlan = adPlanRepository.findById(adUnitRequest.getPlanId());
        if (adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdUnit oldAdUnit = adUnitRepository.findByPlanIdAndUnitName(adUnitRequest.getPlanId(), adUnitRequest.getUnitName());
        if (oldAdUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdUnit = new AdUnit(adUnitRequest.getPlanId(), adUnitRequest.getUnitName(), adUnitRequest.getPositionType(), adUnitRequest.getBudget());
        newAdUnit = adUnitRepository.save(newAdUnit);
        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest adUnitKeywordRequest) throws AdException {
        if (CollectionUtils.isEmpty(adUnitKeywordRequest.getUnitKeywords())) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> unitIds = adUnitKeywordRequest.getUnitKeywords()
                .stream().map(AdUnitKeywordRequest.UnitKeyword::getUnitId).collect(Collectors.toList());
        if (isRelateUnitNotExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitKeyword> adUnitKeywords = Lists.newArrayList();
        adUnitKeywordRequest.getUnitKeywords().forEach(unitKeyword -> adUnitKeywords.add(new AdUnitKeyword(unitKeyword.getUnitId(), unitKeyword.getKeyword())));
        List<Long> ids = adUnitKeywordRepository.saveAll(adUnitKeywords)
                .stream().map(AdUnitKeyword::getId).collect(Collectors.toList());
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest adUnitItRequest) throws AdException {
        if (CollectionUtils.isEmpty(adUnitItRequest.getUnitIts())) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> unitIds = adUnitItRequest.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId).collect(Collectors.toList());
        if (isRelateUnitNotExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitIt> adUnitIts = Lists.newArrayList();
        adUnitItRequest.getUnitIts().forEach(unitIt -> adUnitIts.add(new AdUnitIt(unitIt.getUnitId(), unitIt.getItTag())));
        List<Long> ids = adUnitItRepository.saveAll(adUnitIts).stream()
                .map(AdUnitIt::getId).collect(Collectors.toList());
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest adUnitDistrictRequest) throws AdException {
        if (CollectionUtils.isEmpty(adUnitDistrictRequest.getUnitDistricts())) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> unitIds = adUnitDistrictRequest.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId).collect(Collectors.toList());
        if (isRelateUnitNotExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitDistrict> adUnitDistricts = Lists.newArrayList();
        adUnitDistrictRequest.getUnitDistricts().forEach(unitDistrict -> adUnitDistricts.add(
                new AdUnitDistrict(unitDistrict.getUnitId(), unitDistrict.getProvince(), unitDistrict.getCity())));
        List<Long> ids = adUnitDistrictRepository.saveAll(adUnitDistricts).stream()
                .map(AdUnitDistrict::getId).collect(Collectors.toList());
        return new AdUnitDistrictResponse(ids);
    }

    /**
     * 判断推广单元列表是否存在
     * @param unitIds 推广单元列表
     * @return boolean
     */
    private boolean isRelateUnitNotExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return true;
        }
        return adUnitRepository.findAllById(unitIds).size() != new HashSet<>(unitIds).size();
    }

    /**
     * 判断推广创意列表是否存在
     * @param creativeIds 推广创意列表
     * @return boolean
     */
    private boolean isRelateCreativeNotExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return true;
        }
        return creativeRepository.findAllById(creativeIds).size() != new HashSet<>(creativeIds).size();
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest creativeUnitRequest) throws AdException {
        if (CollectionUtils.isEmpty(creativeUnitRequest.getUnitItems())) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> unitIds = creativeUnitRequest.getUnitItems().stream().
                map(CreativeUnitRequest.CreativeUnitItem::getUnitId).collect(Collectors.toList());
        List<Long> creativeIds = creativeUnitRequest.getUnitItems().stream().
                map(CreativeUnitRequest.CreativeUnitItem::getCreativeId).collect(Collectors.toList());
        if (isRelateUnitNotExist(unitIds) || isRelateCreativeNotExist(creativeIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<CreativeUnit> creativeUnits = Lists.newArrayList();
        creativeUnitRequest.getUnitItems().forEach(creativeUnitItem
                -> creativeUnits.add(new CreativeUnit(creativeUnitItem.getCreativeId(), creativeUnitItem.getUnitId())));
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId).collect(Collectors.toList());
        return new CreativeUnitResponse(ids);
    }
}
