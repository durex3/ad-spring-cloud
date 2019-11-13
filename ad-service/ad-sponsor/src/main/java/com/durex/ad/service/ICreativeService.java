package com.durex.ad.service;

import com.durex.ad.exception.AdException;
import com.durex.ad.vo.CreateUserRequest;
import com.durex.ad.vo.CreativeRequest;
import com.durex.ad.vo.CreativeResponse;

/**
 * @author gelong
 * @date 2019/11/13 23:12
 */
public interface ICreativeService {

    /**
     * 创建创意单元
     * @param creativeRequest 接收参数
     * @return CreativeResponse
     * @throws AdException 异常
     */
    CreativeResponse createCreative(CreativeRequest creativeRequest) throws AdException;
}
