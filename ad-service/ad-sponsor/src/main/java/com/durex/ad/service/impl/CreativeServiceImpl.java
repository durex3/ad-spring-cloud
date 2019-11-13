package com.durex.ad.service.impl;

import com.durex.ad.constant.Constants;
import com.durex.ad.dao.AdUserRepository;
import com.durex.ad.dao.CreativeRepository;
import com.durex.ad.entity.AdUser;
import com.durex.ad.entity.Creative;
import com.durex.ad.exception.AdException;
import com.durex.ad.service.ICreativeService;
import com.durex.ad.vo.CreativeRequest;
import com.durex.ad.vo.CreativeResponse;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author gelong
 * @date 2019/11/13 23:21
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    @Resource
    private AdUserRepository adUserRepository;
    @Resource
    private CreativeRepository creativeRepository;

    @Override
    public CreativeResponse createCreative(CreativeRequest creativeRequest) throws AdException {
        if (!creativeRequest.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdUser> user = adUserRepository.findById(creativeRequest.getUserId());
        if (!user.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        Creative creative = creativeRepository.findByUserIdAndName(creativeRequest.getUserId(), creativeRequest.getName());
        if (creative != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_CREATIVE_ERROR);
        }
        creative = creativeRequest.convertToEntity();
        creative = creativeRepository.save(creative);
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
