package com.durex.ad.controller;

import com.alibaba.fastjson.JSON;
import com.durex.ad.annotation.IgnoreResponseAdvice;
import com.durex.ad.client.SponsorClient;
import com.durex.ad.client.vo.AdPlan;
import com.durex.ad.client.vo.AdPlanGetRequest;
import com.durex.ad.search.ISearch;
import com.durex.ad.search.vo.SearchRequest;
import com.durex.ad.search.vo.SearchResponse;
import com.durex.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author gelong
 * @date 2019/11/17 13:45
 */
@RestController
@Slf4j
public class SearchController {

    @Resource
    private SponsorClient sponsorClient;
    @Resource
    private ISearch iSearch;

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlanByIds(@RequestBody AdPlanGetRequest adPlanGetRequest) {
        log.info("ad-search: getAdPlanByIds -> {}", JSON.toJSONString(adPlanGetRequest));
        return sponsorClient.getAdPlanByIds(adPlanGetRequest);
    }

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {

        log.info("ad-search: fetchAds -> {}",
                JSON.toJSONString(request));
        return iSearch.fetchAds(request);
    }
}
