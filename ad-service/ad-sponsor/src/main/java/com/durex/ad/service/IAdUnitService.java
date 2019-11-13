package com.durex.ad.service;

import com.durex.ad.exception.AdException;
import com.durex.ad.vo.*;

/**
 * @author gelong
 * @date 2019/11/8 23:39
 */
public interface IAdUnitService {

    /**
     * 创建推广单元
     * @param adUnitRequest 接收参数
     * @return AdUnitResponse
     * @throws AdException 异常
     */
    AdUnitResponse createUnit(AdUnitRequest adUnitRequest) throws AdException;

    /**
     * 创建推广单元 关键字
     * @param adUnitKeywordRequest 接收参数
     * @return AdUnitKeywordResponse
     * @throws AdException 异常
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest adUnitKeywordRequest) throws AdException;

    /**
     * 创建推广单元 it
     * @param adUnitItRequest 接收参数
     * @return AdUnitItResponse
     * @throws AdException 异常
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest adUnitItRequest) throws AdException;

    /**
     * 创建推广单元 地域
     * @param adUnitDistrictRequest 接收参数
     * @return AdUnitDistrictResponse
     * @throws AdException 异常
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest adUnitDistrictRequest) throws AdException;

    /**
     * 推广单元与推广创意关联
     * @param creativeUnitRequest 接收参数
     * @return CreativeUnitResponse
     * @throws AdException 异常
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest creativeUnitRequest) throws AdException;

}
