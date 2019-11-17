package com.durex.ad.client;

import com.durex.ad.client.vo.AdPlan;
import com.durex.ad.client.vo.AdPlanGetRequest;
import com.durex.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author gelong
 * @date 2019/11/17 13:56
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> getAdPlanByIds(AdPlanGetRequest adPlanGetRequest) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
