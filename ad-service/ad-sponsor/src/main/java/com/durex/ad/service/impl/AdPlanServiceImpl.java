package com.durex.ad.service.impl;

import com.durex.ad.constant.CommonStatus;
import com.durex.ad.constant.Constants;
import com.durex.ad.dao.AdPlanRepository;
import com.durex.ad.dao.AdUserRepository;
import com.durex.ad.entity.AdPlan;
import com.durex.ad.entity.AdUser;
import com.durex.ad.exception.AdException;
import com.durex.ad.service.IAdPlanService;
import com.durex.ad.utils.DateUtils;
import com.durex.ad.vo.AdPlanGetRequest;
import com.durex.ad.vo.AdPlanRequest;
import com.durex.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author gelong
 * @date 2019/11/8 23:06
 */
@Service
@Slf4j
public class AdPlanServiceImpl implements IAdPlanService {

    @Resource
    private AdUserRepository adUserRepository;
    @Resource
    private AdPlanRepository adPlanRepository;

    @Override
    public AdPlanResponse createAdPlan(AdPlanRequest adPlanRequest) throws AdException {
        if (!adPlanRequest.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdUser> adUser = adUserRepository.findById(adPlanRequest.getUserId());
        if (adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdPlan oldAdPlan = adPlanRepository.findByUserIdAndPlanName(adPlanRequest.getUserId(), adPlanRequest.getPlanName());
        if (oldAdPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newAdPlan = new AdPlan(
                adPlanRequest.getUserId(),
                adPlanRequest.getPlanName(),
                DateUtils.parseStringDate(adPlanRequest.getStartDate()),
                DateUtils.parseStringDate(adPlanRequest.getEndDate())
        );
        newAdPlan = adPlanRepository.save(newAdPlan);
        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest adPlanGetRequest) throws AdException {
        if (!adPlanGetRequest.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return adPlanRepository.findAllByIdInAndUserId(adPlanGetRequest.getIds(), adPlanGetRequest.getUserId());
    }

    @Override
    public AdPlanResponse updateAdPlan(AdPlanRequest adPlanRequest) throws AdException {
        if (!adPlanRequest.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan adPlan = adPlanRepository.findByIdAndUserId(adPlanRequest.getId(), adPlanRequest.getUserId());
        if (adPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        if (StringUtils.isNotEmpty(adPlanRequest.getPlanName())) {
            adPlan.setPlanName(adPlanRequest.getPlanName());
        }
        if (StringUtils.isNotEmpty(adPlanRequest.getStartDate())) {
            adPlan.setStartDate(DateUtils.parseStringDate(adPlanRequest.getStartDate()));
        }
        if (StringUtils.isNotEmpty(adPlanRequest.getEndDate())) {
            adPlan.setEndDate(DateUtils.parseStringDate(adPlanRequest.getEndDate()));
        }
        adPlan.setUpdateTime(new Date());
        adPlan = adPlanRepository.save(adPlan);
        return new AdPlanResponse(adPlan.getId(), adPlan.getPlanName());
    }

    @Override
    public void deleteAdPlan(AdPlanRequest adPlanRequest) throws AdException {
        if (!adPlanRequest.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan adPlan = adPlanRepository.findByIdAndUserId(adPlanRequest.getId(), adPlanRequest.getUserId());
        if (adPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        adPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
        adPlan.setUpdateTime(new Date());
        adPlanRepository.save(adPlan);
    }
}
