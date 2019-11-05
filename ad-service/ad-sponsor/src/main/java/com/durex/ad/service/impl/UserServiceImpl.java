package com.durex.ad.service.impl;

import com.durex.ad.constant.Constants;
import com.durex.ad.dao.AdUserRepository;
import com.durex.ad.entity.AdUser;
import com.durex.ad.exception.AdException;
import com.durex.ad.service.IUserService;
import com.durex.ad.utils.CommonUtils;
import com.durex.ad.vo.CreateUserRequest;
import com.durex.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * @author gelong
 * @date 2019/11/6 0:39
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private AdUserRepository adUserRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = adUserRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        AdUser newUser = adUserRepository.save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));
        return new CreateUserResponse(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getToken(),
                newUser.getCreateTime(),
                newUser.getUpdateTime()
        );
    }
}
