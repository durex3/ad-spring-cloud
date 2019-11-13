package com.durex.ad.controller;

import com.alibaba.fastjson.JSON;
import com.durex.ad.exception.AdException;
import com.durex.ad.service.ICreativeService;
import com.durex.ad.vo.CreativeRequest;
import com.durex.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gelong
 * @date 2019/11/14 0:45
 */
@Slf4j
@RestController
public class CreativeOpController {

    @Resource
    private ICreativeService iCreativeService;

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(CreativeRequest creativeRequest) throws AdException {
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(creativeRequest));
        return iCreativeService.createCreative(creativeRequest);
    }
}
