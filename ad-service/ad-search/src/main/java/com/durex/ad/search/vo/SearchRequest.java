package com.durex.ad.search.vo;

import com.durex.ad.search.vo.feature.DistrictFeature;
import com.durex.ad.search.vo.feature.FeatureRelation;
import com.durex.ad.search.vo.feature.ItFeature;
import com.durex.ad.search.vo.feature.KeywordFeature;
import com.durex.ad.search.vo.media.AdSlot;
import com.durex.ad.search.vo.media.App;
import com.durex.ad.search.vo.media.Device;
import com.durex.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author gelong
 * @date 2020/1/13 20:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    /**
     * 媒体放的请求标识
     */
    private String mediaId;

    /**
     * 请求基本信息
     */
    private RequestInfo requestInfo;

    /**
     * 匹配信息
     */
    private FeatureInfo featureInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {
        private String requestId;
        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo {
        private KeywordFeature keywordFeature;
        private ItFeature itFeature;
        private DistrictFeature districtFeature;
        private FeatureRelation featureRelation = FeatureRelation.AND;
    }
}
