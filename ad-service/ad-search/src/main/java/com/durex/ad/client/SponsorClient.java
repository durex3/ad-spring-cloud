package com.durex.ad.client;

import com.durex.ad.client.vo.AdPlan;
import com.durex.ad.client.vo.AdPlanGetRequest;
import com.durex.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * @author gelong
 * @date 2019/11/17 13:08
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    /**
     * 调用ad-sponsor广告计划列表服务
     * @param adPlanGetRequest 接收参数
     * @return CommonResponse<List<AdPlan>>
     */
    @PostMapping("/ad-sponsor/get/adPlan")
    CommonResponse<List<AdPlan>> getAdPlanByIds(@RequestBody AdPlanGetRequest adPlanGetRequest);
}
