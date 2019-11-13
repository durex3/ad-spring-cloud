package com.durex.ad.controller;

import com.alibaba.fastjson.JSON;
import com.durex.ad.exception.AdException;
import com.durex.ad.service.IUserService;
import com.durex.ad.vo.CreateUserRequest;
import com.durex.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gelong
 * @date 2019/11/14 0:07
 */
@Slf4j
@RestController
public class UserOpController {

    @Resource
    private IUserService iUserService;

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest createUserRequest) throws AdException {
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(createUserRequest));
        return iUserService.createUser(createUserRequest);
    }
}
