package com.durex.ad.service;

import com.durex.ad.exception.AdException;
import com.durex.ad.vo.CreateUserRequest;
import com.durex.ad.vo.CreateUserResponse;

/**
 * @author gelong
 * @date 2019/11/6 0:37
 */
public interface IUserService {

    /**
     * 创建用户
     * @param request 接收参数
     * @return CreateUserResponse
     * @throws AdException 异常
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
